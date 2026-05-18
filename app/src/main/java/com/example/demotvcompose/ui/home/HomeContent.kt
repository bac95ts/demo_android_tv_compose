package com.example.demotvcompose.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.lazy.LazyRow
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
fun HomeContent(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .background(Color(0xFF141414)) // Dark background
            .padding(32.dp)
    ) {
        // Top Bar
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Text(text = "10:11\nT2 - 18/05", color = Color.White)
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // Carousel Placeholder
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Left partially visible item
            Surface(
                onClick = {},
                modifier = Modifier
                    .weight(1f)
                    .height(200.dp),
                shape = ClickableSurfaceDefaults.shape(shape = RoundedCornerShape(16.dp))
            ) {
                Box(modifier = Modifier.fillMaxSize().background(Color.DarkGray))
            }
            
            // Center Feature
            Surface(
                onClick = {},
                modifier = Modifier
                    .weight(3f)
                    .height(300.dp),
                shape = ClickableSurfaceDefaults.shape(shape = RoundedCornerShape(16.dp))
            ) {
                Box(modifier = Modifier.fillMaxSize().background(Color.Gray), contentAlignment = Alignment.BottomStart) {
                    Text("Featured Content", color = Color.White, modifier = Modifier.padding(16.dp))
                }
            }
            
            // Right partially visible item
            Surface(
                onClick = {},
                modifier = Modifier
                    .weight(1f)
                    .height(200.dp),
                shape = ClickableSurfaceDefaults.shape(shape = RoundedCornerShape(16.dp))
            ) {
                Box(modifier = Modifier.fillMaxSize().background(Color.DarkGray))
            }
        }
        
        Spacer(modifier = Modifier.height(48.dp))
        
        // Channel List
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(10) { index ->
                Surface(
                    onClick = {},
                    modifier = Modifier
                        .width(120.dp)
                        .height(60.dp),
                    shape = ClickableSurfaceDefaults.shape(shape = RoundedCornerShape(8.dp))
                ) {
                    Box(modifier = Modifier.fillMaxSize().background(Color.DarkGray), contentAlignment = Alignment.Center) {
                        Text("VTV ${index + 1}", color = Color.White)
                    }
                }
            }
        }
    }
}
