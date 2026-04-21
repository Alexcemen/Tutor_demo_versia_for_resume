package ru.project.tutor.utils

import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import ru.project.tutor.R
import ru.project.tutor.common_ui.composable.theme.AppTheme

object TestDesign {
    object ColorValue {
        private const val FIRST = 1
        private const val SECOND = 2
        private const val THREE = 3

        fun generateColorValue(): Int {
            return (FIRST..THREE).random()
        }

        @Composable
        fun getColor(colorId: Int) = when (colorId) {
            FIRST -> AppTheme.colors.background.blue
            SECOND -> AppTheme.colors.background.red
            THREE -> AppTheme.colors.background.green
            else -> AppTheme.colors.background.blue
        }
    }

    object ImageValue {
        private const val FIRST = 1
        private const val SECOND = 2
        private const val THREE = 3

        fun generateImageValue(): Int {
            return (FIRST..THREE).random()
        }

        @Composable
        fun getImage(imageId: Int) = when (imageId) {
            FIRST -> R.drawable.ic_test_background_first
            SECOND -> R.drawable.ic_test_background_second
            THREE -> R.drawable.ic_test_background_three
            else -> R.drawable.ic_test_background_first
        }

        @Composable
        fun getModifierOffset(imageId: Int) = when (imageId) {
            FIRST -> Modifier
                .offset(x = 15.dp)
                .width(120.dp)

            SECOND -> Modifier
                .offset(x = 32.dp, y = 15.dp)

            THREE -> Modifier
                .offset(x = 32.dp, y = 40.dp)

            else -> Modifier
                .offset(x = 15.dp)
        }

        @Composable
        fun getContentScale(imageId: Int) = when (imageId) {
            FIRST -> ContentScale.Crop

            SECOND -> ContentScale.Fit

            THREE -> ContentScale.FillHeight

            else -> ContentScale.Crop
        }
    }
}