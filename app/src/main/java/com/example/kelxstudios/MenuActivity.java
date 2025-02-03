package com.example.kelxstudios;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class MenuActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;  // Contenedor para el menú lateral (Drawer)
    private NavigationView navigationView;  // Vista del menú de navegación

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_styles);  // Establece el layout de la actividad

        // Configura los clics en los botones de las categorías de productos
        findViewById(R.id.ConstraintSportweear).setOnClickListener(v -> openProductsActivity(3));  // Categoría "Sportwear"
        findViewById(R.id.ConstraintFashion).setOnClickListener(v -> openProductsActivity(4));  // Categoría "Fashion"
        findViewById(R.id.ConstraintEssentials).setOnClickListener(v -> openProductsActivity(1));  // Categoría "Essentials"
        findViewById(R.id.ConstraintBasics).setOnClickListener(v -> openProductsActivity(2));  // Categoría "Basics"

        // Inicializa el DrawerLayout y NavigationView para el menú lateral
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        // Inicializa y configura el menú lateral con MenuGeneral
        MenuGeneral menuGeneral = new MenuGeneral(this, drawerLayout);
        menuGeneral.setupMenu(navigationView);

        // Icono de cuenta para abrir/cerrar el menú lateral
        ImageView accountIcon = findViewById(R.id.account);

        accountIcon.setOnClickListener(v -> {
            // Abre o cierra el menú lateral al hacer clic en el icono de cuenta
            if (!drawerLayout.isDrawerOpen(navigationView)) {
                drawerLayout.openDrawer(navigationView);  // Si no está abierto, lo abre
            } else {
                drawerLayout.closeDrawer(navigationView);  // Si ya está abierto, lo cierra
            }
        });
    }

    // Metodo para abrir la actividad de productos pasando el ID de categoría
    private void openProductsActivity(int categoryId) {
        // Crea un Intent para iniciar ProductsActivity
        Intent intent = new Intent(MenuActivity.this, ProductsActivity.class);
        // Pasa el ID de la categoría seleccionada como extra en el Intent
        intent.putExtra("CATEGORY_ID", categoryId);
        startActivity(intent);  // Inicia la actividad
    }
}
