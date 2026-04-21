package ru.project.tutor.ui.screen.test_info.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.project.tutor.R
import ru.project.tutor.common_ui.composable.elements.SpacerHeight
import ru.project.tutor.common_ui.composable.theme.AppTheme
import ru.project.tutor.common_ui.composable.utils.noRippleClickable
import ru.project.tutor.ui.models.CountQuestionsInTestUi

@Composable
@Preview
fun TestMenuBlockPreview() {
    TestMenuBlock(
        countQuestionsInTestUi = CountQuestionsInTestUi(
            normalQuestionsCount = 3,
            favoriteQuestionsCount = 2,
            errorQuestionsCount = 23
        ),
        {},
        {},
        {},
        {}
    )
}

@Composable
fun TestMenuBlock(
    countQuestionsInTestUi: CountQuestionsInTestUi,
    onClickCreateTest: () -> Unit,
    onClickMyTests: () -> Unit,
    onClickFavoriteQuestions: () -> Unit,
    onClickErrors: () -> Unit,
) {
    Box(modifier = Modifier.padding(horizontal = 16.dp)) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(color = AppTheme.colors.background.secondaryTwo)
                .padding(vertical = 20.dp, horizontal = 16.dp)
        ) {
            MenuItem(
                title = stringResource(R.string.test_info_create_test_title),
                description = stringResource(R.string.test_info_create_test_description),
                colorTitle = AppTheme.colors.text.blueTwo,
                isVisibleDivider = true,
                onClick = onClickCreateTest
            )
            SpacerHeight(16.dp)
            MenuItem(
                title = stringResource(R.string.test_info_test_title_block),
                description = stringResource(R.string.test_info_my_test_description),
                colorTitle = AppTheme.colors.text.blueTwo,
                isVisibleDivider = true,
                onClick = onClickMyTests
            )
            SpacerHeight(16.dp)
            val countFavoritesText = pluralStringResource(
                R.plurals.count_questions,
                countQuestionsInTestUi.favoriteQuestionsCount,
                countQuestionsInTestUi.favoriteQuestionsCount
            )
            MenuItem(
                title = stringResource(R.string.test_info_favorites_title),
                description = stringResource(
                    R.string.test_info_favorites_description,
                    countFavoritesText
                ),
                colorTitle = AppTheme.colors.text.blueTwo,
                isVisibleDivider = true,
                onClick = onClickFavoriteQuestions
            )
            SpacerHeight(16.dp)
            val countErrorsText = pluralStringResource(
                R.plurals.count_errors2,
                countQuestionsInTestUi.errorQuestionsCount,
                countQuestionsInTestUi.errorQuestionsCount
            )
            MenuItem(
                title = stringResource(R.string.test_info_error_title),
                description = stringResource(R.string.test_info_error_description, countErrorsText),
                colorTitle = AppTheme.colors.text.red,
                isVisibleDivider = false,
                onClick = onClickErrors
            )
        }
    }
}

@Composable
private fun MenuItem(
    title: String,
    description: String,
    colorTitle: Color,
    isVisibleDivider: Boolean,
    onClick: () -> Unit = {},
) {
    Column(Modifier.noRippleClickable { onClick() }) {
        Text(title, style = AppTheme.textStyle.subheadOne, color = colorTitle)
        SpacerHeight(8.dp)
        Text(
            description,
            style = AppTheme.textStyle.captionTwo,
            color = AppTheme.colors.text.placeholder
        )
        if (isVisibleDivider) {
            SpacerHeight(16.dp)
            HorizontalDivider(color = AppTheme.colors.background.mask)
        }
    }
}