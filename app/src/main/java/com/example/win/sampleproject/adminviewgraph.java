package com.example.win.sampleproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class adminviewgraph extends AppCompatActivity {
   private Firebase mref;
   // int d = 0, a = 0, b = 0, e, total;
   float c[] = new float[20];
    String name[] = {"Academics", "AdminIssues", "Canteen", "Others"};
//float c[]={23.8f,13.0f,8.9f,5.2f};
PieChart mchart;
    int k=0,l=0,m=0,n=0,total;
   String sub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminviewgraph);
        setTitle("Pie Diagram");
        Firebase.setAndroidContext(this);
        mref=new Firebase("https://sampleproject-69e25.firebaseio.com/");
    String val=getIntent().getExtras().getString("t");
    total=Integer.parseInt(val);
        Toast.makeText(this, val+""+total+"", Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "please rave", Toast.LENGTH_SHORT).show();
        final List<PieEntry> entry = new ArrayList<>();

        mref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                sub = dataSnapshot.getKey();
                if (sub.equals("Academics")) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        k++;

                    }
                  //  Toast.makeText(adminviewgraph.this, k+"coming value", Toast.LENGTH_SHORT).show();
                }else
                {
                    if(sub.equals("AdminIssues"))
                    {
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            l++;

                        }


                    }else
                    {
                        if(sub.equals("Canteen"))
                        {
                            for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                m++;

                            }


                        }else
                        {
                            if(sub.equals("Others"))
                            {
                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                    n++;

                                }


                            }
                        }
                    }
                }
              //  Toast.makeText(adminviewgraph.this, total+"", Toast.LENGTH_SHORT).show();
              c[0]=((k/total)*100);
                c[1]=((l/total)*100);
                c[2]=((m/total)*100);
                c[3]=((n/total)*100);

                for (int i = 0; i <= 3; i++) {
                    entry.add(new PieEntry(c[i], name[i]));
                }
                PieDataSet data = new PieDataSet(entry, "Grievance");
                data.setColors(ColorTemplate.COLORFUL_COLORS);
                PieData piedata = new PieData(data);
                piedata.setValueTextSize(35f);
                mchart = (PieChart) findViewById(R.id.chart);
                mchart.setData(piedata);
                mchart.invalidate();
                mchart.setCenterTextSize(35);
                mchart.setCenterTextSizePixels(20);
                mchart.animateY(1000);


            }
               // Toast.makeText(adminviewgraph.this, temp+"ok goy it", Toast.LENGTH_SHORT).show();

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
       // Toast.makeText(this, m+""+n+""+l+""+k+"", Toast.LENGTH_SHORT).show();

      /*  List<PieEntry> entry = new ArrayList<>();
        for (int i = 0; i <= 3; i++) {
            entry.add(new PieEntry(c[i], name[i]));
        }
        PieDataSet data = new PieDataSet(entry, "Grievance");
        data.setColors(ColorTemplate.COLORFUL_COLORS);
        PieData piedata = new PieData(data);
        piedata.setValueTextSize(35f);
        mchart = (PieChart) findViewById(R.id.chart);
        mchart.setData(piedata);
        mchart.invalidate();
        mchart.setCenterTextSize(35);
        mchart.setCenterTextSizePixels(20);
        mchart.animateY(1000);*/
    }
}