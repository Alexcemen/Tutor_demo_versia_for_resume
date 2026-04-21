package ru.project.tutor.ui.screen.tests_list.tests_list.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.project.tutor.R
import ru.project.tutor.common_ui.composable.elements.BottomSpacerSystem
import ru.project.tutor.common_ui.composable.elements.ContainerContent
import ru.project.tutor.common_ui.composable.elements.PrimaryButton
import ru.project.tutor.common_ui.composable.elements.SpacerHeight
import ru.project.tutor.common_ui.composable.theme.AppTheme
import ru.project.tutor.ui.screen.test_info.TestsListType
import ru.project.tutor.ui.screen.tests_list.tests_list.TestsListStore

@Preview
@Composable
private fun EmptyTestsScreenPreview() {
    EmptyTestsScreen(
        type = TestsListType.ERRORS,
        onEvent = {})
}

@Composable
fun EmptyTestsScreen(
    type: TestsListType,
    onEvent: (TestsListStore.Event) -> Unit,
) {
    ContainerContent {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SpacerHeight(40.dp)
            Image(
                painterResource(
                    when (type) {
                        TestsListType.NORMAL -> R.drawable.ic_emty_normal_test_list
                        TestsListType.ERRORS -> R.drawable.ic_empty_error_test_list
                        TestsListType.FAVORITE -> R.drawable.ic_empty_favorite_test_list
                    }
                ),
                "",
                modifier = Modifier.size(94.dp)
            )
            SpacerHeight(16.dp)
            Text(
                text = stringResource(
                    when (type) {
                        TestsListType.NORMAL -> R.string.text_empty_normal_test_list
                        TestsListType.FAVORITE -> R.string.text_empty_favorite_test_list
                        TestsListType.ERRORS -> R.string.text_empty_error_test_list
                    }
                ),
                style = AppTheme.textStyle.titleOne,
                color = AppTheme.colors.text.primary,
                textAlign = TextAlign.Center,
            )
            SpacerHeight(16.dp)
            Text(
                text = stringResource(
                    when (type) {
                        TestsListType.NORMAL -> R.string.text_empty_normal_test_list_description
                        TestsListType.FAVORITE -> R.string.text_empty_favorite_test_list_description
                        TestsListType.ERRORS -> R.string.text_empty_error_test_list_description
                    }
                ),
                style = AppTheme.textStyle.bodyTwo,
                color = AppTheme.colors.text.placeholder,
                textAlign = TextAlign.Center,
            )
            SpacerHeight(16.dp)
            PrimaryButton(
                text = stringResource(
                    when (type) {
                        TestsListType.NORMAL -> R.string.test_factory_button
                        TestsListType.FAVORITE -> R.string.open_test_list_button
                        TestsListType.ERRORS -> R.string.open_test_list_button
                    }
                ),
                onClick = {
                    onEvent(
                        if (type == TestsListType.NORMAL) {
                            TestsListStore.Event.CreateNewTest
                        } else {
                            TestsListStore.Event.OpenTestTestList
                        }
                    )
                }
            )
            BottomSpacerSystem()
            SpacerHeight(16.dp)
        }
    }
}