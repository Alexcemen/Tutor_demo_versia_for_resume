package ru.project.tutor.ui.screen.test_manager.composable

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ru.project.tutor.R
import ru.project.tutor.common_ui.composable.theme.AppTheme
import ru.project.tutor.ui.screen.test_manager.TestManagerStore

@Preview
@Composable
private fun BottomToolBarWithoutSearchFieldPreview() {
    BottomToolBarWithoutSearchField(
        modifier = Modifier,
        onEvent = {}
    )
}

@Preview
@Composable
private fun BottomToolBarWithSearchFieldPreview() {
    BottomToolBarWithSearchField(
        modifier = Modifier,
        searchQuery = "",
        onSearchQueryChange = {},
        onEvent = {}
    )
}

@Composable
fun BottomToolBar(
    modifier: Modifier,
    showSearchField: Boolean,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onEvent: (TestManagerStore.Event) -> Unit,
) {
    if (showSearchField) {
        BottomToolBarWithSearchField(
            modifier = modifier,
            searchQuery = searchQuery,
            onSearchQueryChange = onSearchQueryChange,
            onEvent = onEvent
        )
    } else {
        BottomToolBarWithoutSearchField(
            modifier = modifier,
            onEvent = onEvent
        )
    }
}

@Composable
private fun BottomToolBarWithoutSearchField(
    modifier: Modifier,
    onEvent: (TestManagerStore.Event) -> Unit,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        RoundButton(
            imageId = R.drawable.ic_search
        ) {
            onEvent(
                TestManagerStore.Event.OpenSearchField
            )
        }
        ButtonWithText(
            text = stringResource(R.string.start_testing_button_2),
            modifier = Modifier.weight(1f)
        ) {
            onEvent(
                TestManagerStore.Event.StartTesting
            )
        }
        RoundButton(
            imageId = R.drawable.ic_setting
        ) {
            onEvent(
                TestManagerStore.Event.OpenSettingsBottomSheet
            )
        }
    }
}

@Composable
private fun BottomToolBarWithSearchField(
    modifier: Modifier,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onEvent: (TestManagerStore.Event) -> Unit,
) {
    val collapsedOffset = 38.dp + 16.dp
    var expandSearch by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        expandSearch = true
    }

    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        RoundButton(
            imageId = R.drawable.ic_arrow_left
        ) {
            onEvent(
                TestManagerStore.Event.CloseSearchField
            )
        }
        AnimatedSearchField(
            value = searchQuery,
            onValueChange = onSearchQueryChange,
            collapsedOffset = collapsedOffset,
            expand = expandSearch
        )
    }
}

@Composable
private fun SearchField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .height(38.dp)
            .fillMaxWidth()
            .background(
                color = AppTheme.colors.background.primary,
                shape = RoundedCornerShape(50.dp)
            ),
        contentAlignment = Alignment.CenterStart
    ) {
        if (value.isEmpty()) {
            Text(
                text = stringResource(R.string.search),
                color = AppTheme.colors.text.reverse,
                style = AppTheme.textStyle.subheadThree,
                modifier = Modifier.padding(start = 16.dp)
            )
        }

        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            textStyle = AppTheme.textStyle.subheadThree.copy(
                color = AppTheme.colors.text.reverse
            ),
            cursorBrush = SolidColor(AppTheme.colors.text.primary),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp)
        )
    }
}

@Composable
private fun AnimatedSearchField(
    value: String,
    onValueChange: (String) -> Unit,
    collapsedOffset: Dp,
    expand: Boolean,
) {
    BoxWithConstraints(
        modifier = Modifier.fillMaxWidth()
    ) {
        val maxWidthAnimated by animateDpAsState(
            targetValue = if (expand) {
                maxWidth
            } else {
                maxWidth - collapsedOffset
            },
            animationSpec = tween(
                durationMillis = 300,
                easing = FastOutSlowInEasing
            ),
            label = "SearchWidthAnim"
        )

        SearchField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .widthIn(max = maxWidthAnimated)
                .height(38.dp)
        )
    }
}
