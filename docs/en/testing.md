# Testing

## Current automated coverage

The project already includes unit tests for selected utility and notification logic:

- `common_ui/src/test/.../FormatTimeTest`
- `app/src/test/.../NotificationMessagesTest`
- `app/src/test/.../NotificationToggleLogicTest`

There are also Android test stubs for app and database modules.

## Recommended local checks

```bash
./gradlew test
./gradlew :app:test
./gradlew :common_ui:test
./gradlew assembleGoogleDebug
```

## Manual verification checklist

### Test and question management

- Create a test
- Rename a test
- Add questions and answer choices
- Verify validation for minimum and maximum answer count
- Delete a question and confirm related records disappear

### Test session flow

- Run an `Exam` session
- Run a `Workout` session
- Check timer-enabled flow
- Check question shuffling and answer shuffling
- Confirm result summary is accurate

### Error and favorites flow

- Answer incorrectly and verify the item appears in the errors flow
- Add items to favorites and verify the dedicated flow

### Import and persistence

- Import a valid JSON file
- Try an invalid JSON file and confirm safe failure
- Restart the app and confirm stored tests remain available

### UI checks

- Verify both light and dark themes
- Verify smaller screen layouts
- Verify long text values for test titles, questions, and answer choices

### Notifications

- Enable reminders
- Simulate inactivity
- Open the app from a notification destination
