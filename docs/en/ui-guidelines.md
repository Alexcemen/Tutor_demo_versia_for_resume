# UI Guidelines

The codebase uses a shared design system from `:common_ui`. Theme tokens are exposed through
`AppTheme`.

## Theme access

```kotlin
AppTheme.colors
AppTheme.textStyle
```

## Core rules

- Use theme colors instead of hardcoded values
- Use typography tokens instead of ad hoc text styles
- Check both light and dark themes
- Prefer shared components before creating new ones

## Color system

Important color groups:

- text colors
- background colors
- semantic colors for success, error, warning
- overlay and mask colors

The full palette lives in:

- `common_ui/.../theme/LightColor.kt`
- `common_ui/.../theme/DarkColor.kt`
- `common_ui/.../theme/AppThemeColors.kt`

## Typography

Typography is centralized in `AppThemeTypography` and includes:

- large titles
- titles
- subtitles
- body styles
- captions
- overline

## Shared components

Common reusable components include:

- `PrimaryButton`
- `SecondaryButton`
- `AppCard`
- `ContainerContent`
- `MainToolbar`
- `TextField`
- bottom navigation components

## Interaction patterns

- Buttons use consistent rounded shapes
- Shared controls protect against accidental double taps where needed
- Screen containers are wrapped with reusable layout primitives from `:common_ui`
