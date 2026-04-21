package ru.project.tutor.ui.screen.test_info.composable

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ru.project.tutor.ui.screen.test_info.TestsListType

@Composable
@Preview
fun TestCoverCard1Preview() {
    TestCoverCard(
        title = "Тест знания по математике",
        1,
        3
    ) {}
}

@Composable
@Preview
fun TestCoverCard2Preview() {
    TestCoverCard(
        title = "Тест знания по математике",
        2,
        1
    ) {}
}

@Composable
@Preview
fun TestCoverCard3Preview() {
    TestCoverCard(
        title = "Тест знания по математике sdf dsf fdsfsd fsdf sd fdsf sd fds sdfd sd sd sd",
        3,
        3
    ) {}
}

@Composable
@Preview
fun TestCoverCardCountPreview() {
    TestCoverCard(
        title = "Тест знания по математике sdf dsf fdsfsd fsdf sd fdsf sd fds sdfd sd sd sd",
        3,
        3,
        countQuestions = 1,
        testsListType = TestsListType.ERRORS
    ) {}
}


@Composable
fun TestCoverCard(
    title: String,
    iconId: Int,
    colorId: Int,
    countQuestions: Int? = null,
    testsListType: TestsListType? = null,
    onClick: () -> Unit,
) {
    BaseTestCoverCard(
        title = title,
        iconId = iconId,
        colorId = colorId,
        countQuestions = countQuestions,
        testsListType = testsListType,
        modifier = Modifier,
        onClick = onClick
    )
}