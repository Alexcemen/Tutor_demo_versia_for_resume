package ru.project.tutor.ui.screen.test_info.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.project.tutor.R
import ru.project.tutor.common_ui.composable.elements.SpacerHeight
import ru.project.tutor.common_ui.composable.elements.SpacerWidth
import ru.project.tutor.common_ui.composable.theme.AppTheme
import ru.project.tutor.ui.screen.test_info.composable.models.UserStatisticsUi

@Composable
@Preview(showBackground = true)
fun TestStatisticsBlockPreview() {
    TestStatisticsBlock(
        UserStatisticsUi(
            firstLaunchDate = "12.12.2024",
            allCountTestCreated = 0,
            allCountErrors = 1,
            allCountTestsCompleted = 12,
            allCountTime = "5ч"
        )
    )
}

@Composable
fun TestStatisticsBlock(userStatisticsUi: UserStatisticsUi) {
    Column(Modifier.background(color = AppTheme.colors.background.basic)) {
        Text(
            text = stringResource(R.string.statistic_info_block_title),
            style = AppTheme.textStyle.titleOne,
            color = AppTheme.colors.text.primary,
            modifier = Modifier.padding(start = 16.dp, end = 16.dp)
        )
        SpacerHeight(8.dp)
        Text(
            text = stringResource(
                R.string.statistic_info_block_description,
                userStatisticsUi.firstLaunchDate
            ),
            style = AppTheme.textStyle.subtitleThree,
            color = AppTheme.colors.text.placeholder,
            modifier = Modifier.padding(start = 16.dp, end = 16.dp)
        )
        SpacerHeight(16.dp)
        Row(
            Modifier
                .padding(start = 16.dp, end = 16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(Modifier.weight(1f)) {
                StatisticsItem(
                    title = pluralStringResource(
                        R.plurals.count_tests,
                        userStatisticsUi.allCountTestCreated,
                        userStatisticsUi.allCountTestCreated
                    ),
                    description = stringResource(R.string.statistic_info_block_tests_description)
                )
                SpacerHeight(8.dp)
                StatisticsItem(
                    title = pluralStringResource(
                        R.plurals.count_errors,
                        userStatisticsUi.allCountErrors,
                        userStatisticsUi.allCountErrors
                    ),
                    description = stringResource(R.string.statistic_info_block_errors_description)
                )
            }
            SpacerWidth(8.dp)
            Column(Modifier.weight(1f)) {
                StatisticsItem(
                    title = pluralStringResource(
                        R.plurals.count_tests,
                        userStatisticsUi.allCountTestsCompleted,
                        userStatisticsUi.allCountTestsCompleted
                    ),
                    description = stringResource(R.string.statistic_info_block_test_completed_description)
                )
                SpacerHeight(8.dp)
                StatisticsItem(
                    title = userStatisticsUi.allCountTime,
                    description = stringResource(R.string.statistic_info_block_time_description)
                )
            }
        }
    }
}

@Composable
private fun StatisticsItem(title: String, description: String) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(color = AppTheme.colors.background.secondaryTwo)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp, end = 4.dp)
        ) {
            Text(
                title,
                style = AppTheme.textStyle.subheadTwo,
                color = AppTheme.colors.text.primary
            )
            Text(
                description,
                style = AppTheme.textStyle.captionTwo,
                color = AppTheme.colors.text.placeholder
            )
        }
//        Image(
//            painter = painterResource(R.drawable.ic_info),
//            modifier = Modifier.padding(end = 16.dp),
//            contentDescription = ""
//        )
    }
}