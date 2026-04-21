package ru.project.tutor.ui.screen.test_manager.composable

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.project.tutor.R
import ru.project.tutor.common_ui.composable.theme.AppTheme
import ru.project.tutor.common_ui.composable.utils.noRippleClickable
import ru.project.tutor.ui.screen.test_manager.TestManagerStore


@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
private fun SettingBottomSheetPreview() {
    val state = rememberStandardBottomSheetState(SheetValue.Expanded)
    SettingBottomSheet(
        isVisibleBottomSheet = true,
        state = state,
        onEvent = {}
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingBottomSheet(
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
    if (!isVisibleBottomSheet) return
    ModalBottomSheet(
        onDismissRequest = {
            onEvent(
                TestManagerStore.Event.CloseSettingsBottomSheet
            )
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
                text = stringResource(R.string.settings_test),
                color = AppTheme.colors.text.primary,
                style = AppTheme.textStyle.titleOne,
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(
                        color = AppTheme.colors.background.secondaryTwo
                    )
                    .padding(start = 16.dp)
                    .noRippleClickable {
                        onEvent(
                            TestManagerStore.Event.CloseSettingsBottomSheet
                        )
                        onEvent(
                            TestManagerStore.Event.ShareTest
                        )
                    },
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_share),
                    colorFilter = ColorFilter.tint(AppTheme.colors.background.primary),
                    contentDescription = ""
                )
                Text(
                    stringResource(R.string.send_test),
                    color = AppTheme.colors.text.primary,
                    style = AppTheme.textStyle.subheadThree,
                    modifier = Modifier
                        .padding(16.dp)
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(
                        color = AppTheme.colors.background.secondaryTwo
                    )
                    .padding(start = 16.dp)
                    .noRippleClickable {
                        onEvent(
                            TestManagerStore.Event.CloseSettingsBottomSheet
                        )
                        onEvent(
                            TestManagerStore.Event.DeleteTest
                        )
                    },
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_trash),
                    colorFilter = ColorFilter.tint(Color(0xFFE67474)),
                    contentDescription = ""
                )
                Text(
                    stringResource(R.string.delete_test),
                    color = AppTheme.colors.text.red,
                    style = AppTheme.textStyle.subheadThree,
                    modifier = Modifier
                        .padding(16.dp)
                )
            }
        }
    }
}