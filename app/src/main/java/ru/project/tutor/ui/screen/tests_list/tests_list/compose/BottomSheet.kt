package ru.project.tutor.ui.screen.tests_list.tests_list.compose

import androidx.compose.foundation.Image
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.project.tutor.R
import ru.project.tutor.common_ui.composable.theme.AppTheme
import ru.project.tutor.common_ui.composable.utils.noRippleClickable
import ru.project.tutor.ui.screen.test_info.TestsListType
import ru.project.tutor.ui.screen.tests_list.tests_list.TestsListStore


@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun SelectActionForTestBottomSheetPreview() {
    val state = rememberStandardBottomSheetState(initialValue = SheetValue.Expanded)
    SelectActionForTestBottomSheet(
        isVisibleBottomSheet = true,
        testsListType = TestsListType.NORMAL,
        state
    ) {}
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectActionForTestBottomSheet(
    isVisibleBottomSheet: Boolean,
    testsListType: TestsListType,
    state: SheetState = rememberModalBottomSheetState(),
    onEvent: (TestsListStore.Event) -> Unit,
) {

    LaunchedEffect(isVisibleBottomSheet) {
        if (isVisibleBottomSheet) {
            state.show()
        } else {
            state.hide()
        }
    }
    if (!isVisibleBottomSheet) return
    ModalBottomSheet(
        onDismissRequest = {
            onEvent(TestsListStore.Event.CloseBottomSheet)
        },
        sheetState = state,
        contentColor = AppTheme.colors.background.primary,
        containerColor = AppTheme.colors.background.basic
    ) {
        Column {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(color = AppTheme.colors.background.secondaryTwo)
                    .noRippleClickable { onEvent(TestsListStore.Event.ActionBottomSheet(true)) },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    stringResource(R.string.bottom_sheet_start_test_title),
                    color = AppTheme.colors.text.primary,
                    style = AppTheme.textStyle.subheadThree,
                    modifier = Modifier.padding(vertical = 16.dp, horizontal = 16.dp)
                )
                Image(
                    painter = painterResource(R.drawable.ic_play),
                    modifier = Modifier.padding(end = 16.dp),
                    colorFilter = ColorFilter.tint(AppTheme.colors.background.primary),
                    contentDescription = ""
                )
            }
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 32.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(color = AppTheme.colors.background.secondaryTwo)
                    .noRippleClickable { onEvent(TestsListStore.Event.ActionBottomSheet(false)) },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = when (testsListType) {
                        TestsListType.NORMAL -> stringResource(R.string.bottom_sheet_edit_test_title)
                        TestsListType.FAVORITE -> stringResource(R.string.bottom_sheet_open_favorite_questions_list)
                        TestsListType.ERRORS -> stringResource(R.string.bottom_sheet_open_errors_list)
                    },
                    color = AppTheme.colors.text.primary,
                    style = AppTheme.textStyle.subheadThree,
                    modifier = Modifier.padding(vertical = 16.dp, horizontal = 16.dp)
                )
                Image(
                    painter = painterResource(R.drawable.ic_edit_pencel),
                    modifier = Modifier.padding(end = 16.dp),
                    colorFilter = ColorFilter.tint(AppTheme.colors.background.primary),
                    contentDescription = ""
                )
            }
        }
    }
}