package com.dayi.notifier;

import android.app.IntentService;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 백엔드 API에서 데이터를 받아 알림을 발송하는 서비스
 */
public class NotificationService extends IntentService {

    private static final String TAG = "NotificationService";
    private static final String CHANNEL_ID = "dayi_channel";
    private static final int NOTIFICATION_ID = 1;

    // 백엔드 API URL (서버 주소로 변경 필요)
    private static final String API_URL = "http://182.221.61.30:3000/api/today";

    private OkHttpClient httpClient;
    private Gson gson;

    public NotificationService() {
        super("NotificationService");
        this.httpClient = new OkHttpClient();
        this.gson = new Gson();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "NotificationService 시작");

        // 알림 채널 생성
        createNotificationChannel();

        // API에서 데이터 받기
        String workStatus = fetchWorkStatus();

        // 알림 발송
        if (workStatus != null) {
            sendNotification(workStatus);
        } else {
            sendNotification("근무 정보를 가져올 수 없습니다.");
        }

        Log.d(TAG, "NotificationService 종료");
    }

    /**
     * 백엔드 API에서 오늘의 근무 상태 조회
     */
    private String fetchWorkStatus() {
        try {
            Request request = new Request.Builder()
                    .url(API_URL)
                    .build();

            Response response = httpClient.newCall(request).execute();

            if (response.isSuccessful() && response.body() != null) {
                String responseBody = response.body().string();
                Log.d(TAG, "API 응답: " + responseBody);

                // JSON 파싱
                JsonObject jsonObject = gson.fromJson(responseBody, JsonObject.class);
                String status = jsonObject.get("status").getAsString();
                int code = jsonObject.get("code").getAsInt();
                int day = jsonObject.get("day").getAsInt();

                return String.format("%d월 %d일 - %s (코드: %d)", 
                        6, day, status, code);
            } else {
                Log.e(TAG, "API 오류: " + response.code());
                return null;
            }
        } catch (Exception e) {
            Log.e(TAG, "API 통신 오류: " + e.getMessage());
            return null;
        }
    }

    /**
     * 알림 채널 생성 (Android 8.0 이상)
     */
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "근무 알림",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel.setDescription("매일 9시 근무 상태 알림");

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }

    /**
     * 알림 발송
     */
    private void sendNotification(String message) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("근무 알림")
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);

        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        if (notificationManager != null) {
            notificationManager.notify(NOTIFICATION_ID, builder.build());
            Log.d(TAG, "알림 발송: " + message);
        }
    }
}
