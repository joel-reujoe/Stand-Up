package com.example.joel.alarmmanager;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {


   private NotificationManager mNotificationManager;
   private static final int NOTIFICATION_ID=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ToggleButton alarmToggle=findViewById(R.id.alarmToggle);

        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        Intent notifyIntent=new Intent(this,AlarmReciever.class);

        boolean alarmUp=(PendingIntent.getBroadcast(this,0,notifyIntent,PendingIntent.FLAG_NO_CREATE))!=null;

        alarmToggle.setChecked(alarmUp);

        final PendingIntent notifyPendingIntent=PendingIntent.getBroadcast(this,NOTIFICATION_ID,notifyIntent,PendingIntent.FLAG_UPDATE_CURRENT);




        final AlarmManager alarmManager=(AlarmManager)getSystemService(ALARM_SERVICE);

        alarmToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                String toastMessage;
                if(b)
                {
                    long triggerTime = SystemClock.elapsedRealtime() + AlarmManager.INTERVAL_FIFTEEN_MINUTES;

                    long repeatInterval=AlarmManager.INTERVAL_FIFTEEN_MINUTES;

                    assert alarmManager != null;
                    alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,triggerTime,repeatInterval,notifyPendingIntent);



                    toastMessage=getString(R.string.alarm_on);
                }
                else
                {
                    assert alarmManager != null;
                    alarmManager.cancel(notifyPendingIntent);
                    mNotificationManager.cancelAll();
                    toastMessage=getString(R.string.alarm_off);
                }
                Toast.makeText(MainActivity.this, toastMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
