# Dependency Injection

## Framework

Tutor uses **Koin** for dependency injection.

Startup entry point:

- `TutorApplication`

Registered modules:

- `appModule`
- `dataBaseModule`
- `repositoryModule`
- `useCaseModule`
- `viewModelModule`

## `appModule`

Provides application-level services such as:

- `StateKeeper`
- `AppResource`
- application coroutine scope
- ad loaders and ad event listeners
- review flow manager
- notification helper and scheduler
- network monitor
- file intent registration
- clipboard wrapper
- locale manager

## `dataBaseModule`

Provides:

- `AppDatabase`
- DAO instances for tests, questions, answers, attempts, errors, and favorites

## `repositoryModule`

Binds repository interfaces to implementations and provides:

- Room-backed repositories
- shared-preferences-backed app settings
- central application error logger

## `useCaseModule`

Registers thin use-case wrappers over repository operations, including:

- creation and deletion use cases
- statistics counters
- test completion pipeline
- question retrieval
- shuffling helpers
- JSON import use cases

## `viewModelModule`

Registers:

- reducers as singletons
- screen ViewModels through Koin `viewModel`
- assisted parameters for screens that depend on navigation arguments

## Runtime selection

Store-specific services are selected at runtime with `BuildConfig.flavourName`, mainly for:

- review flow implementation
- advertising-related behavior
