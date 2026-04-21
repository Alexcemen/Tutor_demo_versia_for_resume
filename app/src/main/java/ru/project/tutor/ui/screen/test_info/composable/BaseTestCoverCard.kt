package ru.project.tutor.ui.screen.test_info.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import ru.project.tutor.R
import ru.project.tutor.common_ui.composable.theme.AppTheme
import ru.project.tutor.common_ui.composable.utils.noRippleClickable
import ru.project.tutor.ui.screen.test_info.TestsListType
import ru.project.tutor.utils.TestDesign

@Composable
fun BaseTestCoverCard(
    title: String,
    iconId: Int,
    colorId: Int,
    countQuestions: Int? = null,
    testsListType: TestsListType? = null,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(136.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(TestDesign.ColorValue.getColor(colorId))
            .noRippleClickable { onClick() },
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(136.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column(
                modifier = Modifier
                    .padding(start = 16.dp, top = 16.dp)
                    .weight(1f)
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = title,
                    style = AppTheme.textStyle.subtitleThree,
                    color = AppTheme.colors.background.basicUniform,
                    maxLines = if (testsListType == null) 3 else 2,
                    overflow = TextOverflow.Ellipsis
                )

                testsListType?.let { type ->
                    countQuestions?.let { count ->
                        Spacer(modifier = Modifier.height(16.dp))
                        Row {
                            Text(
                                text = pluralStringResource(
                                    id = when (type) {
                                        TestsListType.NORMAL -> R.plurals.count_questions
                                        TestsListType.FAVORITE -> R.plurals.count_favorite_questions
                                        TestsListType.ERRORS -> R.plurals.count_errors
                                    },
                                    count,
                                    count
                                ),
                                style = AppTheme.textStyle.subheadThree,
                                color = AppTheme.colors.background.basicUniform,
                            )
                        }
                    }
                }
            }

            Image(
                painter = painterResource(TestDesign.ImageValue.getImage(imageId = iconId)),
                contentDescription = "",
                modifier = TestDesign.ImageValue.getModifierOffset(imageId = iconId),
                contentScale = TestDesign.ImageValue.getContentScale(imageId = iconId)
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
    }
}