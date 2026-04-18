# PetPlanner

PetPlanner is an Android app for dog and cat owners to plan daily tasks, track health, and manage pet care routines.

## Project status
- UI scaffold created with a main dashboard screen
- Kotlin-based Android app module configured
- Gradle build files added

## How to run
1. Install Java 17
2. Install Android SDK command-line tools
3. Install Android SDK packages:
   - `platform-tools`
   - `platforms;android-34`
   - `build-tools;34.0.0`
4. Install Gradle or use the Gradle wrapper if added

From the project root:

```bash
cd ~/GitHub/petplanner
gradle clean assembleDebug
```

To install the debug APK on a connected Android device:

```bash
adb devices
adb install -r app/build/outputs/apk/debug/app-debug.apk
```

If you want Gradle to build and install directly (device must be connected):

```bash
gradle installDebug
```

If you add the Gradle wrapper later, use:

```bash
./gradlew clean assembleDebug
./gradlew installDebug
```

### Device troubleshooting
- Make sure USB debugging is enabled on your phone.
- Approve the computer prompt on the device when it asks for USB debugging permission.
- If `adb devices` shows no devices, try a different USB cable or port.
- On Linux, you may need udev rules for the device manufacturer.

## Notes
- Do not commit the `.gradle/` directory
- Keep local files like `petplanner.docx`, `petplanner.md`, and `petplanner-plan.md` ignored
- The app module is configured under `app/build.gradle`
