package com.example.kelxstudios;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ProductsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private ArrayList<Product> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping);

        // Inicializar el RecyclerView
        recyclerView = findViewById(R.id.productsRecyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2)); // Cambiamos a GridLayoutManager para las columnas

        // Inicializar la lista de productos
        productList = new ArrayList<>();

        // Obtener el ID de categoría de los extras
        int categoryId = getIntent().getIntExtra("CATEGORY_ID", -1);

        // Cargar los productos según el ID de categoría
        loadProducts(categoryId);
    }

    private void loadProducts(int categoryId) {
        String url = "http://10.0.2.2/kelxstudiosgst/get_products.php?id_categoria=" + categoryId;

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            // Parsear la respuesta JSON
                            JSONArray jsonArray = new JSONArray(response);

                            // Recorrer la respuesta JSON y agregar los productos a la lista
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject productObject = jsonArray.getJSONObject(i);

                                String name = productObject.getString("nombre");
                                String description = productObject.getString("descripcion");
                                String price = productObject.getString("precio");
                                String imageUrl = productObject.getString("imagen_url");

                                // Crear el producto y agregarlo a la lista
                                Product product = new Product(name, description, price, imageUrl);
                                productList.add(product);
                            }

                            // Establecer el adaptador del RecyclerView
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
                        // Manejo de errores
                        Toast.makeText(ProductsActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        // Añadir la solicitud a la cola de Volley
        queue.add(stringRequest);
    }
}