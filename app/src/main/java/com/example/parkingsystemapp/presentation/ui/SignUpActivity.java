package com.example.parkingsystemapp.presentation.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
    private ImageView xIconUsername;
    private ImageView xIconName;
    private ImageView xIconSurname;
    private ImageView xIconEmail;
    private ImageView xIconPhone;
    private ImageView xIconPassword;
    private ImageView xIconAgainPassword;
    private TextView usernameWrong;

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
        xIconUsername = findViewById(R.id.x_icon_username);
        xIconName = findViewById(R.id.x_icon_name);
        xIconSurname = findViewById(R.id.x_icon_surname);
        xIconEmail = findViewById(R.id.x_icon_email);
        xIconPhone = findViewById(R.id.x_icon_phone);
        xIconPassword = findViewById(R.id.x_icon_password);
        xIconAgainPassword = findViewById(R.id.x_icon_againPassword);
        usernameWrong = findViewById(R.id.username_wrong);

        register.setOnClickListener(v -> {
            String username = this.username.getText().toString();
            String name = this.name.getText().toString();
            String surname = this.surname.getText().toString();
            String email = this.email.getText().toString();
            String phone = this.phone.getText().toString();
            String password = this.password.getText().toString();
            String againPassword = this.againPassword.getText().toString();

            if(username.isEmpty()) {
                xIconUsername.setVisibility(View.VISIBLE);
            } else if(username.length() < 3) {
                xIconUsername.setVisibility(View.VISIBLE);
                usernameWrong.setText(R.string.username_less_than_3);
            } else if(username.length() > 12) {
                xIconUsername.setVisibility(View.VISIBLE);
                usernameWrong.setText(R.string.username_greater_than_12);
            } else {
                xIconUsername.setVisibility(View.GONE);
                usernameWrong.setText("");
            }

            if(name.isEmpty()) {
                xIconName.setVisibility(View.VISIBLE);
            } else {
                xIconName.setVisibility(View.GONE);
            }

            if(surname.isEmpty()) {
                xIconSurname.setVisibility(View.VISIBLE);
            } else {
                xIconSurname.setVisibility(View.GONE);
            }

            if(!EmailParser.isEmailValid(email)) {
                xIconEmail.setVisibility(View.VISIBLE);
            } else {
                xIconEmail.setVisibility(View.GONE);
            }
        });
    }
}