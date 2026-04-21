package ru.project.tutor.ui.screen.test_manager.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.project.tutor.R
import ru.project.tutor.common_ui.composable.elements.PrimaryButton
import ru.project.tutor.common_ui.composable.theme.AppTheme
import ru.project.tutor.ui.screen.test_manager.TestManagerStore


@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun EmptyListQuestionsNotificationBottomSheetPreview() {
    val state = rememberStandardBottomSheetState(SheetValue.Expanded)
    EmptyListQuestionsNotificationBottomSheet(
        isVisibleBottomSheet = true,
        state = state,
        onEvent = {}
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmptyListQuestionsNotificationBottomSheet(
    isVisibleBottomSheet: Boolean,
    state: SheetState = rememberModalBottomSheetState(),
    onEvent: (TestManagerStore.Event) -> Unit,
) {
    LaunchedEffect(isVisibleBottomSheet) {
        if (isVisibleBottomSheet) {
            state.show()
        } else {
            state.hide()
        }
    }
    if (!isVisibleBottomSheet && !state.isVisible) return
    ModalBottomSheet(
        onDismissRequest = {
            onEvent(TestManagerStore.Event.CloseNoQuestionsNotificationBottomSheet)
        },
        sheetState = state,
        contentColor = AppTheme.colors.background.primary,
        containerColor = AppTheme.colors.background.basic
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = stringResource(R.string.attention),
                color = AppTheme.colors.text.primary,
                style = AppTheme.textStyle.subtitleOne
            )
            Text(
                text = stringResource(
                    R.string.no_questions_notification_bottom_sheet,
                    stringResource(R.string.no_questions)
                ),
                color = AppTheme.colors.text.primary,
                style = AppTheme.textStyle.subheadThree
            )
            PrimaryButton(
                stringResource(R.string.close),
                modifier = Modifier
                    .padding(top = 16.dp, bottom = 16.dp)
            ) {
                onEvent(TestManagerStore.Event.CloseNoQuestionsNotificationBottomSheet)
            }
        }
    }
}