# BMI Calculator

[![Latest Release](https://img.shields.io/github/v/release/SameerShelarr/BMICalculator?style=flat-square&color=blue)](https://github.com/SameerShelarr/BMICalculator/releases/latest)

A native **Android** app that calculates **Body Mass Index (BMI)** from height and weight, keeps a **local history** of calculations, and lets you **adjust height** with a custom picker. The UI is built with **Jetpack Compose** and **Material 3**.
---
### 🚀 Get Started
- 
- **Play Store:** [Releases / latest](https://play.google.com/store/apps/details?id=com.sameershelar.bmicalculator)
- **Privacy Policy:** [PRIVACY_POLICY.md](PRIVACY_POLICY.md)

---

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
| Home — portrait, empty history | <img width="320" height="880" alt="Screenshot_20260322_183530" src="https://github.com/user-attachments/assets/ad4705be-c455-46b7-ae76-61b9dd4c7c37" /> |
| Home — portrait, with history | <img width="320" height="880" alt="Screenshot_20260322_183555" src="https://github.com/user-attachments/assets/6dc01a29-06f5-4d71-8390-b8b8e0e5bdc1" /> |
| Home — landscape | <img width="880" height="340" alt="Screenshot_20260322_210648" src="https://github.com/user-attachments/assets/e3ece740-64a7-4fee-aeda-dabeb5f50135" /> |
| Height picker | <img width="320" height="880" alt="Screenshot_20260322_183515" src="https://github.com/user-attachments/assets/3bd552fc-3e7f-47ab-8f23-6038ba5837a3" /> |
| Clear history dialog | <img width="320" height="880" alt="Screenshot_20260322_183654" src="https://github.com/user-attachments/assets/7558a1b3-878a-4e47-8f2e-6c126eff7cb3" /> |
| Delete entry + snackbar (optional) | <img width="320" height="880" alt="Screenshot_20260322_183643" src="https://github.com/user-attachments/assets/4f3683c2-8372-428a-a001-4889b6ad949b" /> |

**Light theme**:

| Screen / state | Placeholder |
|----------------|-------------|
| Home — Light with history | <img width="320" height="880" alt="Screenshot_20260322_184132" src="https://github.com/user-attachments/assets/41f5f29c-76ba-4843-8ede-7328e460da44" /> |

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

