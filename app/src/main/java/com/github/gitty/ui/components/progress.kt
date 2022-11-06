package com.github.gitty.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp

@Composable
fun ShowSearchProgress(
    isLoadingData: Boolean
) {
    if (isLoadingData) {
        InfinitelyFlowingCircles()
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 28.dp, end = 28.dp),
            contentAlignment = Alignment.Center
        ) {
            AnimatedSearch()
        }
    }
}

@Composable
fun InfinitelyFlowingCircles() {

    // Same color with different variants for different circles.
    val primaryColor = MaterialTheme.colors.primary
    val frontCircle = primaryColor.copy(0.75f)
    val midCircle = primaryColor.copy(0.50f)
    val backCircle = primaryColor.copy(0.25f)

    DrawCircleOnCanvas(
        scale = scaleInfiniteTransition(targetValue = 2f, durationMillis = 600),
        color = backCircle,
        radiusRatio = 4f
    )

    DrawCircleOnCanvas(
        scale = scaleInfiniteTransition(targetValue = 2.5f, durationMillis = 800),
        color = midCircle,
        radiusRatio = 6f
    )

    DrawCircleOnCanvas(
        scale = scaleInfiniteTransition(targetValue = 3f, durationMillis = 1000),
        color = frontCircle,
        radiusRatio = 12f
    )
}

@Composable
private fun DrawCircleOnCanvas(
    scale: Float,
    color: Color,
    radiusRatio: Float
) {
    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
    ) {
        val canvasWidth = size.width
        val canvasHeight = size.height
        drawCircle(
            color = color,
            center = Offset(
                x = canvasWidth / 2,
                y = canvasHeight / 2
            ),
            radius = size.minDimension / radiusRatio,
        )
    }
}

@Composable
private fun scaleInfiniteTransition(
    initialValue: Float = 0f,
    targetValue: Float,
    durationMillis: Int,
): Float {
    val infiniteTransition = rememberInfiniteTransition()
    val scale: Float by infiniteTransition.animateFloat(
        initialValue = initialValue,
        targetValue = targetValue,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )
    return scale
}


@Composable
fun AnimatedSearch() {

    // Simple progressive circle looking animation
    val animateCircle = remember { Animatable(0f) }.apply {
        AnimateShapeInfinitely(this)
    }

    // 0.6f for initial value to reduce floating time of line to reach it's final state.
    // Settings it to 0f -> final animation output looks kind of aggressive movements.
    val animateLine = remember { Animatable(0.6f) }.apply {
        AnimateShapeInfinitely(this)
    }

    // Appears different for dark/light theme colors.
    val surfaceColor = MaterialTheme.colors.surface

    // Arcs & Line drawn in canvas at animation final state looks like search icon.
    Canvas(
        modifier = Modifier
    ) {
        drawArc(
            color = surfaceColor,
            startAngle = 45f,
            sweepAngle = 360f * animateCircle.value,
            useCenter = false,
            size = Size(80f, 80f),
            style = Stroke(16f, cap = StrokeCap.Round)
        )

        drawLine(
            color = surfaceColor,
            strokeWidth = 16f,
            cap = StrokeCap.Round,
            start = Offset(
                animateLine.value * 80f,
                animateLine.value * 80f
            ),
            end = Offset(
                animateLine.value * 110f,
                animateLine.value * 110f
            )
        )
    }
}

@Composable
private fun AnimateShapeInfinitely(
    animateShape: Animatable<Float, AnimationVector1D>,
    targetValue: Float = 1f,
    durationMillis: Int = 1000
) {
    LaunchedEffect(animateShape) {
        animateShape.animateTo(
            targetValue = targetValue,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = durationMillis,
                    easing = LinearEasing
                ),
                repeatMode = RepeatMode.Restart
            )
        )
    }
}
