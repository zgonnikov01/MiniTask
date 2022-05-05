package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    FirebaseAuth mAuth;
    Button signInButton, logInButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        signInButton = findViewById(R.id.signInButton);
        logInButton = findViewById(R.id.logInButton);
        signInButton.setOnClickListener(this);
        logInButton.setOnClickListener(this);


        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            //reload();
            Intent i = new Intent(this, TasksActivity.class);
            i.putExtra("user", currentUser);
            startActivity(i);
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()) {
            case R.id.logInButton:
                i = new Intent(this, LogInActivity.class);
                startActivity(i);
                finish();
                break;
            case R.id.signInButton:
                i = new Intent(this, CreateAccountActivity.class);
                startActivity(i);
                finish();
                break;
        }
    }
}