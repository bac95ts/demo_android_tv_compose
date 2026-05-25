package vn.vtv.vtvgotv.features.search.presentation

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.stringResource
import androidx.tv.material3.*
import vn.vtv.vtvgotv.R
import vn.vtv.vtvgotv.core.theme.DemoTVComposeTheme
import vn.vtv.vtvgotv.features.search.presentation.composables.KeyButton

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun SearchScreen(
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") }
    var isShiftActive by remember { mutableStateOf(true) }
    var isSymbolMode by remember { mutableStateOf(false) }

    // Rows matching the exact 12-column layouts from screen recording / screenshot
    val row1 = listOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "XÓA")
    val row2 = listOf("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "BACKSPACE")
    val row3 = listOf("L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "SHIFT")
    val row4 = listOf("W", "X", "Y", "Z", "SPACE", ".", "@", "#$%")

    val symbolRow2 = listOf("!", "@", "#", "$", "%", "^", "&", "*", "(", ")", "_", "BACKSPACE")
    val symbolRow3 = listOf("+", "-", "=", "{", "}", "[", "]", ";", ":", "'", "\"", "SHIFT")
    val symbolRow4 = listOf("<", ">", "?", "/", "SPACE", ",", "\\", "ABC")

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.radialGradient(
                    colors = listOf(
                        Color(0xFF2E3137), // Subtle lighter center
                        Color(0xFF141518)  // Premium deep dark borders
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(16.dp)
        ) {
            // Search Input Box
            Row(
                modifier = Modifier
                    .width(460.dp) // Identical ratio to screenshot
                    .height(48.dp)
                    .background(Color(0xFF242528), shape = RoundedCornerShape(10.dp))
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CustomSearchIcon(
                    color = Color.White.copy(alpha = 0.5f),
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))

                Box(modifier = Modifier.weight(1f)) {
                    if (searchQuery.isEmpty()) {
                        Text(
                            text = stringResource(id = R.string.search_placeholder),
                            color = Color.White.copy(alpha = 0.4f),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    } else {
                        Text(
                            text = searchQuery,
                            color = Color.White,
                            style = MaterialTheme.typography.bodyLarge,
                            maxLines = 1
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Premium custom Keyboard Panel
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Row 1
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    row1.forEach { key ->
                        val span = if (key == "XÓA") 2 else 1
                        KeyButton(
                            label = key,
                            span = span,
                            onClick = {
                                if (key == "XÓA") {
                                    searchQuery = ""
                                } else {
                                    searchQuery += key
                                }
                            }
                        )
                    }
                }

                // Row 2
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    val activeRow2 = if (isSymbolMode) symbolRow2 else row2
                    activeRow2.forEach { key ->
                        KeyButton(
                            label = key,
                            span = 1,
                            onClick = {
                                when (key) {
                                    "BACKSPACE" -> {
                                        if (searchQuery.isNotEmpty()) {
                                            searchQuery = searchQuery.dropLast(1)
                                        }
                                    }
                                    else -> {
                                        val typedChar = if (isShiftActive) key else key.lowercase()
                                        searchQuery += typedChar
                                    }
                                }
                            }
                        )
                    }
                }

                // Row 3
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    val activeRow3 = if (isSymbolMode) symbolRow3 else row3
                    activeRow3.forEach { key ->
                        KeyButton(
                            label = key,
                            span = 1,
                            onClick = {
                                when (key) {
                                    "SHIFT" -> {
                                        isShiftActive = !isShiftActive
                                    }
                                    else -> {
                                        val typedChar = if (isShiftActive) key else key.lowercase()
                                        searchQuery += typedChar
                                    }
                                }
                            }
                        )
                    }
                }

                // Row 4
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    val activeRow4 = if (isSymbolMode) symbolRow4 else row4
                    activeRow4.forEach { key ->
                        val calculatedSpan = when (key) {
                            "SPACE" -> 4
                            "#$%", "ABC" -> 2
                            else -> 1
                        }
                        KeyButton(
                            label = key,
                            span = calculatedSpan,
                            onClick = {
                                when (key) {
                                    "SPACE" -> {
                                        searchQuery += " "
                                    }
                                    "#$%" -> {
                                        isSymbolMode = true
                                    }
                                    "ABC" -> {
                                        isSymbolMode = false
                                    }
                                    else -> {
                                        val typedChar = if (isShiftActive) key else key.lowercase()
                                        searchQuery += typedChar
                                    }
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CustomSearchIcon(color: Color, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val strokeWidth = 2.dp.toPx()
        val w = size.width
        val h = size.height
        val r = w * 0.32f
        val cx = w * 0.42f
        val cy = h * 0.42f

        drawCircle(
            color = color,
            radius = r,
            center = Offset(cx, cy),
            style = Stroke(width = strokeWidth)
        )

        val handleStartFactor = 0.7071f
        val hxStart = cx + r * handleStartFactor
        val hyStart = cy + r * handleStartFactor

        drawLine(
            color = color,
            start = Offset(hxStart, hyStart),
            end = Offset(w * 0.82f, h * 0.82f),
            strokeWidth = strokeWidth
        )
    }
}

@Preview(showBackground = true, widthDp = 960, heightDp = 540)
@Composable
fun SearchScreenPreview() {
    DemoTVComposeTheme {
        SearchScreen()
    }
}
