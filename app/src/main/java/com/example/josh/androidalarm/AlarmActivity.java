package com.example.josh.androidalarm;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.IntDef;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;



public class AlarmActivity extends AppCompatActivity {
    AlarmManager alarmMgr;
    TimePicker timePicker;
    Context context;
    List<String> items;
    Button createAlarmButton;
    static String optionalMessage = "";
    EditText optionalMessageText;
    int alarmHour;
    int alarmMinute;
    int broadcastID = 0;
    ArrayList<Integer> days = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_alarm);
        this.context = this;

        optionalMessageText = (EditText) findViewById(R.id.optionalMessageTextField) ;

        timePicker = (TimePicker) findViewById(R.id.timePicker);
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                alarmHour = hourOfDay;
                alarmMinute = minute;
                System.out.println(alarmHour + " "  + alarmMinute);
            }
        });

        final MultiSpinner multiSpinner = (MultiSpinner) findViewById(R.id.multi_spinner);
        items = Arrays.asList(getResources().getStringArray(R.array.items));
        multiSpinner.setItems(items, getString(R.string.for_all), new MultiSpinner.MultiSpinnerListener() {
            @Override
            public void onItemsSelected(boolean[] selected) {
                if (selected[0] == true)
                    days.add(Calendar.SUNDAY);
                if (selected[1] == true)
                    days.add(Calendar.MONDAY);
                if (selected[2] == true)
                    days.add(Calendar.TUESDAY);
                if (selected[3] == true)
                    days.add(Calendar.WEDNESDAY);
                if (selected[4] == true)
                    days.add(Calendar.THURSDAY);
                if (selected[5] == true)
                    days.add(Calendar.FRIDAY);
                if (selected[6] == true)
                    days.add(Calendar.SATURDAY);
            }
        });

//        timeZoneSpinner = (Spinner) findViewById(R.id.timeZoneSpinner);

        createAlarmButton = (Button) findViewById(R.id.createAlarmButton);
        createAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(days.size() == 0) {
                    Intent intent = new Intent(context, AlarmReceiver.class);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(context, broadcastID, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                    scheduleAlarm(Calendar.getInstance().DAY_OF_WEEK, alarmHour, alarmMinute, pendingIntent);
                    System.out.println(Calendar.getInstance().get(Calendar.DAY_OF_WEEK));
                    broadcastID++;
                }
                else {
                    for (int i = 0; i < days.size(); i++) {
                        Intent intent = new Intent(context, AlarmReceiver.class);
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, broadcastID, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                        System.out.println("scheduling alarm");
                        scheduleAlarm(days.get(i), alarmHour, alarmMinute, pendingIntent);
                        broadcastID++;
                    }
                }

                if(optionalMessageText.getText().toString() != null)
                    optionalMessage = optionalMessageText.getText().toString();

                System.out.println("hour:" + timePicker.getHour() + " minute: " + timePicker.getMinute());
                System.out.println(Calendar.getInstance().getTimeInMillis());
                System.out.println(alarmMgr.getNextAlarmClock().getTriggerTime());
                Intent intent = new Intent(AlarmActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private void scheduleAlarm(int dayOfWeek, int hourOfDay, int minute, PendingIntent pendingIntent){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, dayOfWeek);
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);


        if(calendar.getTimeInMillis() < System.currentTimeMillis()){
            calendar.add(Calendar.DAY_OF_YEAR, 7);
        }

        System.out.println(calendar.getTimeInMillis());


        alarmMgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY * 7, pendingIntent);

    }


    public static String getOptionalMessage(){
        return optionalMessage;
    }


}

