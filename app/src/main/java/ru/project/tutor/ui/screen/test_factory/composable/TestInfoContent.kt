package ru.project.tutor.ui.screen.test_factory.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.project.tutor.R
import ru.project.tutor.common_ui.composable.elements.BottomSpacerSystem
import ru.project.tutor.common_ui.composable.elements.ContainerContent
import ru.project.tutor.common_ui.composable.elements.MainToolbar
import ru.project.tutor.common_ui.composable.elements.PrimaryButton
import ru.project.tutor.common_ui.composable.elements.SpacerHeight
import ru.project.tutor.common_ui.composable.elements.TextField
import ru.project.tutor.common_ui.composable.theme.AppTheme
import ru.project.tutor.ui.common.CardCreateTestAi
import ru.project.tutor.ui.screen.test_factory.TestFactoryStore

@Preview
@Composable
fun TestFactoryContentPreview() {
    TestFactoryContent(
        TestFactoryStore.UiState(
            "qwerty"
        ), {})
}

@Composable
fun TestFactoryContent(
    state: TestFactoryStore.UiState,
    onEvent: (TestFactoryStore.Event) -> Unit,
) {
    ContainerContent(topBar = {
        MainToolbar(stringResource(R.string.test_factory_title)) { onEvent(TestFactoryStore.Event.Close) }
    }, background = AppTheme.colors.background.basic) {
        Column(Modifier
            .weight(1f)
            .verticalScroll(rememberScrollState())) {
            SpacerHeight(16.dp)
            CardCreateTestAi {
                onEvent(TestFactoryStore.Event.ClickManualAi)
            }
            SpacerHeight(24.dp)
            Text(
                stringResource(R.string.test_create_new),
                style = AppTheme.textStyle.titleOne,
                color = AppTheme.colors.text.primary
            )
            SpacerHeight(8.dp)
            Text(
                stringResource(R.string.hint_for_create_test_name),
                style = AppTheme.textStyle.subheadThree,
                color = AppTheme.colors.text.placeholder
            )
            SpacerHeight(8.dp)
            TextField(state.nameTest, stringResource(R.string.text_field_for_test_name)) {
                onEvent(
                    TestFactoryStore.Event.UpdateText(
                        it
                    )
                )
            }
            SpacerHeight(16.dp)
            PrimaryButton(stringResource(R.string.test_factory_button)) { onEvent(TestFactoryStore.Event.Save) }
            SpacerHeight(24.dp)
            Text(
                stringResource(R.string.test_load_new),
                style = AppTheme.textStyle.titleOne,
                color = AppTheme.colors.text.primary
            )
            SpacerHeight(8.dp)
            Text(
                stringResource(R.string.hint_for_load_test),
                style = AppTheme.textStyle.subheadThree,
                color = AppTheme.colors.text.placeholder
            )
            SpacerHeight(16.dp)
            PrimaryButton(stringResource(R.string.test_load_button)) { onEvent(TestFactoryStore.Event.OpenFileManager) }
        }
        if (WindowInsets.ime.asPaddingValues().calculateBottomPadding().value == 0F) {
            BottomSpacerSystem()
            SpacerHeight(24.dp)
        } else {
            SpacerHeight(12.dp)
            Spacer(
                Modifier.height(
                    WindowInsets.ime.asPaddingValues().calculateBottomPadding()
                )
            )
        }
    }
}