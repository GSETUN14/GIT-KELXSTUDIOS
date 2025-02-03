package com.example.kelxstudios;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;
import android.view.MenuItem;


public class MenuGeneral {

    private final Context context;
    private final DrawerLayout drawerLayout;

    public MenuGeneral(Context context, DrawerLayout drawerLayout) {
        this.context = context;
        this.drawerLayout = drawerLayout;
    }

    public void setupMenu(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(item -> onMenuItemSelected(item));
    }

    private boolean onMenuItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.nameuser) {
            Toast.makeText(context, "User information", Toast.LENGTH_SHORT).show();
        } else if (itemId == R.id.favorites) {
            Toast.makeText(context, "Status | Coming soon...", Toast.LENGTH_SHORT).show();
        } else if (itemId == R.id.products) {
            Toast.makeText(context, "Products", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(context, MenuActivity.class);
            context.startActivity(intent);
        } else if (itemId == R.id.orders) {
            Toast.makeText(context, "Orders", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(context, CheckoutActivity.class);
            context.startActivity(intent);
        } else if (itemId == R.id.logout) {
            logoutUser();
        } else {
            return false;
        }

        drawerLayout.closeDrawers();
        return true;
    }

    private void logoutUser() {
        SharedPreferences preferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();

        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
        if (context instanceof HomeActivity) {
            ((HomeActivity) context).finish();
        }
    }
}
