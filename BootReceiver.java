package com.dayi.notifier;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * 폰 부팅 완료 후 알림 재설정
 */
public class BootReceiver extends BroadcastReceiver {

    private static final String TAG = "BootReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            Log.d(TAG, "폰 부팅 완료, 알림 재설정");

            SharedPreferences sharedPreferences = context.getSharedPreferences(
                    "DayiNotifier",
                    Context.MODE_PRIVATE
            );

            // 알림이 활성화되어 있으면 다시 설정
            if (sharedPreferences.getBoolean("notifications_enabled", false)) {
                Intent mainIntent = new Intent(context, MainActivity.class);
                mainIntent.setAction("RESCHEDULE_ALARM");
                mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(mainIntent);
            }
        }
    }
}
