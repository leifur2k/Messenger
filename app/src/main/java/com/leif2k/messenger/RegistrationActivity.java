package com.leif2k.messenger;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.auth.FirebaseUser;

public class RegistrationActivity extends AppCompatActivity {

    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextName;
    private EditText editTextLastName;
    private EditText editTextAge;
    private Button buttonSignUp;

    private RegistrationViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registration);
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
        viewModel = new ViewModelProvider(this).get(RegistrationViewModel.class);

        observeViewModel();
        setupClickListeners();

    }

    private void observeViewModel() {

        viewModel.getUser().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if (firebaseUser != null) {
                    Intent intent = UsersActivity.newIntent(RegistrationActivity.this, firebaseUser);
                    startActivity(intent);
                    finish();
                }
            }
        });

        viewModel.getIsError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(RegistrationActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setupClickListeners() {

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();
                String name = editTextName.getText().toString().trim();
                String lastName = editTextLastName.getText().toString().trim();
                String age = editTextAge.getText().toString().trim();

                int parseInt = 0;
                try {
                    parseInt = Integer.parseInt(age);
                } catch (NumberFormatException ignored) {
                }

                if (!email.isEmpty() && !password.isEmpty() && !name.isEmpty() && !lastName.isEmpty() && !age.isEmpty()) {
                    if (parseInt > 0 && parseInt < 131)
                        viewModel.registerNewUser(email, password, name, lastName, parseInt);
                    else
                        Toast.makeText(RegistrationActivity.this, "Введите корректный возраст от 1 до 130", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(RegistrationActivity.this, "Заполните все поля!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public static Intent newIntent(Context context) {
        return new Intent(context, RegistrationActivity.class);
    }

    private void initViews() {
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextName = findViewById(R.id.editTextName);
        editTextLastName = findViewById(R.id.editTextLastName);
        editTextAge = findViewById(R.id.editTextAge);
        buttonSignUp = findViewById(R.id.buttonSignUp);
    }

}