package com.example.kelxstudios;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home); // Usamos el layout activity_home.xml

        // Enlazar los botones con sus ID del XML
        Button shopNowButton = findViewById(R.id.btt_shop_now);
        Button myOrdersButton = findViewById(R.id.btt_my_orders);
        Button logoutButton = findViewById(R.id.logout_button);

        // Configurar el listener para el botón SHOP NOW
        shopNowButton.setOnClickListener(v -> {
            // Abrir la pantalla de Menú
            Intent intent = new Intent(HomeActivity.this, MenuActivity.class);
            startActivity(intent);
        });

        // Configurar el listener para el botón MY ORDERS
        myOrdersButton.setOnClickListener(v -> {
            // Abrir la pantalla de Menú (puedes cambiar a otra actividad específica)
            Intent intent = new Intent(HomeActivity.this, ProductsActivity.class);
            startActivity(intent);
        });

        // Configurar el listener para el botón LOGOUT
        logoutButton.setOnClickListener(v -> {
            logoutUser();
        });
    }

    private void logoutUser() {
        // Eliminar estado de autenticación
        SharedPreferences preferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear(); // Eliminar todos los datos almacenados
        editor.apply(); // Guardar cambios

        // Redirigir a LoginActivity
        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
        startActivity(intent);
        finish(); // Cerrar HomeActivity
    }
}
