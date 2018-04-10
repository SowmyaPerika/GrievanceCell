package com.example.win.sampleproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class WelcomeActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference mUserDatabase;
    private FirebaseUser mcurrentUser;
    private TextView textView,textView4,textView5,textView6;
    private Button post,viewstatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        setTitle("Welcome Page");

        Firebase.setAndroidContext(this);

        textView=(TextView)findViewById(R.id.textView);
        textView4=(TextView)findViewById(R.id.textView4);
        textView5=(TextView)findViewById(R.id.textView5);
        textView6=(TextView)findViewById(R.id.textView6);

        mAuth=FirebaseAuth.getInstance();
        mcurrentUser=FirebaseAuth.getInstance().getCurrentUser();
        if (mcurrentUser == null) {
            sendToStart();
        }else {
            String current_uid = mcurrentUser.getUid();
            mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(current_uid);

            mUserDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    String rollnumber = (String) dataSnapshot.child("rollnumber").getValue();
                    String specificyear = (String) dataSnapshot.child("year").getValue();
                    String specificsection = (String) dataSnapshot.child("section").getValue();
                    textView4.setText(rollnumber);
                    textView5.setText(specificyear);
                    textView6.setText(specificsection);
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }

        post=(Button)findViewById(R.id.post);
        viewstatus=(Button)findViewById(R.id.status);
        //final String roll= textView4.getText().toString();

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String roll= textView4.getText().toString();
                final String obtainyear= textView5.getText().toString();
                final String obtainsection= textView6.getText().toString();
                if(!roll.equals("")) {
                    Intent intent = new Intent(WelcomeActivity.this, StartActivity.class);
                    intent.putExtra("id",getIntent().getExtras().getString("mailid"));
                    intent.putExtra("rollnum", roll);
                    intent.putExtra("passingyear", obtainyear);
                    intent.putExtra("passingsection", obtainsection);
                    startActivity(intent);
                }else
                {
                    Toast.makeText(WelcomeActivity.this, "Please wait until your roll gets displayed", Toast.LENGTH_SHORT).show();
                }
            }
        });
        viewstatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String roll = textView4.getText().toString();
                String obtainyear = textView5.getText().toString();
                String obtainsection = textView6.getText().toString();
                if (!roll.equals("")) {
                    Intent intent = new Intent(WelcomeActivity.this, viewStatusActivity.class);

                    intent.putExtra("rollnum", roll);
                    intent.putExtra("passingyear", obtainyear);
                    intent.putExtra("passingsection", obtainsection);
                    startActivity(intent);
                }else
                {
                    Toast.makeText(WelcomeActivity.this, "Please wait until your roll gets displayed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser=mAuth.getCurrentUser();
        if (currentUser==null){
            sendToStart();
        }
    }
    private void sendToStart() {
        Intent i=new Intent(WelcomeActivity.this,MainActivity.class);
        startActivity(i);
        finish();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId()==R.id.main_logout_btn){
            FirebaseAuth.getInstance().signOut();
            sendToStart();
        }
        return true;
    }
}



