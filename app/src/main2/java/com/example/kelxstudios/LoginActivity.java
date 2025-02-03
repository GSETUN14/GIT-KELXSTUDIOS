package com.example.kelxstudios;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private EditText emailField, passwordField;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Verificar si el usuario ya está autenticado
        SharedPreferences preferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        boolean isLoggedIn = preferences.getBoolean("isLoggedIn", false);

        if (isLoggedIn) {
            // Redirigir directamente a HomeActivity
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
            finish(); // Cerrar LoginActivity
            return; // Salir del método para evitar cargar el layout
        }

        // Si no está autenticado, cargar el layout de login
        setContentView(R.layout.activity_login);

        // Inicializar los elementos de la interfaz
        emailField = findViewById(R.id.editTextText);
        passwordField = findViewById(R.id.editTextTextPassword);
        loginButton = findViewById(R.id.btt_shop_now);

        // Establecer el evento de clic en el botón de login
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailField.getText().toString().trim();
                String password = passwordField.getText().toString().trim();

                // Verificar si los campos no están vacíos
                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else {
                    loginUser(email, password); // Llamar al método de login
                }
            }
        });
    }

    // Método para realizar la solicitud POST al servidor PHP
    private void loginUser(String email, String password) {
        String url = "http://10.0.2.2/kelxstudiosgst/login.php"; // URL de tu servidor local

        // Crear la cola de peticiones para Volley
        RequestQueue queue = Volley.newRequestQueue(this);

        // Crear la solicitud de tipo POST
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            // Parsear la respuesta JSON
                            JSONObject jsonObject = new JSONObject(response);
                            String status = jsonObject.getString("status");

                            if (status.equals("success")) {
                                // Guardar estado de sesión en SharedPreferences
                                SharedPreferences preferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putBoolean("isLoggedIn", true); // Guardar que el usuario está autenticado
                                editor.apply();

                                Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();

                                // Redirigir a la nueva actividad
                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                startActivity(intent); // Iniciar la actividad HomeActivity
                                finish(); // Finalizar LoginActivity
                            } else {
                                // Si hay un error, mostrar el mensaje
                                Toast.makeText(LoginActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(LoginActivity.this, "Error parsing response", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Manejo de errores de red
                        Toast.makeText(LoginActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                // Crear los parámetros para la solicitud POST
                Map<String, String> params = new HashMap<>();
                params.put("correo", email); // Nombre del parámetro debe coincidir con el PHP
                params.put("contrasena", password); // Nombre del parámetro debe coincidir con el PHP
                return params;
            }
        };

        // Agregar la solicitud a la cola de Volley
        queue.add(stringRequest);
    }
}
