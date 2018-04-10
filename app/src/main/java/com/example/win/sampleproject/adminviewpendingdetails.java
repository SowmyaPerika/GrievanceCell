package com.example.win.sampleproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class adminviewpendingdetails extends AppCompatActivity {
    private Button bt;
    private Spinner myearspinner,msectionspinner,mtypespinner;
    String year,sec,type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminviewpendingdetails);

        bt=(Button)findViewById(R.id.buttonid);
        mtypespinner=(Spinner)findViewById(R.id.complainttypespinner);
        myearspinner=(Spinner)findViewById(R.id.yearspinner);
        msectionspinner=(Spinner)findViewById(R.id.sectionspinner);



        ArrayAdapter<CharSequence> adapter= ArrayAdapter.createFromResource(adminviewpendingdetails.this,
                R.array.yearspinner,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        myearspinner.setAdapter(adapter);
        myearspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                year=(String) parent.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(adminviewpendingdetails.this,"Please select any year",Toast.LENGTH_LONG).show();
            }
        });

        ArrayAdapter<CharSequence> adapter2= ArrayAdapter.createFromResource(adminviewpendingdetails.this,
                R.array.sectionspinner,android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        msectionspinner.setAdapter(adapter2);
        msectionspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                sec=(String) parent.getItemAtPosition(i);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(adminviewpendingdetails.this,"Please select any section",Toast.LENGTH_LONG).show();

            }
        });

        ArrayAdapter<CharSequence> adapter3= ArrayAdapter.createFromResource(adminviewpendingdetails.this,
                R.array.complainttypespinner,android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mtypespinner.setAdapter(adapter3);
        mtypespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                type=(String) parent.getItemAtPosition(i);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(adminviewpendingdetails.this,"Please select any type",Toast.LENGTH_LONG).show();

            }
        });

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(adminviewpendingdetails.this,adminstart.class);
                i.putExtra("section",sec);
                i.putExtra("year", year);
                i.putExtra("settype",type);

                startActivity(i);
            }
        });
    }
}
