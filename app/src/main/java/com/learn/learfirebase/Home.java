package com.learn.learfirebase;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by Boka on 20-Nov-16.
 */

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        MyFirebaseMessagingService.context = Home.this;

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isReg = pref.getBoolean("isRegistered", false);
        if(!isReg){
            Intent intent = new Intent(this, SignUp.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }

        boolean isNotification = pref.getBoolean("isNotification", false);
        if(isNotification){
            startActivity(new Intent(this, ResultActivity.class));
            finish();
        }else {
            Toast.makeText(this, "no notification", Toast.LENGTH_SHORT).show();
        }

        Button btn = (Button) findViewById(R.id.btnFeature);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this, ResultActivity.class));
                //testNotification();
            }
        });


    }

    private void testNotification() {
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);
        notificationBuilder.setContentTitle(getResources().getString(R.string.app_name));
        notificationBuilder.setContentText("test notification");
        notificationBuilder.setAutoCancel(true);
        notificationBuilder.setSmallIcon(R.mipmap.ic_launcher);

        Intent intent = new Intent(getApplicationContext(), ResultActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        notificationBuilder.setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notificationBuilder.build());
        Toast.makeText(this, "notification created..", Toast.LENGTH_SHORT).show();
    }
}
