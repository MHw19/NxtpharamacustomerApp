package com.lk.nxt_pharma.boardcast;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationCompat;

public class AlertReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {


        Log.i("Alarm","Alarm calling");

        NotificationHelper notificationHelper=new NotificationHelper(context);

        NotificationCompat.Builder nb=notificationHelper.getChannelNotification();

        notificationHelper.getManager().notify(1,nb.build());



    }
}
