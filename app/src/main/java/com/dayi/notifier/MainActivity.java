package com.dayi.notifier;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private TextView statusTextView;
    private TextView scheduleTextView;
    private Button enableButton;
    private Button disableButton;
    private Button testButton;

    private AlarmManager alarmManager;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // UI 요소 초기화
        statusTextView = findViewById(R.id.statusTextView);
        scheduleTextView = findViewById(R.id.scheduleTextView);
        enableButton = findViewById(R.id.enableButton);
        disableButton = findViewById(R.id.disableButton);
        testButton = findViewById(R.id.testButton);

        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        sharedPreferences = getSharedPreferences("DayiNotifier", Context.MODE_PRIVATE);

        // 버튼 리스너 설정
        enableButton.setOnClickListener(v -> enableNotifications());
        disableButton.setOnClickListener(v -> disableNotifications());
        testButton.setOnClickListener(v -> testNotification());

        // 초기 상태 업데이트
        updateStatus();

        // 알림 권한 요청 (Android 13 이상)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissions(new String[]{android.Manifest.permission.POST_NOTIFICATIONS}, 1);
        }
    }

    /**
     * 상태 업데이트
     */
    private void updateStatus() {
        boolean isEnabled = sharedPreferences.getBoolean("notifications_enabled", false);
        String status = isEnabled ? "활성화됨" : "비활성화됨";
        statusTextView.setText("상태: " + status);

        // 다음 알림 시간 표시
        Calendar nextAlarm = getNextAlarmTime();
        String nextTime = String.format("%02d:%02d", nextAlarm.get(Calendar.HOUR_OF_DAY), nextAlarm.get(Calendar.MINUTE));
        scheduleTextView.setText("다음 알림: 매일 09:00\n현재 시간: " + getCurrentTime());
    }

    /**
     * 알림 활성화
     */
    private void enableNotifications() {
        scheduleAlarm();
        sharedPreferences.edit().putBoolean("notifications_enabled", true).apply();
        updateStatus();
        Toast.makeText(this, "알림이 활성화되었습니다.", Toast.LENGTH_SHORT).show();
    }

    /**
     * 알림 비활성화
     */
    private void disableNotifications() {
        cancelAlarm();
        sharedPreferences.edit().putBoolean("notifications_enabled", false).apply();
        updateStatus();
        Toast.makeText(this, "알림이 비활성화되었습니다.", Toast.LENGTH_SHORT).show();
    }

    /**
     * 테스트 알림 발송
     */
    private void testNotification() {
        Intent intent = new Intent(this, NotificationService.class);
        intent.setAction("TEST_NOTIFICATION");
        startService(intent);
        Toast.makeText(this, "테스트 알림을 발송했습니다.", Toast.LENGTH_SHORT).show();
    }

    /**
     * 매일 9시에 실행되도록 알람 설정
     */
    private void scheduleAlarm() {
        Intent intent = new Intent(this, NotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 9);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        // 이미 9시를 지났으면 내일 9시로 설정
        if (calendar.getTimeInMillis() < System.currentTimeMillis()) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        // 매일 반복되도록 설정
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            alarmManager.setAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(),
                    pendingIntent
            );
        } else {
            alarmManager.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY,
                    pendingIntent
            );
        }
    }

    /**
     * 알람 취소
     */
    private void cancelAlarm() {
        Intent intent = new Intent(this, NotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );
        alarmManager.cancel(pendingIntent);
    }

    /**
     * 다음 알람 시간 계산
     */
    private Calendar getNextAlarmTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 9);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        if (calendar.getTimeInMillis() < System.currentTimeMillis()) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        return calendar;
    }

    /**
     * 현재 시간 반환
     */
    private String getCurrentTime() {
        Calendar calendar = Calendar.getInstance();
        return String.format("%02d:%02d:%02d",
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                calendar.get(Calendar.SECOND));
    }
}
