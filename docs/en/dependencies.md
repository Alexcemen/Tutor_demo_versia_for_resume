# Dependencies

Version catalog: `gradle/libs.versions.toml`

## Core platform

| Library                          | Version | Purpose                        |
|----------------------------------|---------|--------------------------------|
| `androidx-core-ktx`              | 1.16.0  | Android core Kotlin extensions |
| `androidx-activity-compose`      | 1.10.1  | Compose activity integration   |
| `androidx-lifecycle-runtime-ktx` | 2.9.1   | Lifecycle-aware coroutines     |
| `androidx-appcompat`             | 1.7.1   | App compatibility APIs         |

## UI

| Library                       | Version    | Purpose                     |
|-------------------------------|------------|-----------------------------|
| `androidx-compose-bom`        | 2025.06.00 | Compose version alignment   |
| `androidx-material3`          | BOM        | Material 3 components       |
| `androidx.compose.ui`         | BOM        | Core Compose UI             |
| `androidx-media3-common-ktx`  | 1.8.0      | Media playback helpers      |
| `com.google.android.material` | 1.13.0     | Additional Material widgets |

## Navigation

| Library                                              | Version | Purpose                                |
|------------------------------------------------------|---------|----------------------------------------|
| `androidx.navigation3`                               | 1.0.0   | Typed back stack navigation            |
| `androidx.lifecycle:lifecycle-viewmodel-navigation3` | 2.10.0  | ViewModel integration for Navigation 3 |

## Dependency injection

| Library                     | Version | Purpose                      |
|-----------------------------|---------|------------------------------|
| `koin-bom`                  | 4.0.4   | Koin version alignment       |
| `koin-android`              | BOM     | Runtime dependency injection |
| `koin-androidx-compose`     | BOM     | Compose integration          |
| `koin-androidx-workmanager` | BOM     | WorkManager integration      |

## Persistence and serialization

| Library                      | Version | Purpose                        |
|------------------------------|---------|--------------------------------|
| `androidx-room-runtime`      | 2.7.1   | Local database                 |
| `androidx-room-compiler`     | 2.7.1   | Room code generation           |
| `gson`                       | 2.11.0  | JSON parsing                   |
| `kotlinx-serialization-core` | 1.8.1   | Serializable navigation models |

## Background work and utilities

| Library                      | Version | Purpose                   |
|------------------------------|---------|---------------------------|
| `androidx-work-runtime-ktx`  | 2.10.1  | Scheduled background work |
| `kotlinx-coroutines-android` | 1.10.1  | Coroutines on Android     |
| `timber`                     | 5.0.1   | Logging                   |

## Store integrations

| Library                          | Version          | Purpose                   |
|----------------------------------|------------------|---------------------------|
| `com.google.android.play:review` | 2.0.2            | Google Play in-app review |
| `ru.rustore.sdk:review`          | `bom:2025.02.01` | RuStore in-app review     |
| `com.yandex.android:mobileads`   | 7.17.0           | Advertising SDK           |

## Testing

| Library                                | Version | Purpose                  |
|----------------------------------------|---------|--------------------------|
| `junit`                                | 4.13.2  | Unit testing             |
| `androidx.test.ext:junit`              | 1.2.1   | Instrumented test runner |
| `androidx.test.espresso:espresso-core` | 3.6.1   | UI testing               |
