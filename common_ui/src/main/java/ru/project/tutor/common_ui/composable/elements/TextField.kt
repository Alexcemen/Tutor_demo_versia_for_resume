package ru.project.tutor.common_ui.composable.elements

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.project.tutor.common_ui.composable.theme.AppTheme

@Composable
@Preview(showBackground = true)
private fun TextFieldPreview() {
    TextField("qwe", "Example") {}
}

@Composable
@Preview(showBackground = false, uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun TextFieldPreviewDark() {
    AppTheme(true) {
        TextField("qwe", "Example") {}
    }
}

@Composable
fun TextField(
    text: String,
    label: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    onValueChange: (String) -> Unit,
) {
    OutlinedTextField(
        value = text,
        modifier = Modifier.fillMaxWidth(),
        onValueChange = onValueChange,
        label = {
            Text(
                text = label,
                color = AppTheme.colors.text.placeholder,
                style = AppTheme.textStyle.subheadThree
            )
        },
        keyboardOptions = keyboardOptions,
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = AppTheme.colors.background.primary,
            unfocusedBorderColor = AppTheme.colors.text.placeholder,
            unfocusedTextColor = AppTheme.colors.text.placeholder,
            focusedTextColor = AppTheme.colors.text.primary,
            cursorColor = AppTheme.colors.text.primary,
            focusedLabelColor = AppTheme.colors.background.primary
        )
    )
}