package vn.vtv.vtvgotv.features.search.presentation.composables

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.stringResource
import androidx.tv.material3.*
import vn.vtv.vtvgotv.R

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun KeyButton(
    label: String,
    span: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val baseWidth = 36.dp
    val height = 36.dp
    val spacing = 8.dp
    val width = baseWidth * span + spacing * (span - 1)

    var isFocused by remember { mutableStateOf(false) }

    Surface(
        onClick = onClick,
        modifier = modifier
            .width(width)
            .height(height)
            .onFocusChanged { isFocused = it.isFocused },
        shape = ClickableSurfaceDefaults.shape(shape = RoundedCornerShape(6.dp)),
        colors = ClickableSurfaceDefaults.colors(
            containerColor = Color.White.copy(alpha = 0.12f),
            focusedContainerColor = Color.White,
            pressedContainerColor = Color.White
        ),
        scale = ClickableSurfaceDefaults.scale(focusedScale = 1.08f)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            when (label) {
                "BACKSPACE" -> {
                    BackspaceIcon(isFocused = isFocused)
                }
                "SHIFT" -> {
                    ShiftIcon(isFocused = isFocused)
                }
                "SPACE" -> {
                    Canvas(modifier = Modifier.size(16.dp, 8.dp)) {
                        val strokeWidth = 2.dp.toPx()
                        val color = if (isFocused) Color.Black else Color.White
                        val w = size.width
                        val h = size.height
                        drawLine(
                            color = color,
                            start = androidx.compose.ui.geometry.Offset(0f, 0f),
                            end = androidx.compose.ui.geometry.Offset(0f, h),
                            strokeWidth = strokeWidth
                        )
                        drawLine(
                            color = color,
                            start = androidx.compose.ui.geometry.Offset(0f, h),
                            end = androidx.compose.ui.geometry.Offset(w, h),
                            strokeWidth = strokeWidth
                        )
                        drawLine(
                            color = color,
                            start = androidx.compose.ui.geometry.Offset(w, h),
                            end = androidx.compose.ui.geometry.Offset(w, 0f),
                            strokeWidth = strokeWidth
                        )
                    }
                }
                else -> {
                    Text(
                        text = if (label == "XÓA") stringResource(id = R.string.keyboard_clear) else label,
                        color = if (isFocused) Color.Black else Color.White,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun BackspaceIcon(isFocused: Boolean, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier.size(18.dp)) {
        val color = if (isFocused) Color.Black else Color.White
        val strokeWidth = 2.dp.toPx()
        val w = size.width
        val h = size.height

        val path = androidx.compose.ui.graphics.Path().apply {
            moveTo(w * 0.15f, h * 0.5f)
            lineTo(w * 0.4f, h * 0.2f)
            lineTo(w * 0.9f, h * 0.2f)
            lineTo(w * 0.9f, h * 0.8f)
            lineTo(w * 0.4f, h * 0.8f)
            close()
        }
        drawPath(
            path = path,
            color = color,
            style = Stroke(width = strokeWidth)
        )

        val xSize = 4.dp.toPx()
        val cx = w * 0.62f
        val cy = h * 0.5f

        drawLine(
            color = color,
            start = androidx.compose.ui.geometry.Offset(cx - xSize, cy - xSize),
            end = androidx.compose.ui.geometry.Offset(cx + xSize, cy + xSize),
            strokeWidth = strokeWidth
        )
        drawLine(
            color = color,
            start = androidx.compose.ui.geometry.Offset(cx + xSize, cy - xSize),
            end = androidx.compose.ui.geometry.Offset(cx - xSize, cy + xSize),
            strokeWidth = strokeWidth
        )
    }
}

@Composable
fun ShiftIcon(isFocused: Boolean, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier.size(18.dp)) {
        val color = if (isFocused) Color.Black else Color.White
        val strokeWidth = 2.dp.toPx()
        val w = size.width
        val h = size.height

        val path = androidx.compose.ui.graphics.Path().apply {
            moveTo(w * 0.5f, h * 0.15f)
            lineTo(w * 0.2f, h * 0.48f)
            lineTo(w * 0.38f, h * 0.48f)
            lineTo(w * 0.38f, h * 0.85f)
            lineTo(w * 0.62f, h * 0.85f)
            lineTo(w * 0.62f, h * 0.48f)
            lineTo(w * 0.8f, h * 0.48f)
            close()
        }
        drawPath(
            path = path,
            color = color,
            style = Stroke(width = strokeWidth)
        )
    }
}
