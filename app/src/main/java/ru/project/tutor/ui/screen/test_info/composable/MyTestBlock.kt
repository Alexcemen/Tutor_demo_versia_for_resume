package ru.project.tutor.ui.screen.test_info.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.project.tutor.R
import ru.project.tutor.common_ui.composable.elements.PrimaryButton
import ru.project.tutor.common_ui.composable.elements.SpacerHeight
import ru.project.tutor.common_ui.composable.theme.AppTheme
import ru.project.tutor.ui.models.TestShortUi
import ru.project.tutor.ui.screen.test_info.TestInfoStore

@Composable
@Preview(showBackground = true)
fun MyTestBlockPreview() {
    MyTestBlock(
        listOf(
            TestShortUi(
                id = 1,
                title = "test",
                colorId = 1,
                imageId = 1,
            ),
            TestShortUi(
                id = 1,
                title = "test",
                colorId = 1,
                imageId = 1,
            )
        ),
        1
    ) {

    }
}

@Composable
@Preview(showBackground = true)
fun MyTestEmptyBlockPreview() {
    MyListEmptyBlock() {}
}

@Composable
fun MyTestBlock(
    testList: List<TestShortUi>,
    currentPage: Int,
    onEvent: (TestInfoStore.Event) -> Unit,
) {
    Column {
        val countTestText =
            pluralStringResource(id = R.plurals.count_tests, testList.size, testList.size)
        val description = stringResource(R.string.test_info_test_description_block, countTestText)
        val pagerState = rememberPagerState { testList.size }
        LaunchedEffect(Unit) {
            pagerState.scrollToPage(0)
        }
        LaunchedEffect(pagerState.currentPage) {
            onEvent(TestInfoStore.Event.UpdatePositionState(currentPage))
        }
        Text(
            text = stringResource(R.string.test_info_test_title_block),
            style = AppTheme.textStyle.titleOne,
            color = AppTheme.colors.text.primary,
            modifier = Modifier.padding(start = 16.dp)
        )
        SpacerHeight(8.dp)
        Text(
            text = description,
            style = AppTheme.textStyle.subtitleThree,
            color = AppTheme.colors.text.placeholder,
            modifier = Modifier.padding(start = 16.dp)
        )
        SpacerHeight(16.dp)
        HorizontalPager(state = pagerState, pageSpacing = 8.dp) { page ->
            testList.getOrNull(page)?.let { testItem ->
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    TestCoverCard(
                        title = testItem.title,
                        colorId = testItem.colorId,
                        iconId = testItem.imageId,
                    ) {
                        onEvent(TestInfoStore.Event.ClickTestCard(testItem.id))
                    }
                }
            }
        }
        SpacerHeight(8.dp)
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {
            CircularPagerIndicator(
                currentPage = pagerState.currentPage
            )
        }
    }
}

@Composable
fun MyListEmptyBlock(onClick: () -> Unit) {
    Column(Modifier
        .fillMaxWidth()
        .padding(start = 16.dp, end = 16.dp)) {
        Text(
            text = stringResource(R.string.test_info_test_title_block),
            style = AppTheme.textStyle.titleOne,
            color = AppTheme.colors.text.primary,
        )
        Text(
            text = stringResource(R.string.test_info_empty_description),
            style = AppTheme.textStyle.subheadThree,
            color = AppTheme.colors.text.placeholder,
            modifier = Modifier.padding(top = 8.dp, bottom = 16.dp)
        )
        PrimaryButton(stringResource(R.string.test_info_create_test_title)) { onClick() }
    }
}