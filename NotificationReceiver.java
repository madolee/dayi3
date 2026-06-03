package com.dayi.notifier;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * 매일 9시에 알림을 발송하는 BroadcastReceiver
 * AlarmManager에 의해 트리거됨
 */
public class NotificationReceiver extends BroadcastReceiver {

    private static final String TAG = "NotificationReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "알림 시간 도래: " + System.currentTimeMillis());

        // NotificationService 시작
        Intent serviceIntent = new Intent(context, NotificationService.class);
        serviceIntent.setAction("SCHEDULED_NOTIFICATION");
        context.startService(serviceIntent);
    }
}
