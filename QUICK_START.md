# 빠른 시작 가이드

## 개요

이 가이드는 Dayi Notifier APK 앱을 빠르게 설정하고 사용하는 방법을 설명합니다.

## 1단계: 파일 준비 (5분)

### 필요한 파일

다음 파일들이 포함되어 있습니다:

```
DayiNotifier/
├── MainActivity.java              # 메인 UI
├── NotificationReceiver.java      # 알람 수신
├── NotificationService.java       # 알림 서비스
├── BootReceiver.java              # 부팅 완료
├── AndroidManifest.xml            # 앱 설정
├── activity_main.xml              # 레이아웃
├── strings.xml                    # 문자열
├── build.gradle                   # 빌드 설정
├── settings.gradle                # Gradle 설정
├── README.md                      # 상세 설명
├── BUILD_GUIDE.md                 # 빌드 가이드
└── ANDROID_STUDIO_SETUP.md        # Android Studio 설정
```

## 2단계: Android Studio 설정 (10분)

### 2.1 Android Studio 설치

1. [Android Studio 다운로드](https://developer.android.com/studio)
2. 설치 마법사 따라 설치
3. Android SDK API 34 설치

### 2.2 프로젝트 구조 생성

```bash
# 프로젝트 디렉토리 생성
mkdir -p DayiNotifier/app/src/main/java/com/dayi/notifier
mkdir -p DayiNotifier/app/src/main/res/layout
mkdir -p DayiNotifier/app/src/main/res/values
mkdir -p DayiNotifier/gradle/wrapper

# 제공된 파일들을 해당 디렉토리에 복사
# Java 파일 → app/src/main/java/com/dayi/notifier/
# XML 파일 → app/src/main/res/layout/ 또는 values/
# build.gradle 등 → 루트 디렉토리
```

### 2.3 Android Studio에서 프로젝트 열기

1. Android Studio 실행
2. **File** → **Open** 선택
3. `DayiNotifier` 디렉토리 선택
4. **Sync Now** 대기 (자동 시작)

## 3단계: 앱 실행 (5분)

### 3.1 에뮬레이터 또는 기기 준비

**에뮬레이터 사용:**
- **Tools** → **Device Manager** → **Create Virtual Device**
- Pixel 4, API 34 선택
- 에뮬레이터 시작

**실제 기기 사용:**
- USB 케이블로 연결
- 개발자 옵션 활성화 (설정 → 정보 → 빌드 번호 7번 탭)
- USB 디버깅 활성화

### 3.2 앱 실행

1. **Run** → **Run 'app'** (또는 Shift+F10)
2. 에뮬레이터/기기 선택
3. 앱 설치 및 실행 대기

## 4단계: 앱 사용 (2분)

### 4.1 알림 활성화

1. 앱 실행
2. **알림 활성화** 버튼 클릭
3. 상태가 "활성화됨"으로 변경 확인

### 4.2 테스트 알림

1. **테스트 알림** 버튼 클릭
2. 즉시 알림 수신 확인
3. 백엔드 API 연결 확인

### 4.3 매일 9시 알림

- 앱이 활성화되면 매일 아침 9시에 자동으로 알림 수신
- 폰이 꺼져있어도 알림 수신 (Android 12 이상에서는 제한 있음)

## 5단계: APK 빌드 (10분)

### 5.1 Release APK 생성

1. **Build** → **Build Bundle(s) / APK(s)** → **Build APK(s)**
2. 빌드 진행 대기
3. **Locate** 클릭하여 APK 파일 확인
4. 위치: `app/release/app-release.apk`

### 5.2 APK 설치

**에뮬레이터:**
```bash
adb install app/release/app-release.apk
```

**실제 기기:**
```bash
adb install app/release/app-release.apk
```

또는 파일 탐색기에서 APK를 폰으로 복사 후 탭하여 설치

## 6단계: 설정 커스터마이징 (선택 사항)

### 6.1 백엔드 API URL 변경

`NotificationService.java` 파일에서:

```java
private static final String API_URL = "http://182.221.61.30:3000/api/today";
```

다른 서버로 변경:

```java
private static final String API_URL = "http://your-server.com:3000/api/today";
```

### 6.2 알림 시간 변경

`MainActivity.java` 파일의 `scheduleAlarm()` 메서드에서:

```java
calendar.set(Calendar.HOUR_OF_DAY, 9);  // 9를 원하는 시간으로 변경
```

### 6.3 앱 이름/아이콘 변경

- 앱 이름: `strings.xml`의 `app_name` 값 수정
- 앱 아이콘: `mipmap/` 디렉토리의 이미지 교체

## 문제 해결

### 알림이 오지 않음

1. 앱이 활성화되어 있는지 확인
2. 백엔드 서버 실행 확인: `curl http://182.221.61.30:3000/health`
3. 폰의 인터넷 연결 확인
4. 폰의 알림 설정 확인

### API 연결 오류

1. 백엔드 서버 주소 확인
2. 방화벽 설정 확인
3. 로그 확인: `adb logcat | grep NotificationService`

### 빌드 오류

1. Android SDK 업데이트 확인
2. JDK 버전 확인 (11 이상 필요)
3. Gradle 동기화: **File** → **Sync Now**

### 에뮬레이터 실행 안 됨

1. Intel HAXM 설치 (Windows/Mac)
2. 또는 ARM 기반 에뮬레이터 사용

## 주요 기능

| 기능 | 설명 |
|------|------|
| 자동 알림 | 매일 아침 9시에 자동으로 알림 발송 |
| 백엔드 연동 | REST API를 통해 서버에서 근무 정보 조회 |
| 테스트 기능 | 즉시 테스트 알림 발송 가능 |
| 부팅 복구 | 폰 재부팅 후 자동으로 알림 재설정 |
| 상태 표시 | 현재 알림 상태 및 다음 알림 시간 표시 |

## 근무 상태 코드

| 코드 | 상태 |
|------|------|
| 8 | 오전 근무 |
| 13 | 오후 근무 |
| 21, 22 | 야근 |
| 87, 88 | 휴일 |

## 백엔드 API

### 엔드포인트

```
GET http://182.221.61.30:3000/api/today
```

### 응답 예시

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

## 다음 단계

1. ✅ 앱 빌드 및 설치
2. ✅ 기본 기능 테스트
3. ✅ 백엔드 API 연결 확인
4. ✅ 매일 9시 알림 확인
5. ✅ Google Play Store 배포 (선택 사항)

## 추가 정보

- [상세 빌드 가이드](BUILD_GUIDE.md)
- [Android Studio 설정](ANDROID_STUDIO_SETUP.md)
- [프로젝트 구조](PROJECT_STRUCTURE.md)
- [README](README.md)

## 지원

문제가 발생하면:

1. 로그 확인: `adb logcat | grep NotificationService`
2. 공식 문서 참고: https://developer.android.com
3. 백엔드 서버 상태 확인: `curl http://182.221.61.30:3000/health`

## 소요 시간

| 단계 | 소요 시간 |
|------|---------|
| 파일 준비 | 5분 |
| Android Studio 설정 | 10분 |
| 앱 실행 | 5분 |
| 앱 사용 | 2분 |
| APK 빌드 | 10분 |
| **총 소요 시간** | **약 30분** |

## 완료!

이제 Dayi Notifier 앱을 사용할 수 있습니다. 매일 아침 9시에 자동으로 근무 정보 알림을 받으세요!
