package com.example.kelxstudios;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;  // Contenedor para el menú lateral (Drawer)
    private NavigationView navigationView;  // Vista del menú de navegación
    private MenuGeneral menuGeneral;  // Clase para manejar el menú general

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);  // Establece el layout de la actividad

        // Inicializa el DrawerLayout y el NavigationView
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        // Inicializa el objeto que maneja el menú general
        menuGeneral = new MenuGeneral(this, drawerLayout);
        menuGeneral.setupMenu(navigationView);  // Configura el menú con la vista de navegación

        // Botón para navegar a la tienda
        Button shopNowButton = findViewById(R.id.btt_shop_now);
        // Botón para ver los pedidos
        Button myOrdersButton = findViewById(R.id.btt_my_orders);
        // Icono de cuenta para abrir/cerrar el menú lateral
        ImageView accountIcon = findViewById(R.id.account);

        // Configura el comportamiento del icono de cuenta: abre/cierra el menú lateral
        accountIcon.setOnClickListener(v -> {
            if (!drawerLayout.isDrawerOpen(navigationView)) {  // Si el menú no está abierto
                drawerLayout.openDrawer(navigationView);  // Lo abre
            } else {
                drawerLayout.closeDrawer(navigationView);  // Si ya está abierto, lo cierra
            }
        });

        // Configura el comportamiento del botón "Shop Now"
        shopNowButton.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, MenuActivity.class);
            startActivity(intent);  // Inicia la actividad MenuActivity
        });

        // Configura el comportamiento del botón "My Orders"
        myOrdersButton.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, CheckoutActivity.class);
            startActivity(intent);  // Inicia la actividad CheckoutActivity
        });
    }
}
