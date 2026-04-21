# Архитектура

## Структура модулей

```text
:app        Слой приложения, экраны, ViewModel, доменные модели, репозитории, DI-модули
:database   Room database, сущности, DAO, миграции
:common_ui  Переиспользуемые UI-компоненты, тема, state helper'ы, базовые MVI-классы
```

`:app` зависит от `:database` и `:common_ui`. Остальные модули друг от друга не зависят.

## Паттерн presentation-слоя

Экраны организованы по единой структуре:

```text
ui/screen/<screen_name>/
├── <ScreenName>Store.kt
├── <ScreenName>ViewModel.kt
├── <ScreenName>Reducer.kt
└── composable/
    ├── <ScreenName>Screen.kt
    └── <ScreenName>Content.kt
```

Для каждого экрана используются:

- `State` — внутреннее состояние
- `UiState` — состояние для UI
- `Event` — действия пользователя
- `Effect` — мутации состояния
- `SideEffect` — одноразовые эффекты, например навигация или диалоги

## Поток данных

```text
UI event
  -> ViewModel.onEvent(...)
  -> handleEvent(...)
  -> поток Effect
  -> handleEffect(...)
  -> обновление State
  -> Reducer.reduce(...)
  -> обновление UiState
  -> recomposition в Compose
```

Одноразовые действия отправляются через `SharedFlow` side effect'ов.

## Навигация

Проект использует `androidx.navigation3` с сериализуемыми navigation key и typed back stack.

Основной flow:

- Splash
- контейнер с нижней навигацией
- список тестов и карточка теста
- редактирование вопросов
- настройка прохождения
- активная тестовая сессия
- экран результатов
- профиль и статистика
- экран-подсказка по импорту и точки входа в JSON import

## Слои внутри `:app`

- UI-слой: Compose-экраны, UI-модели, ViewModel
- Доменный слой: use case'ы, интерфейсы репозиториев, доменные модели
- Data-слой: реализации репозиториев, маппинг, адаптеры хранения

## Сохранение состояния

`StateKeeper` сохраняет состояние экранов, которое должно переживать recreation activity и process
death.

## Инициализация приложения

`TutorApplication` запускает Koin, применяет локаль, создает notification channel и вручную
инициализирует WorkManager, потому что стандартный initializer отключен в manifest.
