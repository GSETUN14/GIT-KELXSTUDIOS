package com.example.kelxstudios;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ProductsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private ArrayList<Product> productList;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping);

        recyclerView = findViewById(R.id.productsRecyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        productList = new ArrayList<>();

        int categoryId = getIntent().getIntExtra("CATEGORY_ID", -1);

        loadProducts(categoryId);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        MenuGeneral menuGeneral = new MenuGeneral(this, drawerLayout);
        menuGeneral.setupMenu(navigationView);

        ImageView accountIcon = findViewById(R.id.account);

        accountIcon.setOnClickListener(v -> {
            if (!drawerLayout.isDrawerOpen(navigationView)) {
                drawerLayout.openDrawer(navigationView);
            } else {
                drawerLayout.closeDrawer(navigationView);
            }
        });

    }

    private void loadProducts(int categoryId) {
        String url = "http://10.0.2.2/kelxstudiosgst/get_products.php?id_categoria=" + categoryId;

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject productObject = jsonArray.getJSONObject(i);

                                String name = productObject.getString("nombre");
                                String description = productObject.getString("descripcion");
                                String price = productObject.getString("precio");
                                String imageUrl = productObject.getString("imagen_url");

                                Product product = new Product(name, description, price, imageUrl);
                                productList.add(product);
                            }

                            productAdapter = new ProductAdapter(ProductsActivity.this, productList);
                            recyclerView.setAdapter(productAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(ProductsActivity.this, "Error parsing JSON", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ProductsActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        queue.add(stringRequest);
    }
}
