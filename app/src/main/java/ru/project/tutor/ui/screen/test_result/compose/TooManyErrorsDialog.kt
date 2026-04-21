package ru.project.tutor.ui.screen.test_result.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import ru.project.tutor.R
import ru.project.tutor.common_ui.composable.elements.PrimaryButton
import ru.project.tutor.common_ui.composable.elements.SpacerHeight
import ru.project.tutor.common_ui.composable.elements.SpacerWidth
import ru.project.tutor.common_ui.composable.elements.model.ButtonWithBorder
import ru.project.tutor.common_ui.composable.theme.AppTheme
import ru.project.tutor.common_ui.composable.utils.noRippleClickable
import ru.project.tutor.ui.screen.test_result.TestResultStore

@Preview
@Composable
private fun TooManyErrorsDialogPreview() {
    TooManyErrorsDialog(
        false,
        {}
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TooManyErrorsDialog(
    isDontShowAgainCheckbox: Boolean,
    onEvent: (TestResultStore.Event) -> Unit,
) {
    Dialog(
        onDismissRequest = {
            onEvent(
                TestResultStore.Event.CloseTooManyErrorsDialog
            )
        },
        DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
            propagateMinConstraints = true
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(color = AppTheme.colors.background.basic)
                    .padding(16.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(AppTheme.colors.background.secondaryTwo)
                                .size(32.dp)
                                .padding(4.dp)
                                .noRippleClickable(
                                    onClick = {
                                        onEvent(
                                            TestResultStore.Event.CloseTooManyErrorsDialog
                                        )
                                    }
                                )
                        ) {
                            Image(
                                painter = painterResource(R.drawable.ic_close_dialog),
                                modifier = Modifier.fillMaxSize(),
                                contentDescription = "",
                                colorFilter = ColorFilter.tint(color = AppTheme.colors.background.primary)
                            )
                        }
                    }

                    Image(
                        painter = painterResource(R.drawable.ic_smile),
                        modifier = Modifier.size(112.dp),
                        contentDescription = ""
                    )

                    SpacerHeight(24.dp)

                    Text(
                        text = stringResource(R.string.too_errors_dialog_title),
                        style = AppTheme.textStyle.subtitleOne,
                        color = AppTheme.colors.text.red
                    )

                    SpacerHeight(16.dp)

                    Text(
                        text = stringResource(R.string.too_errors_dialog_text),
                        style = AppTheme.textStyle.subheadThree,
                        color = AppTheme.colors.text.primary,
                        textAlign = TextAlign.Center
                    )

                    SpacerHeight(28.dp)

                    DontShowAgainCheckbox(
                        isChecked = isDontShowAgainCheckbox,
                        onClick = {
                            onEvent(
                                TestResultStore.Event.ToggleDontShowAgainCheckbox
                            )
                        }
                    )

                    SpacerHeight(18.dp)

                    PrimaryButton(
                        text = stringResource(R.string.too_errors_dialog_button_show_errors_list),
                        onClick = {
                            onEvent(
                                TestResultStore.Event.ShowErrorsList
                            )
                        }
                    )

                    SpacerHeight(8.dp)

                    ButtonWithBorder(
                        text = stringResource(R.string.too_errors_dialog_button_start_testing),
                        onClick = {
                            onEvent(
                                TestResultStore.Event.StartTesting
                            )
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun DontShowAgainCheckbox(
    isChecked: Boolean,
    onClick: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Image(
            painterResource(
                if (isChecked) R.drawable.ic_square_checkmark_correct
                else R.drawable.is_empty_sqdr
            ),
            "",
            colorFilter = ColorFilter.tint(AppTheme.colors.background.primary),
            modifier = Modifier
                .size(16.dp)
                .noRippleClickable {
                    onClick()
                }
        )
        SpacerWidth(8.dp)
        Text(
            text = stringResource(R.string.dont_show_again_checkbox_text),
            style = AppTheme.textStyle.subheadThree,
            color = AppTheme.colors.text.primary
        )
    }
}