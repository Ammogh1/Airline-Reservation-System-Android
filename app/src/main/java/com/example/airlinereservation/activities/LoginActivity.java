package com.example.airlinereservation.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.airlinereservation.R;
import com.example.airlinereservation.database.DatabaseHelper;
import com.example.airlinereservation.models.User;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button btnLogin;
    private TextView tvRegister, tvAdminLogin;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dbHelper = new DatabaseHelper(this);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvRegister = findViewById(R.id.tvRegister);
        tvAdminLogin = findViewById(R.id.tvAdminLogin);

        btnLogin.setOnClickListener(v -> loginUser());
        tvRegister.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        });
        
        tvAdminLogin.setOnClickListener(v -> {
            String mEmail = etEmail.getText().toString().trim();
            String mPassword = etPassword.getText().toString().trim();
            if(mEmail.equalsIgnoreCase("admin") && mPassword.equalsIgnoreCase("admin")) {
                startActivity(new Intent(LoginActivity.this, AdminDashboardActivity.class));
                finish();
            } else {
                Toast.makeText(LoginActivity.this, "Invalid admin credentials (use admin/admin)", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loginUser() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        User user = dbHelper.loginUser(email, password);
        if (user != null) {
            Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();
            // Storing UserId globally is easier without SharedPreferences for a simple app demo
            android.content.SharedPreferences prefs = getSharedPreferences("AppPrefs", MODE_PRIVATE);
            prefs.edit().putInt("USER_ID", user.getId()).apply();
            
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show();
        }
    }
}
