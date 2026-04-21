# Зависимости

Каталог версий: `gradle/libs.versions.toml`

## Базовая платформа

| Библиотека                       | Версия | Назначение                             |
|----------------------------------|--------|----------------------------------------|
| `androidx-core-ktx`              | 1.16.0 | Kotlin extensions для Android core API |
| `androidx-activity-compose`      | 1.10.1 | Интеграция Compose с Activity          |
| `androidx-lifecycle-runtime-ktx` | 2.9.1  | Lifecycle-aware coroutines             |
| `androidx-appcompat`             | 1.7.1  | Совместимость Android API              |

## UI

| Библиотека                    | Версия     | Назначение                         |
|-------------------------------|------------|------------------------------------|
| `androidx-compose-bom`        | 2025.06.00 | Выравнивание версий Compose        |
| `androidx-material3`          | BOM        | Material 3 компоненты              |
| `androidx.compose.ui`         | BOM        | Базовый Compose UI                 |
| `androidx-media3-common-ktx`  | 1.8.0      | Вспомогательные media API          |
| `com.google.android.material` | 1.13.0     | Дополнительные Material-компоненты |

## Навигация

| Библиотека                                           | Версия | Назначение                          |
|------------------------------------------------------|--------|-------------------------------------|
| `androidx.navigation3`                               | 1.0.0  | Typed back stack navigation         |
| `androidx.lifecycle:lifecycle-viewmodel-navigation3` | 2.10.0 | Интеграция ViewModel с Navigation 3 |

## Внедрение зависимостей

| Библиотека                  | Версия | Назначение                   |
|-----------------------------|--------|------------------------------|
| `koin-bom`                  | 4.0.4  | Выравнивание версий Koin     |
| `koin-android`              | BOM    | Runtime dependency injection |
| `koin-androidx-compose`     | BOM    | Интеграция с Compose         |
| `koin-androidx-workmanager` | BOM    | Интеграция с WorkManager     |

## Хранение и сериализация

| Библиотека                   | Версия | Назначение                      |
|------------------------------|--------|---------------------------------|
| `androidx-room-runtime`      | 2.7.1  | Локальная база данных           |
| `androidx-room-compiler`     | 2.7.1  | Генерация кода Room             |
| `gson`                       | 2.11.0 | Парсинг JSON                    |
| `kotlinx-serialization-core` | 1.8.1  | Сериализуемые navigation models |

## Фоновые задачи и утилиты

| Библиотека                   | Версия | Назначение                   |
|------------------------------|--------|------------------------------|
| `androidx-work-runtime-ktx`  | 2.10.1 | Фоновые задачи по расписанию |
| `kotlinx-coroutines-android` | 1.10.1 | Coroutines на Android        |
| `timber`                     | 5.0.1  | Логирование                  |

## Store-интеграции

| Библиотека                       | Версия           | Назначение                |
|----------------------------------|------------------|---------------------------|
| `com.google.android.play:review` | 2.0.2            | Google Play in-app review |
| `ru.rustore.sdk:review`          | `bom:2025.02.01` | RuStore in-app review     |
| `com.yandex.android:mobileads`   | 7.17.0           | Advertising SDK           |

## Тестирование

| Библиотека                             | Версия | Назначение               |
|----------------------------------------|--------|--------------------------|
| `junit`                                | 4.13.2 | Unit testing             |
| `androidx.test.ext:junit`              | 1.2.1  | Instrumented test runner |
| `androidx.test.espresso:espresso-core` | 3.6.1  | UI testing               |
