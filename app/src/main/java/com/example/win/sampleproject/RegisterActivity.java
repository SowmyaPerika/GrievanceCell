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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    private EditText mreg_rollno,mreg_email,mreg_password,mreg_year,mreg_section;
    private Button mCreateBtn;
    private ProgressDialog mProgress;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private TextView malreadyreg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar().setTitle("Create Account");


        mProgress=new ProgressDialog(this);

        mAuth= FirebaseAuth.getInstance();

        mreg_rollno=(EditText) findViewById(R.id.reg_rollno);
        mreg_email=(EditText) findViewById(R.id.reg_email);
        mreg_password=(EditText) findViewById(R.id.reg_password);
        mreg_year=(EditText) findViewById(R.id.reg_year);
        mreg_section=(EditText) findViewById(R.id.reg_section);
        malreadyreg=(TextView)findViewById(R.id.alreadyreg);
        mCreateBtn=(Button)findViewById(R.id.reg_create_btn);

        malreadyreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(i);
            }
        });

        mCreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String roll=mreg_rollno.getText().toString().trim().toLowerCase();
                String email=mreg_email.getText().toString();
                String password=mreg_password.getText().toString();
                String enteredyear=mreg_year.getText().toString().trim().toUpperCase();
                String enteredsection=mreg_section.getText().toString().trim().toUpperCase();
                if (!TextUtils.isEmpty(email) || !TextUtils.isEmpty(password) || !TextUtils.isEmpty(roll)
                        || !TextUtils.isEmpty(enteredsection)|| !TextUtils.isEmpty(enteredyear)) {

                    if (roll.startsWith("147") || roll.startsWith("157") ||
                            roll.startsWith("167") || roll.startsWith("177")&& (roll.substring(5,8).equals("a05"))) {
                        // ||(roll.substring(5,8).equals("a04"))||(roll.substring(5,8).equals("a01"))||(roll.substring(5,8).equals("a03"))) {
                        if (enteredyear.equals("IV")||enteredyear.equals("III")||enteredyear.equals("II")||enteredyear.equals("I"))
                        {
                            if(enteredsection.equals("A")||enteredsection.equals("B")||enteredsection.equals("C")||enteredsection.equals("D")) {
                                if (roll.length() == 10) {
                                    mProgress.setTitle("Registering User");
                                    mProgress.setMessage("Please wait while  we create your account");
                                    mProgress.setCanceledOnTouchOutside(false);
                                    mProgress.show();

                                    register_user(roll, enteredsection, enteredyear, email, password);
                                } else {
                                    mreg_rollno.setError("Roll length should be 10");
                                }
                            }else{
                                mreg_section.setError("Enter correct Section ");
                            }

                        } else {
                            mreg_year.setError("Year should be in Roman letters");
                        }
                    }else {
                        mreg_rollno.setError("Roll Number should be correct");
                    }

                } else {
                    Toast.makeText(RegisterActivity.this, "Please Fill ALL The Details", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    public  void register_user(final String roll, final String enteredsection,final String enteredyear,
                               final String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser current_user=FirebaseAuth.getInstance().getCurrentUser();
                            String uid=current_user.getUid();
                            mDatabase= FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
                            HashMap<String,String> usermap=new HashMap<>();
                            usermap.put("email",email);
                            usermap.put("rollnumber",roll);
                            usermap.put("year",enteredyear);
                            usermap.put("section",enteredsection);
                            verification();
                            mDatabase.setValue(usermap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        mProgress.dismiss();
                                        Intent mainIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                                        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(mainIntent);
                                        finish();
                                    }
                                }
                            });

                        } else {

                            mProgress.hide();
                            Toast.makeText(RegisterActivity.this, "Registration Failed ", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

    private void verification() {
        final FirebaseUser user=mAuth.getCurrentUser();
        user.sendEmailVerification().addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                findViewById(R.id.reg_create_btn).setEnabled(true);
                if (task.isSuccessful()){
                    Toast.makeText(RegisterActivity.this, "Verification Email sent..Please check your mail"+user.getEmail(), Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(RegisterActivity.this, "Failed to send verification Email", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    }

