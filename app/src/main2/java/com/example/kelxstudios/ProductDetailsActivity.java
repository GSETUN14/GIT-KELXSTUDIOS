package com.example.kelxstudios;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.squareup.picasso.Picasso;

public class ProductDetailsActivity extends AppCompatActivity {

    private TextView productName, productDescription, productPrice;
    private ImageView productImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_model);

        // Inicializar vistas
        productName = findViewById(R.id.name_product);
        productDescription = findViewById(R.id.description_item);
        productPrice = findViewById(R.id.price_product);
        productImage = findViewById(R.id.product_photo);

        // Recibir datos del intent
        String name = getIntent().getStringExtra("product_name");
        String description = getIntent().getStringExtra("product_description");
        String price = getIntent().getStringExtra("product_price");
        String imageUrl = getIntent().getStringExtra("product_image_url");

        // Asignar los datos a las vistas
        productName.setText(name);
        productDescription.setText(description);
        productPrice.setText("€" + price);
        Picasso.get().load(imageUrl).into(productImage);
    }
}
