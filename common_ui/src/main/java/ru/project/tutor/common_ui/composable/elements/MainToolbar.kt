package ru.project.tutor.common_ui.composable.elements

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ru.project.tutor.common_ui.R
import ru.project.tutor.common_ui.composable.theme.AppTheme
import ru.project.tutor.common_ui.composable.utils.noDoubleClick

@Composable
@Preview
internal fun MainToolbarPreview() {
    MainToolbar(text = "Title")
}

@Composable
@Preview
internal fun MainToolbarCardPreview() {
    MainToolbarCard(text = "Title", applyStatusBarInsets = false) {}
}

@Composable
fun MainToolbar(
    text: String,
    applyStatusBarInsets: Boolean = true,
    isVisibleBack: Boolean = true,
    backgroundColor: Color = AppTheme.colors.background.basic,
    onClickBack: () -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor)
    ) {
        if (applyStatusBarInsets) {
            Spacer(
                modifier = Modifier.height(
                    WindowInsets.statusBars.asPaddingValues().calculateTopPadding()
                )
            )
        }
        ContentToolBar(text = text, isVisibleBack = isVisibleBack, onClickBack = onClickBack)
    }
}

@Composable
fun MainToolbarCard(
    text: String,
    applyStatusBarInsets: Boolean = true,
    isVisibleBack: Boolean = true,
    backgroundColor: Color = AppTheme.colors.background.basic,
    onClickBack: () -> Unit,
) {
    AppCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(
            topStart = 0.dp,
            topEnd = 0.dp,
            bottomEnd = 0.dp,
            bottomStart = 0.dp,
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(backgroundColor)
        ) {
            if (applyStatusBarInsets) {
                Spacer(
                    modifier = Modifier.height(
                        WindowInsets.statusBars.asPaddingValues().calculateTopPadding()
                    )
                )
            }
            ContentToolBar(
                text = text,
                isVisibleBack = isVisibleBack,
                paddingEnd = 0.dp,
                onClickBack = onClickBack
            )
        }
    }
}

@Composable
private fun ContentToolBar(
    text: String,
    isVisibleBack: Boolean,
    paddingEnd: Dp = 16.dp,
    onClickBack: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (isVisibleBack) {
            Image(
                painter = painterResource(
                    id = R.drawable.ic_arrow_right,
                ),
                contentDescription = "back",
                colorFilter = ColorFilter.tint(color = AppTheme.colors.background.primary),
                modifier = Modifier
                    .rotate(180F)
                    .padding(vertical = 18.dp)
                    .padding(end = paddingEnd)
                    .noDoubleClick { onClickBack() },
            )
        }
        Text(
            text = text,
            modifier =
                Modifier
                    .padding(vertical = 18.dp)
                    .padding(start = 16.dp),
            style = AppTheme.textStyle.subtitleTwo,
            color = AppTheme.colors.text.primary
        )
    }
}