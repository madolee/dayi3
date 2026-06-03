# 프로젝트 구조

```
DayiNotifier/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/dayi/notifier/
│   │   │   │   ├── MainActivity.java              # 메인 액티비티 (UI)
│   │   │   │   ├── NotificationReceiver.java      # 알람 수신자
│   │   │   │   ├── NotificationService.java       # 알림 서비스
│   │   │   │   └── BootReceiver.java              # 부팅 완료 수신자
│   │   │   ├── res/
│   │   │   │   ├── layout/
│   │   │   │   │   └── activity_main.xml          # 메인 레이아웃
│   │   │   │   ├── values/
│   │   │   │   │   └── strings.xml                # 문자열 리소스
│   │   │   │   ├── mipmap/
│   │   │   │   │   ├── ic_launcher.png            # 앱 아이콘
│   │   │   │   │   └── ic_launcher_round.png      # 원형 아이콘
│   │   │   │   └── drawable/
│   │   │   │       ├── rounded_background.xml     # 둥근 배경
│   │   │   │       └── rounded_background_light.xml
│   │   │   └── AndroidManifest.xml                # 앱 매니페스트
│   │   ├── test/
│   │   │   └── java/com/dayi/notifier/
│   │   │       └── ExampleUnitTest.java           # 단위 테스트
│   │   └── androidTest/
│   │       └── java/com/dayi/notifier/
│   │           └── ExampleInstrumentedTest.java   # 계측 테스트
│   ├── build.gradle                               # 앱 빌드 설정
│   └── proguard-rules.pro                         # ProGuard 규칙
├── build.gradle                                   # 루트 빌드 설정
├── settings.gradle                                # Gradle 설정
├── gradle.properties                              # Gradle 속성
├── gradle/
│   └── wrapper/
│       ├── gradle-wrapper.jar
│       └── gradle-wrapper.properties
├── .gitignore
├── README.md                                      # 프로젝트 설명
├── BUILD_GUIDE.md                                 # 빌드 가이드
└── PROJECT_STRUCTURE.md                           # 이 파일
```

## 주요 파일 설명

### Java 소스 코드

#### MainActivity.java
- **역할**: 앱의 메인 UI 및 알림 설정 관리
- **주요 기능**:
  - 알림 활성화/비활성화
  - 테스트 알림 발송
  - 상태 표시
  - AlarmManager 설정

#### NotificationReceiver.java
- **역할**: AlarmManager에 의해 매일 9시에 트리거되는 BroadcastReceiver
- **주요 기능**:
  - 정시 알림 수신
  - NotificationService 시작

#### NotificationService.java
- **역할**: 백엔드 API 호출 및 알림 발송
- **주요 기능**:
  - 백엔드 API 통신
  - JSON 파싱
  - 알림 채널 생성
  - 푸시 알림 발송

#### BootReceiver.java
- **역할**: 폰 부팅 완료 후 알림 재설정
- **주요 기능**:
  - 부팅 완료 감지
  - 알림 재설정

### 리소스 파일

#### activity_main.xml
- 메인 액티비티의 UI 레이아웃
- 버튼, 텍스트뷰 등 UI 요소 정의

#### strings.xml
- 앱에서 사용되는 모든 문자열 리소스
- 다국어 지원 가능

#### AndroidManifest.xml
- 앱의 메타데이터 정의
- 권한 선언
- 액티비티, 서비스, 리시버 등록

### 빌드 설정

#### build.gradle (앱)
- 앱 빌드 설정
- 의존성 정의
- 컴파일 옵션

#### build.gradle (루트)
- 플러그인 설정
- 공통 빌드 설정

#### settings.gradle
- Gradle 저장소 설정
- 프로젝트 모듈 정의

## 의존성

### AndroidX
- `androidx.appcompat:appcompat:1.6.1` - 호환성 라이브러리
- `androidx.constraintlayout:constraintlayout:2.1.4` - 레이아웃
- `androidx.core:core:1.12.0` - 핵심 라이브러리

### Material Design
- `com.google.android.material:material:1.11.0` - Material 디자인 컴포넌트

### 네트워킹
- `com.squareup.okhttp3:okhttp:4.11.0` - HTTP 클라이언트

### JSON
- `com.google.code.gson:gson:2.10.1` - JSON 파싱

## 빌드 프로세스

```
1. 소스 코드 컴파일
   ↓
2. 리소스 컴파일
   ↓
3. DEX 파일 생성
   ↓
4. APK 패키징
   ↓
5. 서명
   ↓
6. APK 파일 생성
```

## 디렉토리 구조 설명

| 디렉토리 | 설명 |
|---------|------|
| `app/src/main/java` | Java 소스 코드 |
| `app/src/main/res` | 리소스 파일 (레이아웃, 이미지, 문자열 등) |
| `app/src/test` | 단위 테스트 코드 |
| `app/src/androidTest` | 계측 테스트 코드 |
| `gradle/wrapper` | Gradle 래퍼 |
| `build` | 빌드 결과 (생성됨) |

## 패키지 구조

```
com.dayi.notifier
├── MainActivity              # 메인 액티비티
├── NotificationReceiver      # 알람 수신자
├── NotificationService       # 알림 서비스
└── BootReceiver              # 부팅 완료 수신자
```

## 리소스 구조

```
res/
├── layout/
│   └── activity_main.xml     # 메인 레이아웃
├── values/
│   ├── strings.xml           # 문자열
│   ├── colors.xml            # 색상
│   ├── styles.xml            # 스타일
│   └── themes.xml            # 테마
├── drawable/
│   ├── ic_launcher.xml       # 벡터 드로어블
│   └── rounded_background.xml
├── mipmap/
│   ├── ic_launcher.png       # 앱 아이콘
│   └── ic_launcher_round.png
└── menu/
    └── menu_main.xml         # 메뉴
```

## 권한 구조

```
AndroidManifest.xml
├── INTERNET                  # 인터넷 접근
├── SCHEDULE_EXACT_ALARM      # 정확한 알람
├── POST_NOTIFICATIONS        # 알림 표시
├── RECEIVE_BOOT_COMPLETED    # 부팅 완료
└── WAKE_LOCK                 # 웨이크 락
```

## 빌드 변형

### Debug 빌드
- 최적화 없음
- 디버깅 정보 포함
- 서명 없음

### Release 빌드
- 최적화 적용
- ProGuard 적용
- 서명 필요

## 테스트 구조

### 단위 테스트 (Unit Test)
- `app/src/test/java`에 위치
- JVM에서 실행
- 빠른 실행

### 계측 테스트 (Instrumented Test)
- `app/src/androidTest/java`에 위치
- 에뮬레이터 또는 기기에서 실행
- Android 프레임워크 접근 가능

## 버전 관리

- **compileSdk**: 34 (최신 Android 버전)
- **targetSdk**: 34 (대상 Android 버전)
- **minSdk**: 26 (최소 Android 버전, Android 8.0)

## 추가 설정 파일

### gradle.properties
- Gradle 전역 설정
- JVM 메모리 설정
- 빌드 성능 최적화

### .gitignore
- Git에서 무시할 파일 목록
- 빌드 결과, IDE 설정 등

### proguard-rules.pro
- ProGuard 난독화 규칙
- 라이브러리 보존 설정

## 커스터마이징 포인트

### 1. 앱 아이콘 변경
- `app/src/main/res/mipmap/` 디렉토리의 이미지 파일 교체

### 2. 앱 이름 변경
- `strings.xml`의 `app_name` 값 수정

### 3. 색상 변경
- `values/colors.xml` 수정

### 4. 레이아웃 변경
- `layout/activity_main.xml` 수정

### 5. API URL 변경
- `NotificationService.java`의 `API_URL` 상수 수정

## 성능 최적화

### 1. 메모리 최적화
- 이미지 압축
- 불필요한 리소스 제거

### 2. 빌드 최적화
- ProGuard 활성화
- 불필요한 의존성 제거

### 3. 런타임 최적화
- 비동기 작업 사용
- 캐싱 활용

## 보안 고려사항

### 1. API 통신
- HTTPS 사용 권장
- 인증서 검증

### 2. 권한 관리
- 최소 권한 원칙
- 런타임 권한 요청

### 3. 데이터 저장
- SharedPreferences 암호화
- 민감한 정보 보호
