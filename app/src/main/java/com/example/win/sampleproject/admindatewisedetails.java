package com.example.win.sampleproject;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by win on 27/03/2018.
 */

public class admindatewisedetails extends AppCompatActivity {
    private Button bt,bdate;
    private TextView datetext;
    private Spinner mtypespinner;
    String type;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admingivendetails);

        bt = (Button) findViewById(R.id.button3);
        bdate = (Button) findViewById(R.id.buttondate);
        datetext = (TextView) findViewById(R.id.date);
        mtypespinner = (Spinner) findViewById(R.id.typespinner);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(admindatewisedetails.this,
                R.array.complainttypespinner, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mtypespinner.setAdapter(adapter);
        mtypespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                type = (String) parent.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(admindatewisedetails.this, "Please select any year", Toast.LENGTH_LONG).show();
            }
        });
        bdate.setOnClickListener(new android.view.View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.N)
            @Override
            public void onClick(android.view.View view) {

                Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(admindatewisedetails.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setTitle("select date");
                dialog.show();
            }
        });
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = year + "-" + month + "-" + dayOfMonth;
                datetext.setText(date);
            }
        };

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String givendate = datetext.getText().toString();
                Intent i = new Intent(admindatewisedetails.this, admindatequestion.class);
                i.putExtra("dates", givendate);
                i.putExtra("years", type);
                // i.putExtra("section",sec);

                startActivity(i);
            }
        });
    }
}
