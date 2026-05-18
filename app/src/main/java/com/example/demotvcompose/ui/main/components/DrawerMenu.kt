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
            .background(if (isClosed) Color.Transparent else Color(0xCC000000))
            .padding(vertical = 32.dp, horizontal = if (isClosed) 0.dp else 16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        menuItems.forEach { title ->
            val isSelected = title == selectedMenu
            
            Surface(
                onClick = { onMenuSelected(title) },
                modifier = Modifier
                    .padding(vertical = 4.dp)
                    .height(48.dp)
                    .then(
                        if (isClosed) {
                            Modifier.width(1.dp) // Chiều rộng 1dp để tàng hình nhưng vẫn bắt được focus
                        } else {
                            Modifier.width(200.dp)
                        }
                    ),
                colors = ClickableSurfaceDefaults.colors(
                    containerColor = if (isSelected && !isClosed) Color.White else Color.Transparent,
                    focusedContainerColor = if (isClosed) Color.Transparent else Color.White.copy(alpha = 0.8f)
                ),
                shape = ClickableSurfaceDefaults.shape(shape = RoundedCornerShape(8.dp))
            ) {
                if (!isClosed) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 12.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .background(if (isSelected) Color.Black else Color.Gray)
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(
                            text = title,
                            color = if (isSelected) Color.Black else Color.White
                        )
                    }
                }
            }
        }
    }
}
