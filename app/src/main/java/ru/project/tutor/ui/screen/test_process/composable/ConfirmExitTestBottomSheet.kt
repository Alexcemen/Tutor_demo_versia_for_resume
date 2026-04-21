package ru.project.tutor.ui.screen.test_process.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.project.tutor.R
import ru.project.tutor.common_ui.composable.elements.SpacerHeight
import ru.project.tutor.common_ui.composable.theme.AppTheme
import ru.project.tutor.common_ui.composable.utils.noRippleClickable
import ru.project.tutor.ui.common.CustomDragHandle
import ru.project.tutor.ui.screen.test_process.TestProcessStore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
private fun ConfirmExitTestBottomSheetPreview() {
    val state = rememberStandardBottomSheetState(SheetValue.Expanded)
    ConfirmExitTestBottomSheet(
        isVisibleBottomSheet = true,
        state = state,
        onEvent = {}
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfirmExitTestBottomSheet(
    isVisibleBottomSheet: Boolean,
    state: SheetState = rememberModalBottomSheetState(),
    onDismiss: () -> Unit = {},
    onEvent: (TestProcessStore.Event) -> Unit,
) {
    LaunchedEffect(isVisibleBottomSheet) {
        if (isVisibleBottomSheet) {
            state.show()
        } else {
            state.hide()
        }
    }

    LaunchedEffect(state.currentValue) {
        if (state.currentValue == SheetValue.Hidden) {
            onDismiss()
        }
    }

    if (!isVisibleBottomSheet && !state.isVisible) return
    ModalBottomSheet(
        onDismissRequest = {
            onEvent(TestProcessStore.Event.CloseConfirmExitBottomSheet)
        },
        sheetState = state,
        contentColor = AppTheme.colors.background.primary,
        containerColor = AppTheme.colors.background.basic,
        dragHandle = { CustomDragHandle() }
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            SpacerHeight(2.dp)
            Text(
                text = stringResource(R.string.confirm_exit_test),
                color = AppTheme.colors.text.primary,
                style = AppTheme.textStyle.subtitleOne,
                modifier = Modifier
                    .padding(end = 16.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(
                        color = AppTheme.colors.background.secondaryTwo
                    )
                    .noRippleClickable {
                        onEvent(TestProcessStore.Event.CloseScreen)
                    },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    stringResource(R.string.exit),
                    color = AppTheme.colors.text.primary,
                    style = AppTheme.textStyle.subheadThree,
                    modifier = Modifier
                        .padding(vertical = 16.dp, horizontal = 16.dp)
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(
                        color = AppTheme.colors.background.secondaryTwo
                    )
                    .noRippleClickable {
                        onEvent(TestProcessStore.Event.CloseConfirmExitBottomSheet)
                    },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    stringResource(R.string.cancel),
                    color = AppTheme.colors.text.primary,
                    style = AppTheme.textStyle.subheadThree,
                    modifier = Modifier
                        .padding(vertical = 16.dp, horizontal = 16.dp)
                )
            }
            SpacerHeight(16.dp)
        }
    }
}
