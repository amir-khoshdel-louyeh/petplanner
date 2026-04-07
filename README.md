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

If you add the Gradle wrapper later, use:

```bash
./gradlew clean assembleDebug
```

## Notes
- Do not commit the `.gradle/` directory
- Keep local files like `petplanner.docx`, `petplanner.md`, and `petplanner-plan.md` ignored
- The app module is configured under `app/build.gradle`
