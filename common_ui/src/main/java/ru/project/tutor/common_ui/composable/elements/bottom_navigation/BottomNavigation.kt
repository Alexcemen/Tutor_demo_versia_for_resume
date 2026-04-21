package ru.project.tutor.common_ui.composable.elements.bottom_navigation

import androidx.annotation.DrawableRes
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import ru.project.tutor.common_ui.R
import ru.project.tutor.common_ui.composable.theme.AppTheme

@Composable
@Preview(showBackground = true)
fun BottomNavigationPreview() {
    BottomNavigation(
        listNavigation = listOf(
            BottomNavigationItem(
                position = 1,
                text = "Первый",
                image = R.drawable.ic_arrow_right,
                isSelected = true,
            ),
            BottomNavigationItem(
                position = 2,
                text = "Второй",
                image = R.drawable.ic_arrow_right,
                isSelected = false,
            ),
        ),
        {}
    )
}


@Composable
fun BottomNavigation(listNavigation: List<BottomNavigationItem>, onClick: (Int) -> Unit) {
    NavigationBar(
        containerColor = AppTheme.colors.background.basic,
        contentColor = AppTheme.colors.background.primary
    ) {
        listNavigation.forEach {
            NavigationBarItem(
                selected = it.isSelected,
                onClick = { onClick(it.position) },
                icon = {
                    Icon(painter = painterResource(it.image), null)
                },
                label = {
                    Text(
                        text = it.text,
                        color = AppTheme.colors.text.primary,
                        style = AppTheme.textStyle.captionThree
                    )
                },
                alwaysShowLabel = true,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = AppTheme.colors.text.whiteUniform, // Icon color when selected
                    unselectedIconColor = AppTheme.colors.background.secondary, // Icon color when not selected
                    selectedTextColor = AppTheme.colors.background.blue, // Label color when selected
                    unselectedTextColor = AppTheme.colors.text.placeholder,
                    indicatorColor = AppTheme.colors.background.blue // Highlight color for selected item
                )
            )
        }
    }
}

data class BottomNavigationItem(
    val position: Int,
    val text: String,
    @DrawableRes val image: Int,
    val isSelected: Boolean,
)