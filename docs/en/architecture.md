# Architecture

## Module structure

```text
:app        Application layer, screens, ViewModels, domain models, repositories, DI modules
:database   Room database, entities, DAO interfaces, migrations
:common_ui  Reusable UI components, theme, state helpers, MVI base classes
```

`:app` depends on `:database` and `:common_ui`. The other two modules stay independent from each
other.

## Presentation pattern

The UI layer uses a consistent screen structure:

```text
ui/screen/<screen_name>/
├── <ScreenName>Store.kt
├── <ScreenName>ViewModel.kt
├── <ScreenName>Reducer.kt
└── composable/
    ├── <ScreenName>Screen.kt
    └── <ScreenName>Content.kt
```

Each screen defines:

- `State` for internal business state
- `UiState` for render-ready state
- `Event` for user actions
- `Effect` for state mutations
- `SideEffect` for one-shot actions such as navigation or dialogs

## Data flow

```text
UI event
  -> ViewModel.onEvent(...)
  -> handleEvent(...)
  -> Effect stream
  -> handleEffect(...)
  -> State update
  -> Reducer.reduce(...)
  -> UiState update
  -> Compose recomposition
```

One-shot actions are sent through a `SharedFlow` of side effects.

## Navigation

The app uses `androidx.navigation3` with serializable navigation keys and a typed back stack.

High-level flow:

- Splash
- Bottom navigation container
- Test list and test details
- Question editing
- Test setup
- Active test session
- Result review
- Profile and statistics
- Import guide and JSON import entry points

## Layers inside `:app`

- UI layer: Compose screens, UI models, ViewModels
- Domain layer: use cases, repository interfaces, domain models
- Data layer: repository implementations, mappers, persistence adapters

## State persistence

`StateKeeper` stores screen state that must survive activity recreation and process death.

## App startup

`TutorApplication` starts Koin, applies locale settings, creates the notification channel, and
initializes WorkManager manually because the default initializer is disabled in the manifest.

## Logging

The project uses `Timber` for runtime logging and screen-level diagnostics.
