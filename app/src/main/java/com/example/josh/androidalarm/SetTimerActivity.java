package com.example.josh.androidalarm;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.GregorianCalendar;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Calendar;


import static edu.ilstu.it226.alarm.R.id.start_timer_textField;

public class SetTimerActivity extends AppCompatActivity {

    int minutes;
    int days;
    private Context context;
    private CountDownTimer countDownTimer;
    private TextView startTimerTextField;
    private Button setTimerButton;
    private TextClock textClock1;
    Context timerContext;

     //track if notification is already in task bar
    private boolean isNotificActive = false;
     //track notification
    int broadcastID = 100;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_timer);
        this.timerContext = this;

        //final Intent myIntent = new Intent(this.context, AlarmReceiver.class);

        //create "Set Timer" Button and initialize it to XML button
        setTimerButton = (Button) findViewById(R.id.start_timer_button);

        //initialize text field to xml textfield
         startTimerTextField = (TextView) findViewById(R.id.start_timer_textField);

        //create textClock component to display current time and timer minutes
        textClock1 = (TextClock) findViewById(R.id.textClock);




        setTimerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                minutes = Integer.parseInt(startTimerTextField.getText().toString());
                days = Integer.parseInt(daysTextField.getText().toString());
                minutes += days*1440;
                Long alertTime = new GregorianCalendar().getTimeInMillis() + (minutes*60)*1000;


                Intent intent = new Intent(timerContext, AlarmReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(timerContext, broadcastID, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, alertTime, pendingIntent);
                //showNotification();


                //switch that catches click and then starts the on screen timer method

                switch (v.getId()) {
                    case R.id.start_timer_button:
                        startOnScreenTimer(minutes);
                        break;


                }//end of switch case
            }
        });
    }//end of onCreate method

    //start on screen display timer inside timer activity
    public void startOnScreenTimer(int minutes) {

        //create timer object and pass in minutes from user input
        countDownTimer = new CountDownTimer(minutes  * 60 * 1000, 1000) {

           //On each tick display the minutes left and the seconds to the on screen display
            public void onTick(long millisUntilFinished) {
                int seconds = Integer.parseInt((((millisUntilFinished)/ 1000) % 60) + "");

                if (seconds < 10)
                    textClock1.setText("" + millisUntilFinished / 1000 / 60 + ":0" + seconds);
                else
                    textClock1.setText("" + millisUntilFinished / 1000 / 60 + ":" + seconds);
            }//end of OnTick


            //when timer is finished.. display the time is up and run the showNotification method
            public void onFinish() {
               // createNotification(Context context, String msg, String msgText, String msgAlert)
                //Intent intent = new Intent(this, AlarmReceiver.class);

               textClock1.setText("TIME IS UP!");
//                PendingIntent notificIntent = PendingIntent.getActivity(timerContext, broadcastID,
//                        new Intent(timerContext, MainActivity.class), 0);
//
//                android.support.v4.app.NotificationCompat.Builder mBuilder = new android.support.v4.app.NotificationCompat.Builder(timerContext)
//                        .setSmallIcon(R.drawable.ic_action_call)
//                        .setContentTitle("Timer")
//                        .setTicker("Test")
//                        .setContentText("Time is up");
//
//                mBuilder.setContentIntent(notificIntent);
//
//                mBuilder.setDefaults(android.support.v4.app.NotificationCompat.DEFAULT_VIBRATE);
//
//                mBuilder.setAutoCancel(true);
//
//                NotificationManager mNotificationManager =
//                        (NotificationManager) timerContext.getSystemService(Context.NOTIFICATION_SERVICE);
//
//                mNotificationManager.notify(1, mBuilder.build());

            }//end of onFinish method



        };//end of CountDown Timer Constructor
        countDownTimer.start();


    }
    NotificationManager notificationManager;









}//end of SetTimer Activity
