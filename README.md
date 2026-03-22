# BMI Calculator

A native **Android** app that calculates **Body Mass Index (BMI)** from height and weight, keeps a **local history** of calculations, and lets you **adjust height** with a custom picker. The UI is built with **Jetpack Compose** and **Material 3**.

> **Note:** BMI is a general screening metric. It is not a medical diagnosis. Consult a healthcare professional for personal health decisions.

---

## Features

- **Metric input** — Height in **centimeters** (100–300 cm via the scrollable picker), weight in **kilograms** (decimal allowed).
- **BMI calculation** — Uses the standard formula: **BMI = weight (kg) ÷ height (m)²**. Results are rounded to **one decimal** place.
- **Latest BMI card** — Shows the most recent calculated value on the home screen.
- **Persistent height** — Saved with **DataStore Preferences** (default **150 cm** if never set).
- **History** — Each calculation is stored in **Room** with BMI, weight, height, and timestamp; list is ordered **newest first**.
- **History actions**
  - Delete a single entry (with a **snackbar** and **Undo**).
  - Clear **all** history via confirmation **dialog**.
- **Edit height** — Opens a dedicated screen with a **custom `Canvas`-based height picker** and a forward action to return home.
- **Responsive layout** — **Portrait**: single column. **Landscape**: inputs and latest BMI on one side, history on the other.
- **Navigation** — **Navigation 3** with **Kotlin Serialization** `NavKey` routes and slide/fade transitions.
- **Exit guard** — On the root screen, **press back twice** within two seconds to exit (toast prompt on first press).
- **Theming** — **Material 3** with a **high-contrast** light/dark palette following system theme. (`MainActivity` disables dynamic/Material You color so the branded contrast scheme is used consistently.)

---

## Screenshots

| Screen / state | Placeholder |
|----------------|-------------|
| Home — portrait, empty history | ![Home — portrait (empty history)](docs/screenshots/home-portrait-empty.png) |
| Home — portrait, with history | ![Home — portrait (with history)](docs/screenshots/home-portrait-history.png) |
| Home — landscape | ![Home — landscape](docs/screenshots/home-landscape.png) |
| Height picker | ![Height input / picker](docs/screenshots/height-picker.png) |
| Clear history dialog | ![Clear history confirmation](docs/screenshots/dialog-clear-history.png) |
| Delete entry + snackbar (optional) | ![Snackbar undo after delete](docs/screenshots/snackbar-undo.png) |

**Dark theme** (optional extras):

| Screen / state | Placeholder |
|----------------|-------------|
| Home — dark | ![Home — dark theme](docs/screenshots/home-dark.png) |

---

## Tech stack

| Area | Choice |
|------|--------|
| Language | Kotlin |
| UI | Jetpack Compose, Material 3 |
| Min / target SDK | **28** / **36** (see `app/build.gradle.kts`) |
| DI | [Koin](https://insert-koin.io/) |
| Navigation | AndroidX **Navigation 3** + `kotlinx-serialization` for routes |
| Local preferences | DataStore Preferences |
| Local database | Room (KSP compiler) |
| Async | Kotlin coroutines, `StateFlow` |
| Quality | [ktlint](https://github.com/pinterest/ktlint) (Gradle plugin on subprojects) |
| Unit tests | JUnit 4, **Kotest**, **MockK**, `kotlinx-coroutines-test` |

---

## Project structure (high level)

```
app/src/main/java/com/sameershelar/bmicalculator/
├── BMICalculatorApplication.kt    # Koin startup
├── MainActivity.kt                 # Edge-to-edge, theme, NavigationRoot
├── data/                           # Room DB, DAO, entities, repositories
├── di/AppModule.kt                 # Koin module (DataStore, Room, ViewModels)
├── navigation/                     # Routes, NavDisplay / back stack
├── ui/
│   ├── components/                 # Pickers, cards, lists, dialogs
│   ├── screens/                    # HomeScreen, HeightInputScreen
│   ├── theme/                      # Colors, typography, BMICalculatorTheme
│   └── viewmodels/                 # HomeScreenViewModel, HeightInputScreenViewModel
```

---

## Build & run

**Requirements**

- [Android Studio](https://developer.android.com/studio) (or compatible IDE) with a recent **Android SDK** matching `compileSdk` in `app/build.gradle.kts`.
- **JDK 11** (aligned with `compileOptions` in the app module).

**Steps**

1. Clone the repository and open the project root in Android Studio.
2. Let Gradle sync; the wrapper pins the Gradle version for this project.
3. Run the **`app`** configuration on an emulator or device (**API 28+**).

**Useful Gradle tasks**

- `./gradlew :app:assembleDebug` — debug APK  
- `./gradlew :app:assembleRelease` — release build (minify enabled)  
- `./gradlew ktlintCheck` — lint Kotlin sources per project setup  
- `./gradlew ktlintFormat` — lint format Kotlin sources per project setup  

**Tests**

- Unit tests: `./gradlew :app:testDebugUnitTest`  

---

## App identity

| Property | Value |
|----------|--------|
| Application ID | `com.sameershelar.bmicalculator` |
| Namespace | `com.sameershelar.bmicalculator` |
| Version (current) | `1.0` (`versionCode` 1) |

---

## Data & privacy

- **Height** is stored on-device in DataStore (`preferencesDataStore(name = "settings")`).
- **BMI history** is stored on-device in a Room database (`bmi_calculator_db`).
- No network APIs are used.

