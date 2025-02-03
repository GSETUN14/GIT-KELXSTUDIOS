package com.example.kelxstudios;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class SessionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session);

        Button signInButton = findViewById(R.id.btt_sign_in);
        Button createAccountButton = findViewById(R.id.btt_create_account);

        signInButton.setOnClickListener(v -> {
            Intent intent = new Intent(SessionActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        createAccountButton.setOnClickListener(v -> {
            Intent intent = new Intent(SessionActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }
}
