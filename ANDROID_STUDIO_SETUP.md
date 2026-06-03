# Android Studio 프로젝트 설정 가이드

이 문서는 제공된 소스 코드를 Android Studio에서 실행 가능한 프로젝트로 변환하는 방법을 설명합니다.

## 1단계: Android Studio 설치

### Windows/Mac/Linux

1. [Android Studio 공식 웹사이트](https://developer.android.com/studio) 방문
2. 최신 버전 다운로드
3. 설치 마법사 따라 설치
4. Android SDK 설치 (API 34 권장)

## 2단계: 프로젝트 구조 생성

### 디렉토리 구조 생성

```bash
mkdir -p DayiNotifier/app/src/main/java/com/dayi/notifier
mkdir -p DayiNotifier/app/src/main/res/layout
mkdir -p DayiNotifier/app/src/main/res/values
mkdir -p DayiNotifier/app/src/main/res/drawable
mkdir -p DayiNotifier/app/src/main/res/mipmap
mkdir -p DayiNotifier/app/src/test/java/com/dayi/notifier
mkdir -p DayiNotifier/app/src/androidTest/java/com/dayi/notifier
mkdir -p DayiNotifier/gradle/wrapper
```

### 파일 배치

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
│   │   │   │   ├── drawable/
│   │   │   │   └── mipmap/
│   │   │   └── AndroidManifest.xml
│   │   ├── test/
│   │   └── androidTest/
│   ├── build.gradle
│   └── proguard-rules.pro
├── build.gradle (루트)
├── settings.gradle
├── gradle.properties
└── gradle/wrapper/
    ├── gradle-wrapper.jar
    └── gradle-wrapper.properties
```

## 3단계: 필수 파일 생성

### 1. build.gradle (루트)

파일 위치: `DayiNotifier/build.gradle`

```gradle
plugins {
    id 'com.android.application' version '8.1.0' apply false
}

task clean(type: Delete) {
    delete rootProject.buildDir
}
```

### 2. build.gradle (앱)

파일 위치: `DayiNotifier/app/build.gradle`

제공된 `build.gradle` 파일 사용

### 3. settings.gradle

파일 위치: `DayiNotifier/settings.gradle`

제공된 `settings.gradle` 파일 사용

### 4. gradle.properties

파일 위치: `DayiNotifier/gradle.properties`

```properties
# Project-wide Gradle settings.
org.gradle.jvmargs=-Xmx2048m -XX:MaxPermSize=512m
org.gradle.parallel=true
org.gradle.caching=true
```

### 5. .gitignore

파일 위치: `DayiNotifier/.gitignore`

```
# Gradle
.gradle/
build/
gradle/wrapper/gradle-wrapper.jar

# Android Studio
.idea/
*.iml
*.iws
*.ipr
local.properties

# OS
.DS_Store
Thumbs.db

# Build
bin/
gen/
out/
```

### 6. proguard-rules.pro

파일 위치: `DayiNotifier/app/proguard-rules.pro`

```
# OkHttp
-dontwarn okhttp3.**
-dontwarn okio.**

# Gson
-keep class com.google.gson.** { *; }
-keepclassmembers class * {
    @com.google.gson.annotations.SerializedName <fields>;
}

# AndroidX
-keep class androidx.** { *; }
```

## 4단계: Gradle Wrapper 설정

### gradle-wrapper.properties

파일 위치: `DayiNotifier/gradle/wrapper/gradle-wrapper.properties`

```properties
distributionBase=GRADLE_USER_HOME
distributionPath=wrapper/dists
distributionUrl=https\://services.gradle.org/distributions/gradle-8.0-bin.zip
zipStoreBase=GRADLE_USER_HOME
zipStorePath=wrapper/dists
```

### gradle-wrapper.jar

공식 Gradle 저장소에서 다운로드하거나 Android Studio에서 자동 생성

## 5단계: Android Studio에서 프로젝트 열기

1. Android Studio 실행
2. **File** → **Open** 선택
3. `DayiNotifier` 디렉토리 선택
4. **OK** 클릭

### 프로젝트 로드

- Gradle 동기화 자동 시작
- 완료 대기 (첫 로드는 시간 소요)

## 6단계: SDK 설정 확인

### SDK Manager 열기

1. **Tools** → **SDK Manager** 선택
2. 다음 항목 확인:
   - **SDK Platforms**: API 34 설치됨
   - **SDK Tools**: 최신 버전 설치됨

### 필수 SDK 버전

- **Compile SDK**: 34
- **Target SDK**: 34
- **Min SDK**: 26 (Android 8.0)

## 7단계: 에뮬레이터 설정 (선택 사항)

### 에뮬레이터 생성

1. **Tools** → **Device Manager** 선택
2. **Create Virtual Device** 클릭
3. 기기 선택 (Pixel 4 권장)
4. API 레벨 선택 (API 34 권장)
5. 설정 확인 후 **Finish** 클릭

### 에뮬레이터 실행

1. Device Manager에서 에뮬레이터 선택
2. 재생 버튼 클릭
3. 에뮬레이터 시작 대기

## 8단계: 앱 실행

### 디버그 모드 실행

1. **Run** → **Run 'app'** 선택 (또는 Shift+F10)
2. 에뮬레이터 또는 기기 선택
3. **OK** 클릭
4. 앱 설치 및 실행

### 실행 결과

- 앱이 에뮬레이터/기기에 설치됨
- "근무 알림" 앱이 실행됨
- 메인 화면이 표시됨

## 9단계: 테스트

### 앱 기능 테스트

1. **알림 활성화** 버튼 클릭
2. 상태가 "활성화됨"으로 변경 확인
3. **테스트 알림** 버튼 클릭
4. 알림 수신 확인

### 로그 확인

1. **View** → **Tool Windows** → **Logcat** 선택
2. 앱 로그 확인
3. 오류 메시지 검색

## 10단계: APK 빌드

### Release APK 생성

1. **Build** → **Build Bundle(s) / APK(s)** → **Build APK(s)** 선택
2. 빌드 진행률 확인
3. 빌드 완료 후 **Locate** 클릭
4. APK 파일 위치: `app/release/app-release.apk`

### Debug APK 생성

1. **Build** → **Build Bundle(s) / APK(s)** → **Build APK(s)** 선택
2. 빌드 완료 후 APK 파일 위치: `app/debug/app-debug.apk`

## 문제 해결

### Gradle 동기화 오류

```
Error: Could not find com.android.tools.build:gradle:...
```

**해결:**
1. **File** → **Settings** → **Appearance & Behavior** → **System Settings** → **Android SDK**
2. SDK 업데이트 확인
3. **Sync Now** 클릭

### 컴파일 오류

```
Error: Unsupported Java version
```

**해결:**
1. **File** → **Project Structure** → **SDK Location**
2. JDK 버전 확인 (11 이상 필요)
3. JDK 경로 설정

### 에뮬레이터 실행 오류

```
Error: HAXM is not installed
```

**해결:**
1. Intel HAXM 설치 (Windows/Mac)
2. 또는 다른 에뮬레이터 사용 (ARM 기반)

### APK 설치 오류

```
Error: INSTALL_FAILED_INVALID_APK
```

**해결:**
1. 기존 앱 제거: `adb uninstall com.dayi.notifier`
2. APK 다시 빌드
3. 다시 설치

## 개발 팁

### 1. 핫 리로드

코드 변경 후 자동 재빌드:
1. **File** → **Settings** → **Build, Execution, Deployment** → **Instant Run**
2. "Enable Instant Run" 체크

### 2. 디버깅

1. 코드에 중단점 설정 (줄 번호 클릭)
2. **Run** → **Debug 'app'** 선택
3. 디버거 창에서 변수 확인

### 3. 성능 프로파일링

1. **Run** → **Profile 'app'** 선택
2. CPU, 메모리, 네트워크 사용량 확인

### 4. 레이아웃 미리보기

1. `activity_main.xml` 파일 열기
2. **Design** 탭 클릭
3. 레이아웃 미리보기 확인

## 다음 단계

1. 앱 기능 테스트
2. 필요시 코드 수정
3. Release APK 빌드
4. 기기에 설치 및 테스트
5. Google Play Store에 배포 (선택 사항)

## 추가 리소스

- [Android 개발자 문서](https://developer.android.com)
- [Android Studio 사용 설명서](https://developer.android.com/studio)
- [Gradle 문서](https://gradle.org/documentation)
- [AndroidX 마이그레이션 가이드](https://developer.android.com/jetpack/androidx/migrate)

## 지원

문제가 발생하면:

1. 로그 확인 (Logcat)
2. 공식 문서 검색
3. Stack Overflow 검색
4. Android 커뮤니티 포럼 참고
