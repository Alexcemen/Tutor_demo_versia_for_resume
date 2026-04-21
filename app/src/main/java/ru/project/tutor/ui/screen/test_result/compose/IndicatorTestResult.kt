package ru.project.tutor.ui.screen.test_result.compose

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ru.project.tutor.common_ui.composable.theme.AppTheme

@Preview(showBackground = true)
@Composable
private fun IndicatorTestResultPreview() {
    IndicatorTestResult(
        indicatorValue = 80
    )
}


@Composable
fun IndicatorTestResult(
    canvasSize: Dp = 250.dp,
    indicatorValue: Int = 0,
    maxIndicatorValue: Int = 100,
    backgroundIndicatorColor: Color = AppTheme.colors.background.mask,
    backgroundIndicatorStrokeWidth: Float = 45f,
    foregroundIndicatorColor: Color = AppTheme.colors.background.green,
    foregroundIndicatorStrokeWidth: Float = 45f,
    animateOnce: Boolean = true,
) {
    val animatedIndicatorValue = remember { Animatable(0f) }
    var hasAnimated by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(indicatorValue, animateOnce) {
        val target = indicatorValue.toFloat()
        if (animateOnce && hasAnimated) {
            animatedIndicatorValue.snapTo(target)
        } else {
            animatedIndicatorValue.animateTo(target, animationSpec = tween(1000))
            hasAnimated = true
        }
    }

    val percentage = (animatedIndicatorValue.value / maxIndicatorValue) * 100f
    val sweepAngle = 1.8f * percentage

    Column(
        modifier = Modifier
            .size(canvasSize)
            .drawBehind {
                val componentSize = size / 1.2f
                backgroundIndicator(
                    componentSize = componentSize,
                    indicatorColor = backgroundIndicatorColor,
                    indicatorStrokeWidth = backgroundIndicatorStrokeWidth
                )
                foregroundIndicator(
                    sweepAngle = sweepAngle,
                    componentSize = componentSize,
                    indicatorColor = foregroundIndicatorColor,
                    indicatorStrokeWidth = foregroundIndicatorStrokeWidth
                )
                foregroundIndicator(
                    sweepAngle = -sweepAngle,
                    componentSize = componentSize,
                    indicatorColor = foregroundIndicatorColor,
                    indicatorStrokeWidth = foregroundIndicatorStrokeWidth
                )
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "$indicatorValue из $maxIndicatorValue",
            style = AppTheme.textStyle.titleOne,
            color = AppTheme.colors.text.primary
        )
    }
}

fun DrawScope.backgroundIndicator(
    componentSize: Size,
    indicatorColor: Color,
    indicatorStrokeWidth: Float,
) {
    drawArc(
        size = componentSize,
        color = indicatorColor,
        startAngle = 0f,
        sweepAngle = 360f,
        useCenter = false,
        style = Stroke(
            width = indicatorStrokeWidth,
            cap = StrokeCap.Round
        ),
        topLeft = Offset(
            x = (size.width - componentSize.width) / 2f,
            y = (size.height - componentSize.height) / 2f,
        )
    )
}

fun DrawScope.foregroundIndicator(
    sweepAngle: Float,
    componentSize: Size,
    indicatorColor: Color,
    indicatorStrokeWidth: Float,
) {
    drawArc(
        size = componentSize,
        color = indicatorColor,
        startAngle = 270f,
        sweepAngle = sweepAngle,
        useCenter = false,
        style = Stroke(
            width = indicatorStrokeWidth,
            cap = StrokeCap.Round
        ),
        topLeft = Offset(
            x = (size.width - componentSize.width) / 2f,
            y = (size.height - componentSize.height) / 2f,
        )
    )
}