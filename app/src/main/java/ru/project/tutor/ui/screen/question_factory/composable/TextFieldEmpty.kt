package ru.project.tutor.ui.screen.question_factory.composable

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.project.tutor.common_ui.composable.theme.AppTheme

@Composable
@Preview
private fun TextFieldEmptyPreview() {
    TextFieldEmpty(
        value = "Test",
        onValueChange = {},
        placeHolder = "Напишите ваш вопрос...",
        placeholderStyleFont = AppTheme.textStyle.subtitleTwo
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldEmpty(
    value: String,
    onValueChange: (String) -> Unit,
    placeHolder: String,
    placeholderStyleFont: TextStyle,
    focusRequester: FocusRequester? = null,
) {
    val interactionSource = remember { MutableInteractionSource() }
    var textFieldValue by remember {
        mutableStateOf(
            TextFieldValue(
                value,
                TextRange(value.length)
            )
        )
    }

    LaunchedEffect(value) {
        if (textFieldValue.text != value) {
            textFieldValue = TextFieldValue(value, TextRange(value.length))
        }
    }

    BasicTextField(
        value = textFieldValue,
        onValueChange = { newValue ->
            textFieldValue = newValue
            onValueChange(newValue.text)
        },
        modifier = Modifier
            .fillMaxWidth()
            .then(if (focusRequester != null) Modifier.focusRequester(focusRequester) else Modifier),
        textStyle = placeholderStyleFont.copy(
            color = AppTheme.colors.text.primary,
            textAlign = TextAlign.Center
        ),
        interactionSource = interactionSource,
        decorationBox =
            @Composable { innerTextField ->
                // places leading icon, text field with label and placeholder, trailing icon
                TextFieldDefaults.DecorationBox(
                    value = value,
                    visualTransformation = VisualTransformation.None,
                    innerTextField = innerTextField,
                    contentPadding = PaddingValues(0.dp),
                    placeholder = {
                        Text(
                            placeHolder,
                            style = placeholderStyleFont,
                            color = AppTheme.colors.text.placeholder,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    },
                    enabled = true,
                    singleLine = false,
                    interactionSource = interactionSource,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        cursorColor = AppTheme.colors.text.reversed
                    ),
                )
            }
    )
}