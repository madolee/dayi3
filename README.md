# Dayi Notifier - 근무 알림 앱

매일 아침 9시에 백엔드 서버에서 근무 정보를 받아 푸시 알림을 표시하는 안드로이드 앱입니다.

## 기능

- **자동 알림**: 매일 아침 9시에 자동으로 알림 발송
- **백엔드 연동**: REST API를 통해 서버에서 근무 정보 조회
- **근무 상태 표시**: 오전, 오후, 야근, 휴일 상태 표시
- **테스트 기능**: 즉시 테스트 알림 발송 가능
- **부팅 후 복구**: 폰 재부팅 후 자동으로 알림 재설정

## 근무 상태 코드

| 코드 | 상태 | 설명 |
|------|------|------|
| 8 | 오전 | 오전 근무 |
| 13 | 오후 | 오후 근무 |
| 21, 22 | 야근 | 야근 |
| 87, 88 | 휴일 | 휴일 |

## 시스템 요구사항

- **Android 버전**: 8.0 (API 26) 이상
- **인터넷**: 필수 (API 통신)
- **권한**: 알림, 인터넷, 정확한 알람

## 설치

### 1. APK 파일 다운로드

빌드된 APK 파일을 다운로드합니다.

### 2. 앱 설치

```bash
adb install app-release.apk
```

또는 파일 탐색기에서 APK를 폰으로 복사 후 탭하여 설치

### 3. 권한 허용

앱 실행 시 다음 권한을 허용합니다:
- 알림 표시
- 인터넷 접근

## 사용 방법

### 1. 앱 실행

폰의 앱 드로어에서 "근무 알림" 앱 선택

### 2. 알림 활성화

1. **알림 활성화** 버튼 클릭
2. 상태가 "활성화됨"으로 변경
3. 매일 아침 9시에 알림 수신

### 3. 테스트 알림

1. **테스트 알림** 버튼 클릭
2. 즉시 테스트 알림 수신
3. 백엔드 API 연결 확인

### 4. 알림 비활성화

1. **알림 비활성화** 버튼 클릭
2. 상태가 "비활성화됨"으로 변경

## 빌드 방법

### 필요한 도구

- Android Studio 최신 버전
- JDK 11 이상
- Android SDK API 34

### 빌드 단계

1. Android Studio에서 프로젝트 열기
2. **Build** → **Build Bundle(s) / APK(s)** → **Build APK(s)** 선택
3. 빌드 완료 대기
4. APK 파일 위치: `app/release/app-release.apk`

자세한 내용은 [BUILD_GUIDE.md](BUILD_GUIDE.md) 참고

## 아키텍처

### 주요 컴포넌트

1. **MainActivity**: 메인 UI 및 알림 설정
2. **NotificationReceiver**: AlarmManager에 의해 트리거되는 BroadcastReceiver
3. **NotificationService**: 백엔드 API 호출 및 알림 발송
4. **BootReceiver**: 폰 부팅 후 알림 재설정

### 동작 흐름

```
1. 사용자가 "알림 활성화" 클릭
   ↓
2. AlarmManager가 매일 9시에 NotificationReceiver 트리거
   ↓
3. NotificationReceiver가 NotificationService 시작
   ↓
4. NotificationService가 백엔드 API 호출
   ↓
5. API 응답 받아 알림 발송
   ↓
6. 사용자가 알림 수신
```

## API 연동

### 백엔드 API

**엔드포인트**: `http://182.221.61.30:3000/api/today`

**응답 예시**:
```json
{
  "date": "2026-06-02",
  "day": 2,
  "code": 13,
  "status": "오후",
  "type": "afternoon",
  "timestamp": "2026-06-02T09:46:00.000Z"
}
```

### API URL 변경

`NotificationService.java`의 다음 줄을 수정합니다:

```java
private static final String API_URL = "http://182.221.61.30:3000/api/today";
```

## 설정

### 알림 시간 변경

`MainActivity.java`의 `scheduleAlarm()` 메서드에서:

```java
calendar.set(Calendar.HOUR_OF_DAY, 9);  // 9시를 원하는 시간으로 변경
```

### 알림 채널 설정

`NotificationService.java`의 `createNotificationChannel()` 메서드에서 설정 변경 가능

## 문제 해결

### 알림이 오지 않음

1. 앱이 활성화되어 있는지 확인
2. 백엔드 서버가 실행 중인지 확인
3. 폰의 알림 설정 확인
4. 인터넷 연결 확인

### API 연결 오류

1. 백엔드 서버 주소 확인
2. 방화벽 설정 확인
3. 로그 확인: `adb logcat | grep NotificationService`

### 앱이 실행되지 않음

1. Android 버전 확인 (8.0 이상 필요)
2. 필수 권한 허용 확인
3. 앱 캐시 삭제 후 재설치

## 로그 확인

```bash
# 앱 로그 확인
adb logcat | grep NotificationService

# 로그 저장
adb logcat > logcat.log
```

## 라이선스

MIT

## 지원

문제가 발생하면 로그를 확인하고 다음을 확인하세요:

1. 백엔드 서버 상태: `curl http://182.221.61.30:3000/health`
2. API 응답: `curl http://182.221.61.30:3000/api/today`
3. 폰의 인터넷 연결
4. 앱의 알림 권한 설정

## 버전 정보

- **버전**: 1.0.0
- **최소 API**: 26 (Android 8.0)
- **대상 API**: 34 (Android 14)
- **컴파일 SDK**: 34

## 변경 사항

### v1.0.0 (2026-06-02)
- 초기 릴리스
- 매일 9시 자동 알림
- 백엔드 API 연동
- 테스트 알림 기능
