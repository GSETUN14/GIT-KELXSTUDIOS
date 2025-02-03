package com.example.kelxstudios;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class SessionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session); // Usamos el layout activity_home.xml

        // Enlazar los botones con sus ID del XML
        Button signInButton = findViewById(R.id.btt_sign_in);
        Button createAccountButton = findViewById(R.id.btt_create_account);

        // Configurar el listener para el botón SIGN-IN
        signInButton.setOnClickListener(v -> {
            // Abrir la pantalla de Login
            Intent intent = new Intent(SessionActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        // Configurar el listener para el botón CREATE ACCOUNT
        createAccountButton.setOnClickListener(v -> {
            // Abrir la pantalla de creación de cuenta
            Intent intent = new Intent(SessionActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }
}
