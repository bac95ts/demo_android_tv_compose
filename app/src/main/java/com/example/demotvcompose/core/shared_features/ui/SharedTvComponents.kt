package com.example.demotvcompose.core.shared_features.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer

/**
 * Premium focus-scaling modifier wrapper optimized for Android TV D-pad interactions
 */
@Composable
fun Modifier.tvFocusScale(
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    scaleFocused: Float = 1.06f,
    scaleUnfocused: Float = 1.0f
): Modifier {
    val isFocused by interactionSource.collectIsFocusedAsState()
    val scale by animateFloatAsState(
        targetValue = if (isFocused) scaleFocused else scaleUnfocused,
        label = "TVFocusScale"
    )
    return this.then(
        Modifier.graphicsLayer {
            scaleX = scale
            scaleY = scale
        }
    )
}
