package com.example.kelxstudios;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_styles);

        // Listener para cada categoría
        findViewById(R.id.ConstraintSportweear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openProductsActivity(3); // ID de SPORTWEEAR
            }
        });

        findViewById(R.id.ConstraintFashion).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openProductsActivity(4); // ID de FASHION
            }
        });

        findViewById(R.id.ConstraintEssentials).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openProductsActivity(1); // ID de ESSENTIALS
            }
        });

        findViewById(R.id.ConstraintBasics).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openProductsActivity(2); // ID de BASICS
            }
        });
    }

    // Método para abrir la actividad de productos con un ID de categoría específico
    private void openProductsActivity(int categoryId) {
        Intent intent = new Intent(MenuActivity.this, ProductsActivity.class);
        intent.putExtra("CATEGORY_ID", categoryId); // Pasar el ID de categoría como extra
        startActivity(intent);
    }
}
