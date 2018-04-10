package com.example.win.sampleproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;

/**
 * Created by win on 27/03/2018.
 */

public class adminViewall extends AppCompatActivity {
    private Firebase mref;
    private ListView mlistview;
    private ArrayList<String> mComplaint = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminstart);

        Firebase.setAndroidContext(this);
        final String selectedyear=getIntent().getExtras().getString("year");
        final String selectedsec=getIntent().getExtras().getString("section");
        final String selectedtype=getIntent().getExtras().getString("settype");


        mref = new Firebase("https://sampleproject-69e25.firebaseio.com/Complaints").child(selectedyear).child(selectedsec).child(selectedtype);

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

            }
        });

    }
}
