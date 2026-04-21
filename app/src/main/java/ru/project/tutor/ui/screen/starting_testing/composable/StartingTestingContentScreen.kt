package ru.project.tutor.ui.screen.starting_testing.composable

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
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
import ru.project.tutor.ui.models.options_for_testing.OptionsStartTestingUi
import ru.project.tutor.ui.screen.starting_testing.StartingTestingStore
import ru.project.tutor.ui.screen.test_info.composable.TestCoverCard


@Composable
@Preview(showBackground = false, uiMode = Configuration.UI_MODE_NIGHT_YES)
fun StartingTestingScreenPreview() {
    AppTheme(true) {
        StartingTestingContentScreen(
            state = StartingTestingStore.UiState(
                isExam = false,
                countQuestions = 33,
                testName = "Основные формулы и определения",
                testId = 1,
                imageId = 1,
                colorId = 1,
                optionsStartTestingUi = OptionsStartTestingUi(
                    isTimerEnabled = true,
                    durationTesting = 0,
                    shuffleQuestions = false,
                    showRightAnswer = false,
                    shuffleAnswers = false,
                    doNotMarkMultipleAnswerChoice = false,
                    isRandomQuestionsEnable = false,
                    countRandomQuestions = -1
                ),
                isVisibleBottomSheet = false,
                bottomSheetMessage = ""
            ),
            onEvent = {}
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StartingTestingContentScreen(
    state: StartingTestingStore.UiState,
    onEvent: (StartingTestingStore.Event) -> Unit,
) {
    MinTimerBottomSheet(
        isVisibleBottomSheet = state.isVisibleBottomSheet,
        text = state.bottomSheetMessage,
        onEvent = onEvent
    )
    ContainerContent(topBar = {
        MainToolbar(
            text = stringResource(R.string.start_testing_tittle),
            isVisibleBack = true,
            onClickBack = {
                onEvent(StartingTestingStore.Event.Close)
            }
        )
    }, background = AppTheme.colors.background.basic) {

        SpacerHeight(8.dp)

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {
            TestCoverCard(
                title = state.testName,
                iconId = state.imageId,
                colorId = state.colorId
            ) { }
            SpacerHeight(24.dp)
            TrainingExamSwitcher(
                isExam = state.isExam,
                onEvent = onEvent,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            )
            SpacerHeight(32.dp)
            if (state.isExam) OptionsForExam(
                options = state.optionsStartTestingUi,
                countQuestions = state.countQuestions,
                onEvent = onEvent
            )
            else OptionsForWorkout(
                options = state.optionsStartTestingUi,
                countQuestions = state.countQuestions,
                onEvent = onEvent
            )
        }

        SpacerHeight(16.dp)
        PrimaryButton(
            stringResource(R.string.start_testing_button)
        ) {
            onEvent(
                StartingTestingStore.Event.ActionBottom(
                    isExam = state.isExam
                )
            )
        }
        BottomSpacerSystem()
        SpacerHeight(16.dp)
    }
}





