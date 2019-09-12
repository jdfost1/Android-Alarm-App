package com.example.josh.androidalarm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button newAlarmButton = (Button) findViewById(R.id.newAlarmButton);
        newAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newAlarmIntent = new Intent(MainActivity.this, AlarmActivity.class);
                MainActivity.this.startActivity(newAlarmIntent);
            }
        });

        Button newTimerButton = (Button) findViewById(R.id.newTimerButton);
        newTimerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newTimerIntent = new Intent (MainActivity.this, SetTimerActivity.class);
                MainActivity.this.startActivity(newTimerIntent);
            }
        });
    }
}
