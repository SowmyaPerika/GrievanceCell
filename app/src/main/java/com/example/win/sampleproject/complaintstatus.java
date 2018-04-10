package com.example.win.sampleproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class complaintstatus extends AppCompatActivity {
    private TextView testquest,teststate;

    private Firebase mref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaintstatus);

        Firebase.setAndroidContext(this);
        testquest=(TextView)findViewById(R.id.textquestion);
        teststate=(TextView)findViewById(R.id.textstatus);
        final String givenquestion=getIntent().getExtras().getString("quest");
        final String Rolling=getIntent().getExtras().getString("rollnumber");
        mref=new Firebase("https://sampleproject-69e25.firebaseio.com/").child("Rollwisecomplaint").child(Rolling).child(givenquestion);
        testquest.setText(givenquestion);

        mref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String value= (String) dataSnapshot.getValue();

                if(value.equals("null"))
                {
                    teststate.setText("PENDING");
                }else{
                    teststate.setText(value);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Toast.makeText(complaintstatus.this, "No Complaints Are Avaliable", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
