# Functionality

## Core use case

Tutor helps users create their own knowledge checks, practice them repeatedly, and review mistakes
with local progress tracking.

## Main features

### Test creation

- Create a test with a title
- Assign visual presentation automatically
- Edit test title later
- Delete tests with cascade cleanup of related data

### Question management

- Add and edit question text
- Add, edit, and remove answer choices
- Support one or multiple correct answers
- Enforce 2 to 8 answer choices per question
- Preserve question order inside a test

### Test sessions

Supported modes:

- `Exam`
- `Workout`
- `Errors`
- `Favorites`

Available options before start:

- timer
- random question subset
- question shuffle
- answer shuffle
- immediate answer reveal
- alternative handling for multiple-correct questions

### Results and review

- Save each attempt
- Show per-question review after completion
- Mark incorrect questions
- Add questions to favorites

### Statistics

The app stores local aggregate statistics such as:

- total completed tests
- total errors
- total time spent
- first launch date

### JSON import

Tutor can create a test from an incoming JSON file. The import flow parses the file into
`TestShareData`, creates the test, then inserts questions and answer choices.

### Notifications

Local reminder notifications are scheduled with WorkManager for inactive users. They can be enabled
or disabled from the profile screen.

### Store-specific integrations

- Google Play flavor: in-app review flow
- RuStore flavor: in-app review flow and advertising integrations

### UI and localization

- Light and dark themes
- Multiple language resource sets
- Shared design system in `:common_ui`
