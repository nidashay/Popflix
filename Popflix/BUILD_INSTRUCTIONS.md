# Popflix - Build Instructions (Command Line Only)

## Complete guide to building Popflix without Android Studio

### Prerequisites Installation

#### 1. Install JDK 17

**Windows:**
```bash
# Download from https://adoptium.net/temurin/releases/?version=17
# Or use winget:
winget install EclipseAdoptium.Temurin.17.JDK

# Verify installation
java -version
```

**macOS:**
```bash
# Using Homebrew
brew install openjdk@17

# Link Java
sudo ln -sfn /opt/homebrew/opt/openjdk@17/libexec/openjdk.jdk /Library/Java/JavaVirtualMachines/openjdk-17.jdk

# Verify installation
java -version
```

**Linux (Ubuntu/Debian):**
```bash
sudo apt update
sudo apt install openjdk-17-jdk

# Verify installation
java -version
```

#### 2. Install Android SDK Command-Line Tools

**All Platforms:**
```bash
# Create Android SDK directory
mkdir -p ~/Android/Sdk
cd ~/Android/Sdk

# Download command-line tools (replace with latest version)
# Visit: https://developer.android.com/studio#command-tools

# For Linux/Mac:
wget https://dl.google.com/android/repository/commandlinetools-linux-11076708_latest.zip
unzip commandlinetools-linux-11076708_latest.zip
mv cmdline-tools latest
mkdir -p cmdline-tools && mv latest cmdline-tools/

# For Windows (PowerShell):
Invoke-WebRequest -Uri "https://dl.google.com/android/repository/commandlinetools-win-11076708_latest.zip" -OutFile "commandlinetools.zip"
Expand-Archive commandlinetools.zip
Move-Item -Path "cmdline-tools" -Destination "latest"
New-Item -ItemType Directory -Path "cmdline-tools" | Out-Null
Move-Item -Path "latest" -Destination "cmdline-tools/"
```

**Set Environment Variables:**

**Linux/Mac (~/.bashrc or ~/.zshrc):**
```bash
export ANDROID_HOME=$HOME/Android/Sdk
export PATH=$PATH:$ANDROID_HOME/cmdline-tools/latest/bin
export PATH=$PATH:$ANDROID_HOME/platform-tools
export PATH=$PATH:$ANDROID_HOME/build-tools/34.0.0
```

**Windows (System Properties > Environment Variables):**
```
ANDROID_HOME = C:\Users\YourUsername\AppData\Local\Android\Sdk
PATH = %PATH%;%ANDROID_HOME%\cmdline-tools\latest\bin;%ANDROID_HOME%\platform-tools
```

**Install Required SDK Components:**
```bash
# Accept licenses
yes | sdkmanager --licenses

# Install required components
sdkmanager "platform-tools"
sdkmanager "platforms;android-34"
sdkmanager "build-tools;34.0.0"

# List installed packages
sdkmanager --list_installed
```

### 3. Set Up Local Properties

Create `local.properties` in the Popflix root directory:

**Linux/Mac:**
```bash
echo "sdk.dir=$HOME/Android/Sdk" > local.properties
```

**Windows:**
```powershell
echo "sdk.dir=C:\\Users\\YourUsername\\AppData\\Local\\Android\\Sdk" > local.properties
```

### 4. Configure API Keys (Optional)

Edit `app/build.gradle.kts` and replace placeholder values:

```kotlin
// TODO: Replace with your actual TMDB API key
buildConfigField("String", "TMDB_API_KEY", "\"YOUR_ACTUAL_TMDB_API_KEY\"")

// TODO: Replace with your actual server endpoints
buildConfigField("String", "MEGA_CLOUD_BASE_URL", "\"https://your-megacloud-api.com/\"")
buildConfigField("String", "WOOTLY_BASE_URL", "\"https://your-wootly-api.com/\"")
buildConfigField("String", "VIDSRC_BASE_URL", "\"https://your-vidsrc-api.com/\"")
buildConfigField("String", "MFC_BASE_URL", "\"https://your-mfc-api.com/\"")
```

### 5. Build the APK

```bash
# Navigate to project directory
cd Popflix

# Make gradlew executable (Linux/Mac only)
chmod +x gradlew

# Clean and build debug APK
./gradlew clean assembleDebug

# Or for release APK (requires signing config)
# ./gradlew assembleRelease
```

### 6. Locate Generated APK

**Debug APK:**
```
Popflix/app/build/outputs/apk/debug/app-debug.apk
```

**Release APK:**
```
Popflix/app/build/outputs/apk/release/app-release.apk
```

### 7. Install on Device (Optional)

```bash
# Connect device via USB with debugging enabled
# List connected devices
adb devices

# Install APK
adb install app/build/outputs/apk/debug/app-debug.apk
```

## Project Structure

```
Popflix/
├── build.gradle.kts              # Root build configuration
├── settings.gradle.kts           # Project settings
├── gradle.properties             # Gradle properties
├── local.properties              # SDK location (create manually)
├── gradle/wrapper/
│   └── gradle-wrapper.properties
├── app/
│   ├── build.gradle.kts          # App module build config
│   ├── proguard-rules.pro        # ProGuard rules
│   └── src/main/
│       ├── AndroidManifest.xml
│       ├── java/com/popflix/
│       │   ├── PopflixApplication.kt
│       │   ├── MainActivity.kt
│       │   ├── data/
│       │   │   ├── local/        # Room DB, DataStore
│       │   │   ├── remote/       # Retrofit APIs, models
│       │   │   └── source/       # VideoSource adapters
│       │   ├── di/               # Hilt dependency injection
│       │   ├── domain/           # Domain models
│       │   └── ui/               # Compose UI
│       │       ├── theme/        # Theme, colors, typography
│       │       ├── navigation/   # Navigation setup
│       │       ├── components/   # Reusable components
│       │       └── screens/      # All screen composables
│       └── res/                  # Resources
│           ├── drawable/         # Vector drawables, logos
│           ├── values/           # strings, colors, themes
│           ├── xml/              # Backup rules
│           └── mipmap-*/         # App icons
└── BUILD_INSTRUCTIONS.md
```

## Troubleshooting

### Common Issues:

1. **"SDK location not found"**
   - Create `local.properties` with correct SDK path

2. **"Java version mismatch"**
   - Ensure JDK 17 is installed and JAVA_HOME points to it

3. **"Build failed with error 65000"**
   - Increase Gradle heap: Edit `gradle.properties`, set `org.gradle.jvmargs=-Xmx4096m`

4. **"Missing Android platform"**
   - Run: `sdkmanager "platforms;android-34"`

5. **"KSP errors"**
   - Clean build: `./gradlew clean`

## Features Implemented

✅ Modular VideoSource interface with adapters for:
   - Mega Cloud
   - Wootly
   - VidSrc
   - My Family Cinema
✅ Auto-fallback between sources
✅ Room Database for local caching
✅ DataStore for preferences
✅ Jetpack Compose UI with Material3
✅ Dark cinematic theme
✅ Animated splash screen
✅ Bottom navigation (Home, Search, Categories, Watchlist, Settings)
✅ Media3 ExoPlayer integration
✅ Credits screen with developer info
✅ Settings disclaimer
✅ ProGuard rules for APK shrinking

## TODO Items (Marked in Code)

- Replace TMDB API key placeholder
- Implement actual video source API integrations
- Add content fetching logic in repositories
- Implement full search functionality
- Add watchlist persistence
- Complete player controls (quality, subtitles, speed)

---

**Developed by Clinton Gethi**
Website: clintongethi.fun
Instagram: @clint_be_me
