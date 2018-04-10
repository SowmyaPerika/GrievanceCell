package com.example.win.sampleproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class adminAnswer extends AppCompatActivity {
    private TextView DisplayQuestion;
    private EditText EnterAnswer;
    private Button subAnswer;
    public static int k=0;


    private Firebase mref,mc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_answer);

        Firebase.setAndroidContext(this);
        final String selectedgivenyear=getIntent().getExtras().getString("year");
        final String selectedgivensec=getIntent().getExtras().getString("section");
        final String selectedgiventype=getIntent().getExtras().getString("settype");
        mref=new Firebase("https://sampleproject-69e25.firebaseio.com/").child("ComplaintAnswer").child(selectedgivenyear).child(selectedgivensec).child(selectedgiventype);

        String Display=getIntent().getExtras().getString("quest");
        DisplayQuestion=(TextView)findViewById(R.id.Dquestion);
        EnterAnswer=(EditText)findViewById(R.id.enterAnswer);
        subAnswer=(Button)findViewById(R.id.submitAnswer);
        DisplayQuestion.setText(Display);

        subAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String Answered = EnterAnswer.getText().toString();
                if (TextUtils.isEmpty(Answered)) {
                    EnterAnswer.setError("please,provide the answer what you thing is right");
                    return;
                }
                final String matching = DisplayQuestion.getText().toString();
                mref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String sub;

                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            sub = dataSnapshot1.getKey();

                            if (sub.equals(matching)) {
                                k=1;
                                mref.child(sub).setValue(Answered);
              /*                  Intent its = new Intent(adminAnswer.this, adminthank.class);
                                startActivity(its);
                                finish();
              */              }
                        }
                        if(k==1)
                        {
                            Intent its = new Intent(adminAnswer.this, adminthank.class);
                            startActivity(its);
                            finish();

                        }
                    }
                    @Override
                    public void onCancelled(FirebaseError firebaseError) {
                        Toast.makeText(adminAnswer.this, "No Complaints", Toast.LENGTH_SHORT).show();

                    }
                });
                mc=new Firebase("https://sampleproject-69e25.firebaseio.com/").child("Rollwisecomplaint");

                mc.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String subj,s;
                        for(DataSnapshot datasnapshot1:dataSnapshot.getChildren()) {
                            s=datasnapshot1.getKey();
                            for(DataSnapshot dataSnapshot2:datasnapshot1.getChildren()) {
                                subj =dataSnapshot2.getKey();
                                if (subj.equals(matching)) {
                                    mc.child(s).child(subj).setValue(Answered);
                                }
                            }
                        }

                    }
                    @Override
                    public void onCancelled(FirebaseError firebaseError) {
                        Toast.makeText(adminAnswer.this, "No Complaints", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });
       /* if(k==1)
        {
            Intent its = new Intent(adminAnswer.this, adminthank.class);
            startActivity(its);
            finish();

        }*/
    }
}
