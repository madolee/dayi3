# Dayi Notifier APK 빌드 가이드

## 개요

이 가이드는 Dayi Notifier 안드로이드 앱을 빌드하여 APK 파일을 생성하는 방법을 설명합니다.

## 요구사항

- **Android Studio** 최신 버전
- **Java Development Kit (JDK)** 11 이상
- **Android SDK** API 34 (최소 API 26)
- **Gradle** 8.0 이상

## 설치 방법

### 1단계: 프로젝트 구조 생성

```
DayiNotifier/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/dayi/notifier/
│   │   │   │   ├── MainActivity.java
│   │   │   │   ├── NotificationReceiver.java
│   │   │   │   ├── NotificationService.java
│   │   │   │   └── BootReceiver.java
│   │   │   ├── res/
│   │   │   │   ├── layout/
│   │   │   │   │   └── activity_main.xml
│   │   │   │   ├── values/
│   │   │   │   │   └── strings.xml
│   │   │   │   └── mipmap/
│   │   │   │       └── (앱 아이콘)
│   │   │   └── AndroidManifest.xml
│   │   └── test/
│   └── build.gradle
├── build.gradle
├── settings.gradle
└── gradle.properties
```

### 2단계: Android Studio에서 프로젝트 열기

1. Android Studio 실행
2. **File** → **Open** 선택
3. 프로젝트 디렉토리 선택
4. **OK** 클릭

### 3단계: 의존성 동기화

1. **File** → **Sync Now** 선택
2. Gradle 동기화 완료 대기

### 4단계: 에뮬레이터 또는 기기 연결

**에뮬레이터 사용:**
1. **Tools** → **Device Manager** 선택
2. 새 에뮬레이터 생성 (API 26 이상)
3. 에뮬레이터 시작

**실제 기기 사용:**
1. USB 케이블로 안드로이드 폰 연결
2. 개발자 옵션 활성화 (설정 → 정보 → 빌드 번호 7번 탭)
3. USB 디버깅 활성화

### 5단계: 앱 실행 (테스트)

1. **Run** → **Run 'app'** 선택 (또는 Shift+F10)
2. 에뮬레이터 또는 기기 선택
3. 앱이 설치되고 실행됨

## APK 빌드

### Release APK 생성

1. **Build** → **Build Bundle(s) / APK(s)** → **Build APK(s)** 선택
2. 빌드 완료 대기
3. 알림 창에서 **Locate** 클릭
4. APK 파일 위치: `app/release/app-release.apk`

### Debug APK 생성

1. **Build** → **Build Bundle(s) / APK(s)** → **Build APK(s)** 선택
2. 빌드 완료 대기
3. APK 파일 위치: `app/debug/app-debug.apk`

## APK 설치

### 에뮬레이터에 설치

```bash
adb install app/release/app-release.apk
```

### 실제 기기에 설치

```bash
adb install app/release/app-release.apk
```

또는 파일 탐색기에서 APK를 폰으로 복사 후 탭하여 설치

## 앱 사용 방법

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
3. 더 이상 알림을 받지 않음

## 설정 변경

### 백엔드 API URL 변경

`NotificationService.java`의 다음 줄을 수정합니다:

```java
private static final String API_URL = "http://182.221.61.30:3000/api/today";
```

예시:
```java
private static final String API_URL = "http://your-server.com:3000/api/today";
```

### 알림 시간 변경

`MainActivity.java`의 `scheduleAlarm()` 메서드에서:

```java
calendar.set(Calendar.HOUR_OF_DAY, 9);  // 9시를 원하는 시간으로 변경
calendar.set(Calendar.MINUTE, 0);       // 분 설정
```

## 문제 해결

### 빌드 오류

```
Error: Could not find com.android.tools.build:gradle:...
```

**해결:** Gradle 버전 업데이트
```bash
./gradlew wrapper --gradle-version=8.0
```

### APK 설치 오류

```
Error: INSTALL_FAILED_INVALID_APK
```

**해결:** 
1. 기존 앱 제거: `adb uninstall com.dayi.notifier`
2. APK 다시 설치

### 알림이 오지 않음

1. 앱이 활성화되어 있는지 확인
2. 백엔드 서버가 실행 중인지 확인
3. API URL이 올바른지 확인
4. 폰의 알림 설정 확인

### API 연결 오류

1. 폰이 인터넷에 연결되어 있는지 확인
2. 백엔드 서버 주소가 올바른지 확인
3. 방화벽 설정 확인
4. 로그 확인: `adb logcat | grep NotificationService`

## 로그 확인

```bash
# 모든 로그
adb logcat

# 특정 태그만 필터링
adb logcat NotificationService:V *:S

# 로그 저장
adb logcat > logcat.log
```

## 배포

### Google Play Store에 배포

1. Google Play Developer 계정 생성 (유료)
2. 앱 서명 설정
3. Release APK 생성
4. Google Play Console에 업로드
5. 심사 대기

### APK 직접 배포

1. Release APK 생성
2. 사용자에게 파일 전송
3. 사용자가 직접 설치

## 보안 설정

### 앱 서명

```bash
# 서명 키 생성
keytool -genkey -v -keystore my-release-key.keystore -keyalg RSA -keysize 2048 -validity 10000 -alias my-key-alias

# build.gradle에서 서명 설정
signingConfigs {
    release {
        storeFile file('my-release-key.keystore')
        storePassword 'password'
        keyAlias 'my-key-alias'
        keyPassword 'password'
    }
}
```

## 최적화

### ProGuard 설정

`build.gradle`:
```gradle
buildTypes {
    release {
        minifyEnabled true
        proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
    }
}
```

### APK 크기 축소

```gradle
android {
    bundle {
        density.enableSplit = true
        abi.enableSplit = true
    }
}
```

## 추가 정보

- [Android 개발자 문서](https://developer.android.com)
- [Android Studio 가이드](https://developer.android.com/studio)
- [AlarmManager 문서](https://developer.android.com/reference/android/app/AlarmManager)
- [Notification 문서](https://developer.android.com/guide/topics/ui/notifiers/notifications)

## 라이선스

MIT
