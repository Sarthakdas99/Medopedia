package com.sarthak.medopedia.activities;

import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sarthak.medopedia.R;
import com.sarthak.medopedia.db.AlarmDBHelper;
import com.sarthak.medopedia.pojos.Alarm;

import java.util.ArrayList;
import java.util.Calendar;

public class AlarmActivity extends AppCompatActivity {

    private MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        Button button = findViewById(R.id.stop_alarm_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        TextView medicineName = findViewById(R.id.medicine_name);

        Calendar rightNow = Calendar.getInstance();
        int currentHour = rightNow.get(Calendar.HOUR_OF_DAY);
        int currentMinute = rightNow.get(Calendar.MINUTE);

        AlarmDBHelper alarmDBHelper = new AlarmDBHelper(AlarmActivity.this);
        ArrayList<Alarm> alarms = alarmDBHelper.getAlarms();
        for (Alarm alarm : alarms) {
            if (alarm.getHour() == currentHour && alarm.getMinute() == currentMinute) {
                medicineName.append(alarm.getName() + "\n");
            }
        }
        try {
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
            mp = MediaPlayer.create(getApplicationContext(), notification);
            mp.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        try {
            mp.stop();
            mp.release();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
