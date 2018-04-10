package com.example.win.sampleproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

public class adminstart extends AppCompatActivity {
    private Firebase mref,mchi;
    private ListView mlistview;
    private ArrayList<String> mComplaints=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminstart);

        Firebase.setAndroidContext(this);
        final String selectedyear=getIntent().getExtras().getString("year");
        final String selectedsec=getIntent().getExtras().getString("section");
        final String selectedtype=getIntent().getExtras().getString("settype");

        mref=new Firebase("https://sampleproject-69e25.firebaseio.com/Complaints").child(selectedyear).child(selectedsec).child(selectedtype);

        mlistview =(ListView)findViewById(R.id.listview);

        final ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,mComplaints);
        mlistview.setAdapter((ListAdapter) arrayAdapter);

        mref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                final String value=dataSnapshot.getValue(String.class);
                mchi=new Firebase("https://sampleproject-69e25.firebaseio.com/ComplaintAnswer").child(selectedyear).child(selectedsec).child(selectedtype).child(value);
                mchi.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        String ans= (String) dataSnapshot.getValue();
                        if(ans.equals("null"))
                        {
                            mComplaints.add(value);
                            arrayAdapter.notifyDataSetChanged();
                        }
                    }


                    @Override
                    public void onCancelled(FirebaseError firebaseError) {
                        Toast.makeText(adminstart.this, "No Complaints", Toast.LENGTH_SHORT).show();
                    }
                });

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
                Intent it=new Intent(adminstart.this,adminAnswer.class);
                it.putExtra("section",selectedsec);
                it.putExtra("year", selectedyear);
                it.putExtra("settype",selectedtype);

                it.putExtra("quest",question);
                startActivity(it);
            }
        });

    }
}
