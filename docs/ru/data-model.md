# Модель данных

## Обзор

Persistence-слой построен на Room и не протекает напрямую в доменный слой.

```text
Доменные модели <-> маппинг в репозиториях
Room entities и DAO
```

## База данных

- класс базы: `AppDatabase`
- модуль: `:database`
- текущая версия схемы: `4`

## Таблицы

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
- `questions` — сериализованная попытка

### `errors_table`

- `testId`
- `questionId`

### `favorites_table`

- `testId`
- `questionId`

## Каскадные правила

- удаление теста удаляет его вопросы
- удаление вопроса удаляет варианты ответа, записи об ошибках и записи избранного

## Репозитории

Основные интерфейсы репозиториев:

- `TestRepository`
- `QuestionRepository`
- `AnswerChoiceRepository`
- `AttemptRepository`
- `ErrorRepository`
- `FavoriteRepository`
- `AppSharedPreferences`

## Shared preferences

Сохраняются:

- дата первого запуска
- счетчики сессий
- суммарное время
- настройки уведомлений
- одноразовые UI-флаги

## Flow импорта

Входящий JSON парсится в `TestShareData`, преобразуется в доменные модели и вставляется через
репозиторный слой.
