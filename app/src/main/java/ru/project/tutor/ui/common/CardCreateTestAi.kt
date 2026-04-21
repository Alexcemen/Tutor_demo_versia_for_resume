package ru.project.tutor.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.project.tutor.R
import ru.project.tutor.common_ui.composable.elements.PrimaryButton
import ru.project.tutor.common_ui.composable.elements.SpacerHeight
import ru.project.tutor.common_ui.composable.theme.AppTheme

@Composable
@Preview
private fun CardCreateTestAiPreview() {
    CardCreateTestAi {}
}

@Composable
fun CardCreateTestAi(goClick: () -> Unit) {
    Column(
        modifier = Modifier
            .height(140.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(color = AppTheme.colors.background.blueLight)
    ) {
        Row(modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp, bottom = 16.dp)
            ) {
                Text(
                    stringResource(R.string.title_test_create_ai),
                    style = AppTheme.textStyle.headline,
                    color = AppTheme.colors.text.whiteUniform,
                    modifier = Modifier.padding(top = 16.dp, end = 24.dp)
                )
                SpacerHeight(8.dp)
                PrimaryButton(
                    stringResource(R.string.next_screen),
                    minHeight = 32.dp,
                    width = 138.dp
                ) { goClick() }
            }
            Image(
                painter = painterResource(R.drawable.ic_ai),
                modifier = Modifier
                    .size(100.dp)
                    .padding(end = 12.dp),
                contentDescription = ""
            )
        }
    }
}