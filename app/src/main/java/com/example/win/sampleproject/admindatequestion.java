package com.example.win.sampleproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;

/**
 * Created by win on 27/03/2018.
 */

public class admindatequestion extends AppCompatActivity {
    private Firebase mref;
    private ListView mlistview;
    private ArrayList<String> mComplaint = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminstart);

        Firebase.setAndroidContext(this);
        String givendating=getIntent().getExtras().getString("dates");
        String givenyear=getIntent().getExtras().getString("years");
        mref = new Firebase("https://sampleproject-69e25.firebaseio.com/Dates").child(givendating).child(givenyear);

        mlistview = (ListView) findViewById(R.id.listview);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mComplaint);
        mlistview.setAdapter((ListAdapter) arrayAdapter);

        mref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                final String value = dataSnapshot.getValue(String.class);
                mComplaint.add(value);
                arrayAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {


            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Toast.makeText(admindatequestion.this, " No Complaints", Toast.LENGTH_SHORT).show();
            }
        });
       /* mlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String question= mComplaint.get(i);
                Intent it=new Intent(admindatequestion.this,adminAnswer.class);
                it.putExtra("date",selectedsec);
                it.putExtra("year", selectedyear);
                it.putExtra("settype",selectedtype);

                it.putExtra("quest",question);
                startActivity(it);
            }
        });*/


    }
}
