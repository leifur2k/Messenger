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

public class ResetPasswordActivity extends AppCompatActivity {

    private static final String EXTRA_EMAIL = "email";

    private EditText editTextEmail;
    private Button buttonResetPassword;

    private ResetPasswordViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_reset_password);
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

        String email = getIntent().getStringExtra(EXTRA_EMAIL);
        editTextEmail.setText(email);

        viewModel = new ViewModelProvider(this).get(ResetPasswordViewModel.class);

        observeViewModel();
        setupClickListeners();

    }

    private void observeViewModel() {

        viewModel.getIsSentReset().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isSentReset) {
                if (isSentReset)
                    finish();
            }
        });

        viewModel.getIsError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(ResetPasswordActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setupClickListeners() {

        buttonResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString().trim();

                if (!email.isEmpty())
                    viewModel.resetPass(email);
                else
                    Toast.makeText(ResetPasswordActivity.this, "Поле Email пусто.", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public static Intent newIntent(Context context, String email) {
        Intent intent = new Intent(context, ResetPasswordActivity.class);
        intent.putExtra("email", email);
        return intent;
    }

    private void initViews() {
        editTextEmail = findViewById(R.id.editTextEmail);
        buttonResetPassword = findViewById(R.id.buttonResetPassword);
    }

}