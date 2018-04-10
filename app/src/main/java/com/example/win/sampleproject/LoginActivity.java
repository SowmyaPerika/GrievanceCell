package com.example.win.sampleproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private EditText mlogin_email,mlogin_password;
    private Button mlogin_btn,mgetPassword;
    private ProgressDialog mProgress;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().setTitle("Login");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mProgress=new ProgressDialog(this);

        mAuth= FirebaseAuth.getInstance();

        mlogin_email=(EditText) findViewById(R.id.login_email);
        mlogin_password=(EditText) findViewById(R.id.login_password);
        mlogin_btn=(Button)findViewById(R.id.login_btn);
        mgetPassword=(Button)findViewById(R.id.getpassword);

        mlogin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email =mlogin_email.getText().toString();
                String password = mlogin_password.getText().toString();
                if (!TextUtils.isEmpty(email) || !TextUtils.isEmpty(password)) {
                    mProgress.setTitle("Logging In");
                    mProgress.setMessage("Please wait while we check your Credentials.");
                    mProgress.setCanceledOnTouchOutside(false);
                    mProgress.show();
                    loginUser(email,password);
                }else {
                    Toast.makeText(LoginActivity.this, "Please Fill the form correctly", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    private void loginUser(final String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    mProgress.dismiss();
                    Intent a=new Intent(LoginActivity.this,WelcomeActivity.class);
                    a.putExtra("mailid",email);
                    startActivity(a);
                    finish();
                }else{
                    mProgress.hide();
                    Toast.makeText(LoginActivity.this, "Cannot Sign in.Please check the form and try again.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick (View v){
                String email = mlogin_email.getText().toString();
                if (TextUtils.isEmpty(email)) {
                    mlogin_email.setError("Email cannot be empty");
                    return;
                } else {
                    mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(LoginActivity.this, "Check your email to reset password", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }

            }

        });
    }
    }

