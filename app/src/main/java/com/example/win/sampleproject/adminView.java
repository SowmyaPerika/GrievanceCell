package com.example.win.sampleproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

public class adminView extends AppCompatActivity {
    private Button bview,bviewall,bdate,bgraph;
    int total;
    private Firebase mref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view);

        bview=(Button)findViewById(R.id.viewPending);
        bviewall=(Button)findViewById(R.id.update);
        bdate=(Button)findViewById(R.id.datewise);
        bgraph=(Button)findViewById(R.id.graph);
       /* mref=new Firebase("https://sampleproject-69e25.firebaseio.com/givencomplaints");
        mref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                //      int g = 0;
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    total = (int) dataSnapshot.getChildrenCount();
                    //        g++;

                }
                Toast.makeText(adminView.this, total+"", Toast.LENGTH_SHORT).show();
                i=total;
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
*/

        bdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(adminView.this,admindatewisedetails.class);
                startActivity(i);
            }
        });

        bview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(adminView.this,adminviewpendingdetails.class);

                startActivity(i);
            }
        });

        bviewall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(adminView.this,adminalldetails.class);
                startActivity(i);
            }
        });
       bgraph.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               mref=new Firebase("https://sampleproject-69e25.firebaseio.com/givencomplaints");
               mref.addChildEventListener(new ChildEventListener() {
                   @Override
                   public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                       //      int g = 0;
                       for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                           total = (int) dataSnapshot.getChildrenCount();
                           //        g++;

                       }
                       Toast.makeText(adminView.this, total+"", Toast.LENGTH_SHORT).show();
                     //  i=total;
                       Intent a=new Intent(adminView.this,adminviewgraph.class);
                       a.putExtra("t",total);
                       startActivity(a);
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
              /* Intent a=new Intent(adminView.this,adminviewgraph.class);
            //   a.putExtra("t",i);
               startActivity(a);*/
           }
       });
    }
}
