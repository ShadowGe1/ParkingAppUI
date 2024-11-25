package com.example.parkingsystemapp.presentation.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.parkingsystemapp.R;
import com.example.parkingsystemapp.data.remote.SignInClient;
import com.example.parkingsystemapp.data.repository.AuthRepository;

public class SignInActivity extends AppCompatActivity {
    private EditText email;
    private EditText password;
    private TextView textView;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_in);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        email = findViewById(R.id.email_prompt);
        password = findViewById(R.id.password_prompt);
        Button signIn = findViewById(R.id.sign_in_action);
        textView = findViewById(R.id.textView);

        signIn.setOnClickListener(v -> {
            if(password.getText().toString().length() < 8) {
                textView.setText(R.string.the_password_has_less_than_8_symbols);
                return;
            }
            AuthRepository authRepository = new AuthRepository();
            SignInClient apiClient = new SignInClient(email.getText().toString(), password.getText().toString(), authRepository);
            apiClient.execute(((success, message) -> textView.setText(authRepository.getMessage())));
        });
    }
}