package com.example.kelxstudios;

import android.os.Bundle;
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

public class RegisterActivity extends AppCompatActivity {

    private EditText nameField, lastNameField, emailField, phoneField, passwordField, confirmPasswordField;
    private Button createAccountButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Vincular los elementos del diseño
        nameField = findViewById(R.id.editTextText);
        lastNameField = findViewById(R.id.editTextText6);
        emailField = findViewById(R.id.editTextText4);
        phoneField = findViewById(R.id.editTextText7);
        passwordField = findViewById(R.id.editTextTextPassword);
        confirmPasswordField = findViewById(R.id.editTextTextPassword4);
        createAccountButton = findViewById(R.id.btt_shop_now);

        // Configurar el botón de crear cuenta
        createAccountButton.setOnClickListener(v -> {
            String name = nameField.getText().toString().trim();
            String lastName = lastNameField.getText().toString().trim();
            String email = emailField.getText().toString().trim();
            String phone = phoneField.getText().toString().trim();
            String password = passwordField.getText().toString().trim();
            String confirmPassword = confirmPasswordField.getText().toString().trim();

            // Validar campos
            if (name.isEmpty() || lastName.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(RegisterActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!password.equals(confirmPassword)) {
                Toast.makeText(RegisterActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                return;
            }

            // Llamar al método para registrar al usuario
            registerUser(name, lastName, email, phone, password);
        });
    }

    private void registerUser(String name, String lastName, String email, String phone, String password) {
        String url = "http://10.0.2.2/kelxstudiosgst/register.php"; // Cambia según tu servidor

        // Crear la cola de solicitudes de Volley
        RequestQueue queue = Volley.newRequestQueue(this);

        // Crear la solicitud POST
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            // Parsear la respuesta del servidor
                            JSONObject jsonObject = new JSONObject(response);
                            String status = jsonObject.getString("status");

                            if (status.equals("success")) {
                                Toast.makeText(RegisterActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                                finish(); // Regresar a la actividad anterior
                            } else {
                                Toast.makeText(RegisterActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(RegisterActivity.this, "Error parsing response", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Manejo de errores
                        Toast.makeText(RegisterActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                // Crear los parámetros para la solicitud POST
                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                params.put("lastName", lastName);
                params.put("email", email);
                params.put("phone", phone);
                params.put("password", password);
                return params;
            }
        };

        // Agregar la solicitud a la cola
        queue.add(stringRequest);
    }
}
