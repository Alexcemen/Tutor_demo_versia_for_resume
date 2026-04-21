# Внедрение зависимостей

## Фреймворк

Tutor использует **Koin** для dependency injection.

Точка входа:

- `TutorApplication`

Подключенные модули:

- `appModule`
- `dataBaseModule`
- `repositoryModule`
- `useCaseModule`
- `viewModelModule`

## `appModule`

Поставляет сервисы уровня приложения:

- `StateKeeper`
- `AppResource`
- application coroutine scope
- ad loader'ы и ad listener'ы
- review manager
- notification helper и scheduler
- network monitor
- регистрацию file intent
- clipboard wrapper
- locale manager

## `dataBaseModule`

Поставляет:

- `AppDatabase`
- DAO для тестов, вопросов, ответов, попыток, ошибок и избранного

## `repositoryModule`

Связывает интерфейсы с реализациями и поставляет:

- репозитории на базе Room
- настройки приложения на базе shared preferences
- центральный application error logger

## `useCaseModule`

Регистрирует тонкие use case-обертки над операциями репозиториев, включая:

- создание и удаление сущностей
- счетчики статистики
- pipeline завершения теста
- получение вопросов
- shuffling helper'ы
- use case'ы для JSON-импорта

## `viewModelModule`

Регистрирует:

- reducer'ы как singleton
- screen ViewModel через Koin `viewModel`
- assisted-параметры для экранов, зависящих от navigation arguments

## Runtime-выбор реализаций

Сервисы, завязанные на store, выбираются через `BuildConfig.flavourName`, в основном для:

- review flow
- рекламного поведения
