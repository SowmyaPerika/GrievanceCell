package com.example.win.sampleproject;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.Calendar;

public class StartActivity extends AppCompatActivity {
    private EditText editComplaint;
    private Button bpick,bsubmit;
    private TextView tdate,textView2;
    private Firebase mref,fordata;
    String Roll,Year,Section,type;
    public int e;
    private Spinner mspinner;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Firebase.setAndroidContext(this);

        editComplaint=(EditText)findViewById(R.id.complaintposted);
        bpick=(Button)findViewById(R.id.buttondate);
        bsubmit=(Button)findViewById(R.id.buttonsubmit);
        tdate=(TextView)findViewById(R.id.date);
        mspinner=(Spinner)findViewById(R.id.complainttypespinner);

        final String mail=getIntent().getExtras().getString("id");
      // Toast.makeText(this, mail+"", Toast.LENGTH_SHORT).show();
        mref=new Firebase("https://sampleproject-69e25.firebaseio.com/");


        ArrayAdapter<CharSequence> adapter= ArrayAdapter.createFromResource(StartActivity.this,
                R.array.complainttypespinner,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mspinner.setAdapter(adapter);
        mspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {



            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                type=(String) parent.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(StartActivity.this,"Please select complaint type", Toast.LENGTH_LONG).show();
            }
        });

        bpick.setOnClickListener(new android.view.View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.N)
            @Override
            public void onClick(android.view.View view) {
                Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(StartActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setTitle("select date");
                dialog.show();
            }
        });
        mDateSetListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month=month+1;
                String date=year+"-"+month+"-"+dayOfMonth;
                tdate.setText(date);
            }
        };
        /*try {
            BackgroundMail.newBuilder(this)
                    .withUsername("sowmya.sami904@gmail.com")
                    .withPassword("sowmya20215")
                    .withMailto("nihanth.m@hebeon.com")
                    .withType(BackgroundMail.TYPE_PLAIN)
                    .withSubject("this is the subject")
                    .withBody("this is the body")
                    .withOnSuccessCallback(new BackgroundMail.OnSuccessCallback() {
                        @Override
                        public void onSuccess() {
                            //do some magic
                        }
                    })
                    .withOnFailCallback(new BackgroundMail.OnFailCallback() {
                        @Override
                        public void onFail() {
                            //do some magic
                        }
                    })
                    .send();
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();

        }*/
        bsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String textdate=tdate.getText().toString();
                final String edit=editComplaint.getText().toString();


                if(TextUtils.isEmpty(textdate)){

                    tdate.setError("please,select the date");
                    return;
                }
                if(TextUtils.isEmpty(edit)) {
                    editComplaint.setError("enter your complaint");
                    return;
                }
                Intent intent=new Intent();
                PendingIntent pendingIntent=PendingIntent.getActivity(StartActivity.this,0, intent,0);
                NotificationCompat.Builder notificationCompat=new NotificationCompat.Builder(StartActivity.this)
                        .setSmallIcon(android.R.drawable.stat_notify_error)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher))
                        .setContentTitle("hello "+mail)
                        .setContentText("Your Grievance posted on "+textdate+" has been stored. Your complaint is "+edit);
                //.addAction("Reply",pendingIntent);
                //.setContentIntent(pendingIntent).getNotification();
                notificationCompat.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE);
                NotificationManagerCompat manager= NotificationManagerCompat.from(StartActivity.this);
                manager.notify(1,notificationCompat.build());

              mref.child("givencomplaints").addChildEventListener(new ChildEventListener() {

                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        e=0;
                        for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()) {
                            final String value = dataSnapshot1.getValue(String.class);
                            if (value.equals(edit)) {
                                e = 1;
                                break;
                            }

                        }
                        if(e==1)
                        {
                            Toast.makeText(StartActivity.this, "your complaint already exists", Toast.LENGTH_SHORT).show();

                        }else {
                            String id = mref.push().getKey();
                            Roll = getIntent().getExtras().getString("rollnum");
                            Year = getIntent().getExtras().getString("passingyear");
                            Section = getIntent().getExtras().getString("passingsection");
                            Firebase mchi = mref.child("Complaints").child(Year).child(Section).child(type).child(id);
                            mchi.setValue(edit);
                            mchi = mref.child("Rollwisecomplaint").child(Roll).child(edit);
                            mchi.setValue("null");
                            mchi = mref.child("Dates").child(textdate).child(type).child(id);
                            mchi.setValue(edit);
                            mchi = mref.child("ComplaintAnswer").child(Year).child(Section).child(type).child(edit);
                            mchi.setValue("null");
                            mchi = mref.child("givencomplaints").child("comp").child(id);
                            mchi.setValue(edit);
                            mchi=mref.child(type).child(id);
                            mchi.setValue(edit);

                            Intent i = new Intent(StartActivity.this, Conclusion.class);
                            startActivity(i);
                            finish();

                        }



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
                        Toast.makeText(StartActivity.this, "your complaint is not stored ", Toast.LENGTH_SHORT).show();
                    }
                });







                     /* String id=mref.push().getKey();

                      Firebase mchi = mref.child("Complaints").child(id);
                      mchi.setValue(edit);
                      mchi = mref.child("Rollwisecomplaint").child(Roll).child(edit);
                      mchi.setValue("null");
                      mchi = mref.child("Dates").child(textdate).child(id);
                      mchi.setValue(edit);
                      mchi = mref.child("ComplaintAnswer").child(edit);
                      mchi.setValue("null");

                      Intent i = new Intent(StartActivity.this, Conclusion.class);
                      startActivity(i);
                      finish();*/


            }
        });


    }
}
