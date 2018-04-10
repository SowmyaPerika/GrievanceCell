package com.example.win.sampleproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;

public class viewStatusActivity extends AppCompatActivity {
    private Firebase mref;
    private ListView mlistview;
    private ArrayList<String> mComplaints=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_status);


        Firebase.setAndroidContext(this);
        final String Roll=getIntent().getExtras().getString("rollnum");
        mref=new Firebase("https://sampleproject-69e25.firebaseio.com/").child("Rollwisecomplaint").child(Roll);
        mlistview =(ListView)findViewById(R.id.list);
        final ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,mComplaints);
        mlistview.setAdapter((ListAdapter) arrayAdapter);
        mref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String value=dataSnapshot.getKey();
                mComplaints.add(value);
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

        mlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String question= mComplaints.get(i);
                Intent it=new Intent(viewStatusActivity.this,complaintstatus.class);
                it.putExtra("quest",question);
                it.putExtra("rollnumber",Roll);
                startActivity(it);
            }
        });
    }
}
