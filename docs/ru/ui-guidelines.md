# UI-гайд

Проект использует общую дизайн-систему из `:common_ui`. Theme tokens доступны через `AppTheme`.

## Доступ к теме

```kotlin
AppTheme.colors
AppTheme.textStyle
```

## Базовые правила

- использовать theme colors вместо hardcoded values
- использовать typography tokens вместо локальных текстовых стилей
- проверять и светлую, и темную тему
- сначала использовать shared components, потом создавать новые

## Цветовая система

Основные группы цветов:

- цвета текста
- фоновые цвета
- семантические цвета для success, error, warning
- overlay и mask colors

Основная палитра находится в:

- `common_ui/.../theme/LightColor.kt`
- `common_ui/.../theme/DarkColor.kt`
- `common_ui/.../theme/AppThemeColors.kt`

## Типографика

Типографика централизована в `AppThemeTypography` и включает:

- крупные заголовки
- заголовки
- подзаголовки
- body-стили
- captions
- overline

## Общие компоненты

Основные переиспользуемые компоненты:

- `PrimaryButton`
- `SecondaryButton`
- `AppCard`
- `ContainerContent`
- `MainToolbar`
- `TextField`
- компоненты нижней навигации

## Паттерны взаимодействия

- кнопки используют единые скругления
- shared controls при необходимости защищают от двойных нажатий
- экранные контейнеры строятся на переиспользуемых layout-примитивах из `:common_ui`
