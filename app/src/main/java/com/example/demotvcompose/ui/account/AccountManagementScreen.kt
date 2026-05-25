package com.example.demotvcompose.ui.account

import androidx.compose.animation.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.tv.material3.*
import com.example.demotvcompose.theme.DemoTVComposeTheme
import com.example.demotvcompose.ui.search.KeyButton
import kotlinx.coroutines.delay
import org.koin.compose.koinInject
import com.example.demotvcompose.data.repository.AccountRepository

enum class LoginStep {
    EMAIL, PASSWORD, SUCCESS
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun AccountManagementScreen(
    onLoginSuccess: () -> Unit,
    modifier: Modifier = Modifier,
    accountRepository: AccountRepository = koinInject()
) {

    var loginStep by remember { mutableStateOf(LoginStep.EMAIL) }
    var emailInput by remember { mutableStateOf("") }
    var passwordInput by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }
    var isShiftActive by remember { mutableStateOf(false) } // Default lowercase as in screenshot 1
    var isSymbolMode by remember { mutableStateOf(false) }

    // Keyboard data matching 12-column exact key arrangement
    val row1 = listOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "XÓA")
    val row2 = listOf("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "BACKSPACE")
    val row3 = listOf("L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "SHIFT")
    val row4 = listOf("W", "X", "Y", "Z", "SPACE", ".", "@", "#$%")

    val symbolRow2 = listOf("!", "@", "#", "$", "%", "^", "&", "*", "(", ")", "_", "BACKSPACE")
    val symbolRow3 = listOf("+", "-", "=", "{", "}", "[", "]", ";", ":", "'", "\"", "SHIFT")
    val symbolRow4 = listOf("<", ">", "?", "/", "SPACE", ",", "\\", "ABC")

    // Helper validation functions
    fun validateEmail(email: String): Boolean {
        val emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$".toRegex()
        return email.matches(emailRegex)
    }

    LaunchedEffect(loginStep) {
        if (loginStep == LoginStep.SUCCESS) {
            delay(1500)
            onLoginSuccess()
        }
    }

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
        AnimatedContent(
            targetState = loginStep,
            transitionSpec = {
                fadeIn() togetherWith fadeOut()
            },
            label = "LoginStepTransition"
        ) { step ->
            when (step) {
                LoginStep.EMAIL -> {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(16.dp)
                    ) {
                        // Input Field
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Row(
                                modifier = Modifier
                                    .width(460.dp)
                                    .height(48.dp)
                                    .background(
                                        Color(0xFF242528),
                                        shape = RoundedCornerShape(10.dp)
                                    )
                                    .padding(horizontal = 16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                CustomUserIcon(
                                    color = Color.White.copy(alpha = 0.5f),
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(12.dp))

                                Box(modifier = Modifier.weight(1f)) {
                                    if (emailInput.isEmpty()) {
                                        Text(
                                            text = "Nhập email/số điện thoại",
                                            color = Color.White.copy(alpha = 0.4f),
                                            style = MaterialTheme.typography.bodyLarge
                                        )
                                    } else {
                                        Text(
                                            text = emailInput,
                                            color = Color.White,
                                            style = MaterialTheme.typography.bodyLarge,
                                            maxLines = 1
                                        )
                                    }
                                }
                            }

                            // Error text
                            if (emailError != null) {
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = emailError!!,
                                    color = MaterialTheme.colorScheme.primary,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        // Keyboard
                        KeyboardPanel(
                            row1 = row1,
                            row2 = if (isSymbolMode) symbolRow2 else row2,
                            row3 = if (isSymbolMode) symbolRow3 else row3,
                            row4 = if (isSymbolMode) symbolRow4 else row4,
                            isShiftActive = isShiftActive,
                            isSymbolMode = isSymbolMode,
                            onKeyPress = { key ->
                                emailError = null
                                when (key) {
                                    "XÓA" -> emailInput = ""
                                    "BACKSPACE" -> {
                                        if (emailInput.isNotEmpty()) {
                                            emailInput = emailInput.dropLast(1)
                                        }
                                    }

                                    "SHIFT" -> isShiftActive = !isShiftActive
                                    "SPACE" -> emailInput += " "
                                    "#$%" -> isSymbolMode = true
                                    "ABC" -> isSymbolMode = false
                                    else -> {
                                        val typedChar = if (isShiftActive) key else key.lowercase()
                                        emailInput += typedChar
                                    }
                                }
                            }
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        // Continue Button
                        ActionButton(
                            text = "Tiếp tục",
                            onClick = {
                                if (emailInput.isEmpty()) {
                                    emailError = "Vui lòng nhập Email"
                                } else if (!validateEmail(emailInput)) {
                                    emailError = "Email không đúng định dạng"
                                } else {
                                    emailError = null
                                    isShiftActive =
                                        true // Start password with uppercase by default (standard)
                                    loginStep = LoginStep.PASSWORD
                                }
                            },
                            modifier = Modifier.width(200.dp)
                        )
                    }
                }

                LoginStep.PASSWORD -> {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(16.dp)
                    ) {
                        // Input Field
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Row(
                                modifier = Modifier
                                    .width(460.dp)
                                    .height(48.dp)
                                    .background(
                                        Color(0xFF242528),
                                        shape = RoundedCornerShape(10.dp)
                                    )
                                    .padding(horizontal = 16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                CustomLockIcon(
                                    color = Color.White.copy(alpha = 0.5f),
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(12.dp))

                                Box(modifier = Modifier.weight(1f)) {
                                    if (passwordInput.isEmpty()) {
                                        Text(
                                            text = "Nhập mật khẩu",
                                            color = Color.White.copy(alpha = 0.4f),
                                            style = MaterialTheme.typography.bodyLarge
                                        )
                                    } else {
                                        // Mask password visually with dots
                                        Text(
                                            text = "•".repeat(passwordInput.length),
                                            color = Color.White,
                                            style = MaterialTheme.typography.bodyLarge,
                                            maxLines = 1
                                        )
                                    }
                                }
                            }

                            if (passwordError != null) {
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = passwordError!!,
                                    color = MaterialTheme.colorScheme.primary,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        // Keyboard
                        KeyboardPanel(
                            row1 = row1,
                            row2 = if (isSymbolMode) symbolRow2 else row2,
                            row3 = if (isSymbolMode) symbolRow3 else row3,
                            row4 = if (isSymbolMode) symbolRow4 else row4,
                            isShiftActive = isShiftActive,
                            isSymbolMode = isSymbolMode,
                            onKeyPress = { key ->
                                passwordError = null
                                when (key) {
                                    "XÓA" -> passwordInput = ""
                                    "BACKSPACE" -> {
                                        if (passwordInput.isNotEmpty()) {
                                            passwordInput = passwordInput.dropLast(1)
                                        }
                                    }

                                    "SHIFT" -> isShiftActive = !isShiftActive
                                    "SPACE" -> passwordInput += " "
                                    "#$%" -> isSymbolMode = true
                                    "ABC" -> isSymbolMode = false
                                    else -> {
                                        val typedChar = if (isShiftActive) key else key.lowercase()
                                        passwordInput += typedChar
                                    }
                                }
                            }
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        // Actions Column
                        Column(
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            ActionButton(
                                text = "Đồng ý",
                                onClick = {
                                    if (passwordInput.isEmpty()) {
                                        passwordError = "Vui lòng nhập mật khẩu"
                                    } else if (passwordInput.length < 6) {
                                        passwordError = "Mật khẩu yêu cầu độ dài ít nhất 6 ký tự"
                                    } else {
                                        passwordError = null
                                        // Use repository to persist state
                                        accountRepository.setLoggedIn(true)
                                        loginStep = LoginStep.SUCCESS
                                    }
                                },
                                modifier = Modifier.width(260.dp)
                            )
                            ActionButton(
                                text = "Quên mật khẩu",
                                onClick = {
                                    passwordError = "Tính năng này đang được phát triển"
                                },
                                modifier = Modifier.width(260.dp)
                            )
                            ActionButton(
                                text = "Huỷ",
                                onClick = {
                                    passwordInput = ""
                                    passwordError = null
                                    loginStep = LoginStep.EMAIL
                                },
                                modifier = Modifier.width(260.dp)
                            )
                        }
                    }
                }

                LoginStep.SUCCESS -> {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        CustomSuccessCheckmark(modifier = Modifier.size(80.dp))
                        Spacer(modifier = Modifier.height(24.dp))
                        Text(
                            text = "Đăng nhập thành công!",
                            color = Color.White,
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun KeyboardPanel(
    row1: List<String>,
    row2: List<String>,
    row3: List<String>,
    row4: List<String>,
    isShiftActive: Boolean,
    isSymbolMode: Boolean,
    onKeyPress: (String) -> Unit
) {
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
                    onClick = { onKeyPress(key) }
                )
            }
        }

        // Row 2
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            row2.forEach { key ->
                KeyButton(
                    label = if (isShiftActive || isSymbolMode || key == "BACKSPACE") key else key.lowercase(),
                    span = 1,
                    onClick = { onKeyPress(key) }
                )
            }
        }

        // Row 3
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            row3.forEach { key ->
                KeyButton(
                    label = if (isShiftActive || isSymbolMode || key == "SHIFT") key else key.lowercase(),
                    span = 1,
                    onClick = { onKeyPress(key) }
                )
            }
        }

        // Row 4
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            row4.forEach { key ->
                val calculatedSpan = when (key) {
                    "SPACE" -> 4
                    "#$%", "ABC" -> 2
                    else -> 1
                }
                KeyButton(
                    label = if (isShiftActive || isSymbolMode || key == "SPACE" || key == "#$%" || key == "ABC") key else key.lowercase(),
                    span = calculatedSpan,
                    onClick = { onKeyPress(key) }
                )
            }
        }
    }
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun ActionButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isFocused by remember { mutableStateOf(false) }
    Surface(
        onClick = onClick,
        modifier = modifier
            .onFocusChanged { isFocused = it.isFocused },
        shape = ClickableSurfaceDefaults.shape(shape = RoundedCornerShape(20.dp)),
        colors = ClickableSurfaceDefaults.colors(
            containerColor = Color.White.copy(alpha = 0.12f),
            focusedContainerColor = Color.White,
            pressedContainerColor = Color.White
        ),
        scale = ClickableSurfaceDefaults.scale(focusedScale = 1.05f)
    ) {
        Box(
            modifier = Modifier
                .padding(vertical = 10.dp).fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                color = if (isFocused) Color.Black else Color.White,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }
}

@Composable
fun CustomUserIcon(color: Color, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val strokeWidth = 2.dp.toPx()
        val w = size.width
        val h = size.height

        drawCircle(
            color = color,
            radius = w * 0.22f,
            center = androidx.compose.ui.geometry.Offset(w * 0.5f, h * 0.35f),
            style = Stroke(width = strokeWidth)
        )

        drawArc(
            color = color,
            startAngle = 180f,
            sweepAngle = 180f,
            useCenter = false,
            topLeft = androidx.compose.ui.geometry.Offset(w * 0.2f, h * 0.62f),
            size = androidx.compose.ui.geometry.Size(w * 0.6f, h * 0.5f),
            style = Stroke(width = strokeWidth)
        )
    }
}

@Composable
fun CustomLockIcon(color: Color, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val strokeWidth = 2.dp.toPx()
        val w = size.width
        val h = size.height

        val rectTop = h * 0.45f
        val rectHeight = h * 0.45f
        val rectWidth = w * 0.6f
        val rectLeft = w * 0.2f
        drawRoundRect(
            color = color,
            topLeft = androidx.compose.ui.geometry.Offset(rectLeft, rectTop),
            size = androidx.compose.ui.geometry.Size(rectWidth, rectHeight),
            cornerRadius = androidx.compose.ui.geometry.CornerRadius(4.dp.toPx()),
            style = Stroke(width = strokeWidth)
        )

        val arcLeft = w * 0.32f
        val arcWidth = w * 0.36f
        val arcTop = h * 0.15f
        val arcHeight = h * 0.4f
        drawArc(
            color = color,
            startAngle = 180f,
            sweepAngle = 180f,
            useCenter = false,
            topLeft = androidx.compose.ui.geometry.Offset(arcLeft, arcTop),
            size = androidx.compose.ui.geometry.Size(arcWidth, arcHeight),
            style = Stroke(width = strokeWidth)
        )
    }
}

@Composable
fun CustomSuccessCheckmark(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val strokeWidth = 5.dp.toPx()
        val w = size.width
        val h = size.height

        drawCircle(
            color = Color(0xFF20B050),
            radius = w * 0.45f,
            style = Stroke(width = strokeWidth)
        )

        // Draw checkmark path
        val path = androidx.compose.ui.graphics.Path().apply {
            moveTo(w * 0.3f, h * 0.5f)
            lineTo(w * 0.45f, h * 0.65f)
            lineTo(w * 0.7f, h * 0.35f)
        }
        drawPath(
            path = path,
            color = Color(0xFF20B050),
            style = Stroke(width = strokeWidth)
        )
    }
}

@Preview(showBackground = true, widthDp = 960, heightDp = 540)
@Composable
fun AccountManagementScreenPreview() {
    val context = LocalContext.current
    val mockRepo = remember { AccountRepository(context) }
    DemoTVComposeTheme {
        AccountManagementScreen(
            onLoginSuccess = {},
            accountRepository = mockRepo
        )
    }
}
