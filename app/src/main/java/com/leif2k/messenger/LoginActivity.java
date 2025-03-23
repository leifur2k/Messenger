package com.leif2k.messenger;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonLogin;
    private TextView textViewForgotPassword;
    private TextView textViewRegister;

    private LoginViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            Insets imeInsets = insets.getInsets(WindowInsetsCompat.Type.ime());
            v.setPadding(systemBars.left,
                    Math.max(systemBars.top, imeInsets.top),
                    systemBars.right,
                    Math.max(systemBars.bottom, imeInsets.bottom));
            return insets;
        });

        initViews();
        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        observeViewModel();
        setupClickListeners();

    }


    private void observeViewModel() {

        viewModel.getIsLogged().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if (firebaseUser != null) {
                    Intent intent = UsersActivity.newIntent(LoginActivity.this, firebaseUser.getUid());
                    startActivity(intent);
                    finish();
                }
            }
        });

        viewModel.getIsException().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s != null)
                    Toast.makeText(LoginActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setupClickListeners() {

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();

                if (!email.isEmpty() && !password.isEmpty())
                    viewModel.signIn(email, password);
                else
                    Toast.makeText(LoginActivity.this, "Логин или Пароль не заполнены.", Toast.LENGTH_SHORT).show();
            }
        });

        textViewForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = ResetPasswordActivity.newIntent(
                        LoginActivity.this,
                        editTextEmail.getText().toString().trim()
                );
                startActivity(intent);
            }
        });

        textViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(RegistrationActivity.newIntent(LoginActivity.this));
            }
        });

    }

    public static Intent newIntent(Context context) {
        return new Intent(context, LoginActivity.class);
    }

    private void initViews() {
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        textViewForgotPassword = findViewById(R.id.textViewForgotPassword);
        textViewRegister = findViewById(R.id.textViewRegister);
    }

}