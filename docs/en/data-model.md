# Data Model

## Overview

The persistence layer is built on Room and keeps the domain layer independent from database
entities.

```text
Domain models
  <-> repository mapping
Room entities and DAO interfaces
```

## Database

- Database class: `AppDatabase`
- Module: `:database`
- Current schema version: `4`

## Tables

### `test_table`

- `id`
- `title`
- `colorId`
- `imageId`
- `dateCreation`
- `dateLastTake`

### `question_table`

- `id`
- `question`
- `testId`
- `position`

### `answer_choice_table`

- `id`
- `answerChoice`
- `isRightAnswer`
- `questionId`

### `attempt_table`

- `attemptId`
- `testId`
- `questions` — serialized attempt payload

### `errors_table`

- `testId`
- `questionId`

### `favorites_table`

- `testId`
- `questionId`

## Cascade rules

- Deleting a test removes its questions
- Deleting a question removes its answer choices, error records, and favorite records

## Repositories

Main repository interfaces:

- `TestRepository`
- `QuestionRepository`
- `AnswerChoiceRepository`
- `AttemptRepository`
- `ErrorRepository`
- `FavoriteRepository`
- `AppSharedPreferences`

## Shared preferences

Stored values include:

- first launch date
- session counters
- total time
- notification preferences
- one-time UI flags

## Import flow

Incoming JSON is parsed into `TestShareData`, converted to domain models, then inserted through the
repository layer.
