package com.example.win.sampleproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class adminLogin extends AppCompatActivity {
    private Button mlogin,mpassword;
    private EditText memail,mpass;
    private FirebaseAuth mAuth;
    private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        mProgress=new ProgressDialog(this);
        Firebase.setAndroidContext(this);
        mAuth= FirebaseAuth.getInstance();

        mpass = (EditText) findViewById(R.id.login_password);
        memail = (EditText) findViewById(R.id.login_email);
        mlogin = (Button) findViewById(R.id.login_btn);
        mpassword = (Button) findViewById(R.id.getpassword);

        mlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = memail.getText().toString();
                String password = mpass.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    memail.setError("Email cannot be empty");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    mpass.setError("Password cannot be empty");
                    return;
                }
                if (email.equals("drksrujanraju@gmail.com")) {
                    mProgress.setTitle("Logging In");
                    mProgress.setMessage("Please wait while we check your Credentials.");
                    mProgress.setCanceledOnTouchOutside(false);
                    mProgress.show();

                    loginUser(email, password);
                    /*Intent i = new Intent(adminLogin.this, adminView.class);
                    startActivity(i);*/
                } else {
                    Toast.makeText(adminLogin.this, "You are an unauthorized user", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    private void loginUser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    mProgress.dismiss();
                    Intent a=new Intent(adminLogin.this,adminView.class);
                    startActivity(a);
                    finish();
                }else{
                    mProgress.hide();
                    Toast.makeText(adminLogin.this, "Cannot Sign in.Please check the form and try again.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mpassword.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                String email = memail.getText().toString();
                if (TextUtils.isEmpty(email)) {
                    memail.setError("Email cannot be empty");
                    return;
                } else {
                    mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(adminLogin.this, "Check your email to reset password", Toast.LENGTH_SHORT).show();
                            }


                        }
                    });
                }
            }
        });
    }
}
