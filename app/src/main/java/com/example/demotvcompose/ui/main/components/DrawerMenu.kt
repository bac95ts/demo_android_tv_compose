package com.example.demotvcompose.ui.main.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.tv.material3.ClickableSurfaceDefaults
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.Surface
import androidx.tv.material3.Text

import androidx.tv.material3.MaterialTheme
import com.example.demotvcompose.theme.customColors

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun DrawerMenu(
    isClosed: Boolean,
    selectedMenu: String,
    onMenuSelected: (String) -> Unit
) {
    val menuItems = listOf("Quản lý tài khoản", "Evoucher", "Tìm kiếm", "Trang chủ", "Mua gói", "Truyền hình", "Cá nhân")

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .background(if (isClosed) Color.Transparent else MaterialTheme.colorScheme.background.copy(alpha = 0.9f))
            .padding(vertical = 32.dp, horizontal = if (isClosed) 0.dp else 16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        menuItems.forEach { title ->
            val isSelected = title == selectedMenu
            
            Surface(
                onClick = { onMenuSelected(title) },
                modifier = Modifier
                    .padding(vertical = 4.dp)
                    .height(56.dp)
                    .then(
                        if (isClosed) {
                            Modifier.width(1.dp) // Chiều rộng 1dp để tàng hình nhưng vẫn bắt được focus
                        } else {
                            Modifier.width(220.dp)
                        }
                    ),
                colors = ClickableSurfaceDefaults.colors(
                    containerColor = if (isSelected && !isClosed) MaterialTheme.colorScheme.primary else Color.Transparent,
                    focusedContainerColor = if (isClosed) Color.Transparent else MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                ),
                shape = ClickableSurfaceDefaults.shape(shape = RoundedCornerShape(12.dp))
            ) {
                if (!isClosed) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .background(if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f))
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(
                            text = title,
                            color = when {
                                isSelected -> MaterialTheme.colorScheme.onPrimary
                                title == "Mua gói" -> MaterialTheme.customColors.vip
                                else -> MaterialTheme.colorScheme.onBackground
                            },
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        }
    }
}
