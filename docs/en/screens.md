# Screens

## Primary screens

### Splash

- Initializes launch flow
- Loads local app state
- Decides whether to continue to onboarding or the main container

### Home / test overview

- Displays user summary and entry points into test lists
- Opens test creation and review flows

### Test editor

- Creates new tests
- Renames or deletes existing tests

### Question editor

- Creates and updates questions
- Manages answer choices and correct-answer flags

### Test list

- Shows tests for the selected category
- Supports normal, favorite, and error-focused collections

### Test setup

- Selects session mode
- Configures timer, shuffling, and question count options

### Test session

- Renders active question flow
- Tracks progress
- Applies answer validation logic
- Supports interruption handling before exit

### Result review

- Shows score summary
- Displays each answered question with the correct result
- Allows follow-up actions such as favorites and mistake review

### Profile and statistics

- Shows aggregate local statistics
- Displays app version information
- Exposes notification settings and review entry points

### Import guide

- Explains the JSON import flow
- Serves as an entry point for file-based test creation

## Navigation model

The app uses a typed back stack with serializable navigation keys. Navigation is performed through a
shared `RootNavigation` composition local and imperative stack operations such as `add()` and
`removeLastOrNull()`.
