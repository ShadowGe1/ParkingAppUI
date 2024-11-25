package com.example.parkingsystemapp.presentation.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.parkingsystemapp.R;
import com.example.parkingsystemapp.data.parser.EmailParser;

public class SignUpActivity extends AppCompatActivity {
    private EditText username;
    private EditText name;
    private EditText surname;
    private EditText email;
    private EditText phone;
    private EditText password;
    private EditText againPassword;
    private Button register;
    private ImageView x_icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        username = findViewById(R.id.register_username);
        name = findViewById(R.id.register_name);
        surname = findViewById(R.id.register_surname);
        email = findViewById(R.id.register_email);
        phone = findViewById(R.id.register_phone);
        password = findViewById(R.id.register_password);
        againPassword = findViewById(R.id.register_again_password);
        register = findViewById(R.id.register_button);
        x_icon = findViewById(R.id.x_icon);

        register.setOnClickListener(v -> {
            if(!EmailParser.isEmailValid(email.getText().toString())) {
                showErrorIcon(email);
            } else {
                hideErrorIcon(email);
            }
        });
    }

    private void showErrorIcon(View nearView) {
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) x_icon.getLayoutParams();
        params.startToEnd = nearView.getId(); // Poziționează iconița lângă câmp
        params.topToTop = nearView.getId();  // Aliniază vertical
        x_icon.setLayoutParams(params);

        x_icon.setVisibility(View.VISIBLE); // Afișează iconița
    }

    // Metodă pentru a ascunde iconița de eroare
    private void hideErrorIcon(View nearView) {
        if (x_icon.getVisibility() == View.VISIBLE) {
            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) x_icon.getLayoutParams();
            if (params.startToEnd == nearView.getId()) {
                x_icon.setVisibility(View.GONE); // Ascunde iconița
            }
        }
    }

}