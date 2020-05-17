package com.example.honoursproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LogInActivity extends AppCompatActivity implements Button.OnClickListener{

    private EditText emailText, passwordText;
    private Button loginBtn, createAccountBtn;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        mAuth = FirebaseAuth.getInstance();

        emailText = findViewById(R.id.emailText);
        passwordText = findViewById(R.id.passwordText);

        createAccountBtn = findViewById(R.id.createAccountButton);
        createAccountBtn.setOnClickListener(this);
        loginBtn = (Button)findViewById(R.id.signInButton);
        loginBtn.setOnClickListener(this);
    }

    @Override
    protected void onStart(){
        super.onStart();

        FirebaseUser mUser = mAuth.getCurrentUser();
        if(mUser != null){
            finish();
            overridePendingTransition(0, 0);
            startActivity(new Intent(LogInActivity.this, MainPageActivity.class));
            overridePendingTransition(0, 0);
        }
    }

    private void checkCredentials(){
        String email, password;
        email = emailText.getText().toString();
        password = passwordText.getText().toString();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(getApplicationContext(), "Please enter an Email...", Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(getApplicationContext(), "Please enter a Password...", Toast.LENGTH_LONG).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(), "Login successful!", Toast.LENGTH_LONG).show();
                            finish();
                            startActivity(new Intent(LogInActivity.this, MainPageActivity.class));
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Login failed, Please try again!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.signInButton:
                checkCredentials();
                break;

            case R.id.createAccountButton:
                startActivity(new Intent(LogInActivity.this, RegisterActivity.class));
                break;
        }
    }
}
