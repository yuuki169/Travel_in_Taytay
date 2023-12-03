package com.project.travel_in_taytay.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.project.travel_in_taytay.R;
import com.project.travel_in_taytay.User.UserDashboard;

public class Login extends AppCompatActivity {
    TextInputEditText editTextEmail, editTextPassword;
    Button loginButton;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    TextView loginTextView;


    //check if the user is log in it will open the main activity screen or the resto screen
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent userDashboard = new Intent(getApplicationContext(), UserDashboard.class);
            startActivity(userDashboard);
            finish();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        editTextEmail= findViewById(R.id.email);
        editTextPassword= findViewById(R.id.password);
        loginButton = findViewById(R.id.btnlogin);
        progressBar = findViewById(R.id.progressBar);
        loginTextView = findViewById(R.id.createAccount);

        //when text view or return to login is click
        loginTextView.setOnClickListener(v -> {
            Intent openRegister = new Intent(getApplicationContext(), Register.class);
            startActivity(openRegister);
            finish();

        });

        loginButton.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);
            String email, password;
            email = String.valueOf(editTextEmail.getText());
            password = String.valueOf(editTextPassword.getText());


            //show message for email
            if(TextUtils.isEmpty((email))){
                Toast.makeText(Login.this, "Invalid Email", Toast.LENGTH_SHORT).show();
                return;
            }

            //show message for password
            if(TextUtils.isEmpty((password))){
                Toast.makeText(Login.this, "Invalid Password", Toast.LENGTH_SHORT).show();
                return;
            }

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(getApplicationContext(), "Sign-in successfully",
                                    Toast.LENGTH_SHORT).show();
                            //open the main activity for restaurant
                            Intent userDashboard = new Intent(getApplicationContext(), UserDashboard.class);
                            startActivity(userDashboard);
                            finish();
                        } else {
                            // Sign in failed, update UI with the signed-in user's information
                            Toast.makeText(Login.this, "Authentication failed",
                                    Toast.LENGTH_SHORT).show();

                        }
                    });


        });
    }

}