package com.example.kelxstudios;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class ProductDetailS extends AppCompatActivity {

    private TextView productName, productDescription, productPrice;
    private ImageView productImage;
    private Button addToCartButton;
    private ShoppingCart shoppingCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_model);

        productName = findViewById(R.id.name_product);
        productDescription = findViewById(R.id.description_item);
        productPrice = findViewById(R.id.price_product);
        productImage = findViewById(R.id.product_photo);
        addToCartButton = findViewById(R.id.button_add_product);

        shoppingCart = ShoppingCart.getInstance();

        String name = getIntent().getStringExtra("product_name");
        String description = getIntent().getStringExtra("product_description");
        String price = getIntent().getStringExtra("product_price");
        String imageUrl = getIntent().getStringExtra("product_image_url");

        productName.setText(name);
        productDescription.setText(description);
        productPrice.setText(price + "€");
        Picasso.get().load(imageUrl).into(productImage);

        addToCartButton.setOnClickListener(v -> {
            try {
                int productId = (int) (Math.random() * 1000);
                double productPrice = Double.parseDouble(price);

                CartProduct cartProduct = new CartProduct(productId, name, description, productPrice, imageUrl, "M");

                shoppingCart.addProduct(cartProduct);

                Toast.makeText(ProductDetailS.this, "Product added to the cart", Toast.LENGTH_SHORT).show();
            } catch (NumberFormatException e) {
                Toast.makeText(ProductDetailS.this, "Error with the product price", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
