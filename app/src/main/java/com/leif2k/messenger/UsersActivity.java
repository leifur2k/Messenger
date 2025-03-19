package com.leif2k.messenger;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.auth.FirebaseUser;

public class UsersActivity extends AppCompatActivity {

    private static final String EXTRA_USER = "user";
    private Button buttonLogout;
    private UsersViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_users);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initViews();
        viewModel = new ViewModelProvider(this).get(UsersViewModel.class);

        observeViewModel();
        setupClickListeners();

        Intent intent = getIntent();
        FirebaseUser firebaseUser = intent.getParcelableExtra(EXTRA_USER);


    }


    private void observeViewModel() {

        viewModel.getUser().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if (firebaseUser == null) {
                    Intent intent = LoginActivity.newIntent(UsersActivity.this);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }

    private void setupClickListeners() {

        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.logOut();
            }
        });

    }

    public static Intent newIntent(Context context, FirebaseUser firebaseUser) {
        Intent intent = new Intent(context, UsersActivity.class);
        intent.putExtra(EXTRA_USER, firebaseUser);
        return intent;

    }

    private void initViews() {
        buttonLogout = findViewById(R.id.buttonLogout);
    }
}