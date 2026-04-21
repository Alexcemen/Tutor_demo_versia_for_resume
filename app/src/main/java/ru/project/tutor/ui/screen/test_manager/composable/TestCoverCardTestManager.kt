package ru.project.tutor.ui.screen.test_manager.composable

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.project.tutor.ui.screen.test_info.TestsListType
import ru.project.tutor.ui.screen.test_info.composable.BaseTestCoverCard

@Composable
fun TestCoverCardTestManager(
    title: String,
    iconId: Int,
    colorId: Int,
    countQuestions: Int? = null,
    testsListType: TestsListType? = null,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    BaseTestCoverCard(
        title = title,
        iconId = iconId,
        colorId = colorId,
        countQuestions = countQuestions,
        testsListType = testsListType,
        modifier = modifier,
        onClick = onClick
    )
}