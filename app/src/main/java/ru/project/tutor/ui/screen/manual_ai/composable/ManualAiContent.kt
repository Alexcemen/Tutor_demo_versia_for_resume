package ru.project.tutor.ui.screen.manual_ai.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
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
import ru.project.tutor.common_ui.composable.theme.AppTheme
import ru.project.tutor.ui.screen.manual_ai.ManualAiStore

@Composable
@Preview
fun ManualAiContentPreview() {
    ManualAiContent() {}
}

@Composable
fun ManualAiContent(onEvent: (ManualAiStore.Event) -> Unit) {
    ContainerContent(topBar = {
        MainToolbar(
            text = "",
            isVisibleBack = true,
            onClickBack = {
                onEvent(ManualAiStore.Event.ClickClose)
            }
        )
    }, background = AppTheme.colors.background.basic) {
        Column(Modifier.verticalScroll(rememberScrollState())) {
            Text(
                stringResource(R.string.manual_ai_title),
                style = AppTheme.textStyle.titleOne,
                color = AppTheme.colors.text.primary,
                modifier = Modifier.padding(top = 8.dp)
            )
            Text(
                stringResource(R.string.manual_ai_info_one),
                style = AppTheme.textStyle.subheadThree,
                color = AppTheme.colors.text.primary,
                modifier = Modifier.padding(top = 16.dp)
            )
            Text(
                stringResource(R.string.manual_ai_info_two),
                style = AppTheme.textStyle.subheadThree,
                color = AppTheme.colors.text.primary,
                modifier = Modifier.padding(top = 8.dp)
            )
            Text(
                stringResource(R.string.manual_ai_move),
                style = AppTheme.textStyle.subheadOne,
                color = AppTheme.colors.text.primary,
                modifier = Modifier.padding(top = 16.dp)
            )
            PrimaryButton(
                stringResource(R.string.manual_ai_move_btn),
                modifier = Modifier.padding(top = 16.dp)
            ) {
                onEvent(ManualAiStore.Event.ClickOpenMovie)
            }
            Text(
                stringResource(R.string.manual_ai_step_title),
                style = AppTheme.textStyle.subtitleOne,
                color = AppTheme.colors.text.primary,
                modifier = Modifier.padding(top = 24.dp)
            )
            Text(
                stringResource(R.string.manual_ai_step_first),
                style = AppTheme.textStyle.bodyOne,
                color = AppTheme.colors.text.primary,
                modifier = Modifier.padding(top = 12.dp)
            )
            Text(
                stringResource(R.string.manual_ai_step_first_description),
                style = AppTheme.textStyle.subheadThree,
                color = AppTheme.colors.text.primary,
                modifier = Modifier.padding(top = 8.dp)
            )
            PrimaryButton(
                stringResource(R.string.manual_ai_step_first_btn_first),
                modifier = Modifier.padding(top = 16.dp),
                contentColor = AppTheme.colors.background.red,
                textColor = AppTheme.colors.text.whiteUniform
            ) {
                onEvent(ManualAiStore.Event.ClickCopyPromtFile)
            }
            PrimaryButton(
                stringResource(R.string.manual_ai_step_first_btn_second),
                modifier = Modifier.padding(top = 16.dp),
                contentColor = AppTheme.colors.background.yellow,
                textColor = AppTheme.colors.text.whiteUniform
            ) {
                onEvent(ManualAiStore.Event.ClickCopyPromtUrl)
            }
            Text(
                stringResource(R.string.manual_ai_step_second),
                style = AppTheme.textStyle.bodyOne,
                color = AppTheme.colors.text.primary,
                modifier = Modifier.padding(top = 24.dp)
            )
            Text(
                stringResource(R.string.manual_ai_step_second_description),
                style = AppTheme.textStyle.subheadThree,
                color = AppTheme.colors.text.primary,
                modifier = Modifier.padding(top = 8.dp)
            )
            PrimaryButton(
                stringResource(R.string.manual_ai_step_second_btn),
                modifier = Modifier.padding(top = 16.dp),
                contentColor = AppTheme.colors.background.blue,
                textColor = AppTheme.colors.text.whiteUniform
            ) {
                onEvent(ManualAiStore.Event.ClickDeepSeek)
            }
            Text(
                stringResource(R.string.manual_ai_step_second_description_two),
                style = AppTheme.textStyle.subheadThree,
                color = AppTheme.colors.text.primary,
                modifier = Modifier.padding(top = 16.dp)
            )
            Text(
                stringResource(R.string.manual_ai_step_three),
                style = AppTheme.textStyle.bodyOne,
                color = AppTheme.colors.text.primary,
                modifier = Modifier.padding(top = 24.dp)
            )
            Text(
                stringResource(R.string.manual_ai_step_three_description_first),
                style = AppTheme.textStyle.subheadThree,
                color = AppTheme.colors.text.primary,
                modifier = Modifier.padding(top = 16.dp)
            )
            PrimaryButton(
                stringResource(R.string.manual_ai_step_three_btn_first),
                modifier = Modifier.padding(top = 16.dp),
            ) {
                onEvent(ManualAiStore.Event.ClickInsertBuffer)
            }
            Text(
                stringResource(R.string.manual_ai_step_three_description_second),
                style = AppTheme.textStyle.subheadThree,
                color = AppTheme.colors.text.primary,
                modifier = Modifier.padding(top = 16.dp)
            )
            PrimaryButton(
                stringResource(R.string.manual_ai_step_three_btn_second),
                modifier = Modifier.padding(top = 16.dp),
            ) {
                onEvent(ManualAiStore.Event.ClickChooseFile)
            }
            Text(
                stringResource(R.string.manual_ai_finish),
                style = AppTheme.textStyle.subtitleOne,
                color = AppTheme.colors.background.green,
                modifier = Modifier.padding(top = 16.dp),
            )
            SpacerHeight(24.dp)
            BottomSpacerSystem()
        }
    }
}