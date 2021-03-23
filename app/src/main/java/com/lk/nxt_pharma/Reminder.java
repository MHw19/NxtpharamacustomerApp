package com.lk.nxt_pharma;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.lk.nxt_pharma.boardcast.AlertReceiver;

import java.util.Calendar;

public class Reminder extends AppCompatActivity {

    Button time,date,remind;
    TextView txttime,txtdate;

    Calendar timecCalendar;

      EditText takemedicine;

      public static  String fulltxt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);


        time=findViewById(R.id.timebtn);
        date=findViewById(R.id.datebtn);
        remind=findViewById(R.id.remindbtn);

        txttime=findViewById(R.id.txttime);
        txtdate=findViewById(R.id.txtdate);

        takemedicine=findViewById(R.id.txttakemedicine);









        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleTime();
            }
        });



        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleDate();
            }
        });


        remind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(timecCalendar !=null){

                    setAlarm(timecCalendar);
                    Toast.makeText(Reminder.this,"Alarm Calling",Toast.LENGTH_LONG).show();

                }



            }
        });






    }

    private void setAlarm(Calendar c) {


        fulltxt=takemedicine.getText().toString();

       // Toast.makeText(Reminder.this,fulltxt,Toast.LENGTH_LONG).show();
        AlarmManager alarmManager=(AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent intent=new Intent(Reminder.this,AlertReceiver.class);

        PendingIntent pendingIntent=PendingIntent.getBroadcast(this,1,intent,0);

       alarmManager.setExact(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(),pendingIntent);
        //alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(),1000,pendingIntent);





    }

    private void handleDate() {

        Calendar calendar=Calendar.getInstance();

        int YEAR=calendar.get(Calendar.YEAR);
        int Month=calendar.get(Calendar.MONTH);
        int Date=calendar.get(Calendar.DATE);


        DatePickerDialog datePickerDialog=new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                String date=year+"/"+month+"/"+dayOfMonth;


                Calendar calendar1=Calendar.getInstance();
                calendar1.set(Calendar.YEAR,year);
                calendar1.set(Calendar.MONTH,month);
                calendar1.set(Calendar.DATE,dayOfMonth);

                CharSequence datCharSequence=DateFormat.format("EEEE, dd MMM yyyy",calendar1);
                txtdate.setText(datCharSequence);




            }
        },YEAR,Month,Date);

        datePickerDialog.show();


    }

    private void handleTime() {


        Calendar calendar=Calendar.getInstance();

        int Hour=calendar.get(Calendar.HOUR);
        int Minutes=calendar.get(Calendar.MINUTE);

        Boolean is24Format= DateFormat.is24HourFormat(this);

        TimePickerDialog timePickerDialog=new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                String times=hourOfDay+":"+minute;



                timecCalendar=Calendar.getInstance();

                timecCalendar.set(Calendar.HOUR,hourOfDay);
                timecCalendar.set(Calendar.MINUTE,minute);



                CharSequence timeCharSequence=DateFormat.format("hh:mm a",timecCalendar);

                txttime.setText(timeCharSequence);



            }
        },Hour,Minutes,is24Format);

        timePickerDialog.show();





    }







}