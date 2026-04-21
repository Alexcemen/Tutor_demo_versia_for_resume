package ru.project.tutor.ui.screen.question_factory.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import ru.project.tutor.R
import ru.project.tutor.common_ui.composable.elements.PrimaryButton
import ru.project.tutor.common_ui.composable.theme.AppTheme
import ru.project.tutor.common_ui.composable.utils.noRippleClickable
import ru.project.tutor.ui.models.AnswerChoiceUi
import ru.project.tutor.ui.screen.question_factory.QuestionFactoryStore

@Composable
@Preview
private fun AnswerItemPreview() {
    AnswerItem(
        answer = AnswerChoiceUi(
            id = 1,
            text = """
                Супер вопрос
            """.trimIndent(),
            isRightAnswer = true
        ),
        onValueChange = {},
        onRemove = {},
        onEvent = {},
    )
}


@Composable
fun AnswerItem(
    answer: AnswerChoiceUi,
    onValueChange: (String) -> Unit,
    onRemove: () -> Unit,
    onEvent: (QuestionFactoryStore.Event) -> Unit,
) {
    var isEditing by remember { mutableStateOf(false) }
    var editingText by remember { mutableStateOf(answer.text) }
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    LaunchedEffect(isEditing) {
        if (isEditing) {
            focusManager.clearFocus()
        }
    }

    if (isEditing) {
        Dialog(
            onDismissRequest = { isEditing = false },
            properties = DialogProperties(
                usePlatformDefaultWidth = false,
                dismissOnBackPress = false,
                dismissOnClickOutside = false
            ),
        ) {
            LaunchedEffect(Unit) {
                focusRequester.requestFocus()
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .imePadding()
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) { isEditing = false }
            ) {
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .navigationBarsPadding()
                        .padding(16.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(AppTheme.colors.background.basic)
                        .padding(16.dp)
                ) {
                    Column {
                        TextFieldEmpty(
                            value = editingText,
                            onValueChange = {
                                editingText = it
                                onValueChange(it)
                            },
                            placeHolder = stringResource(R.string.enter_answer),
                            AppTheme.textStyle.subheadThree,
                            focusRequester = focusRequester,
                        )

                        PrimaryButton(
                            text = stringResource(R.string.apply),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 24.dp)
                        ) {
                            onValueChange(editingText)
                            isEditing = false
                        }
                    }
                }
            }
        }
    }

    Box(
        Modifier
            .clip(shape = RoundedCornerShape(16.dp))
            .border(
                width = 2.dp, AppTheme.colors.background.mask,
                shape = RoundedCornerShape(16.dp)
            )
            .fillMaxWidth()
            .wrapContentHeight()
            .background(AppTheme.colors.background.basic)
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painterResource(R.drawable.ic_correct_choice),
                "",
                colorFilter = ColorFilter.tint(
                    if (answer.isRightAnswer) AppTheme.colors.background.green
                    else AppTheme.colors.background.primary
                ),
                modifier = Modifier
                    .size(24.dp)
                    .noRippleClickable {
                        onEvent(
                            QuestionFactoryStore.Event.SelectRightAnswer(answer.id)
                        )
                    }
            )

            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        editingText = answer.text
                        isEditing = true
                    }
            ) {
                Text(
                    text = answer.text.ifEmpty { stringResource(R.string.enter_answer) },
                    style = AppTheme.textStyle.subheadThree,
                    color = if (answer.text.isEmpty()) AppTheme.colors.text.placeholder else AppTheme.colors.text.primary
                )
            }

            Image(
                painterResource(R.drawable.ic_trash),
                "",
                colorFilter = ColorFilter.tint(AppTheme.colors.background.red),
                modifier = Modifier
                    .size(24.dp)
                    .noRippleClickable { onRemove() }
            )
        }
    }
}