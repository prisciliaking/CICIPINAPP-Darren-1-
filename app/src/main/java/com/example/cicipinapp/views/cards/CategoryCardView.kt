package com.example.cicipinapp.views.cards

import androidx.compose.runtime.Composable
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.remember
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun CategoryCardView() {


                val languages = listOf("Italian", "Indonesian", "Japanese", "Korean")
                var selectedLanguage by remember { mutableStateOf("Italian") } // Default selected language

                // Horizontal scrollable row
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()) // Allows horizontal scrolling
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp) // Spacing between items
                ) {
                    languages.forEach { language ->
                        // Each card
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp)) // Rounded corners
                                .background(
                                    if (language == selectedLanguage) Color(0xFFFFA000) // Yellow when selected
                                    else Color(0xFFF5F5F5) // Grey when not selected
                                )
                                .clickable { selectedLanguage = language } // Set selected language on click
                                .padding(horizontal = 16.dp, vertical = 8.dp) // Padding inside the box
                        ) {
                            Text(
                                text = language,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium,
                                color = if (language == selectedLanguage) Color.White else Color.Gray
                            )
                        }
                    }
                }

}

@Preview(showBackground = true)
@Composable
private fun CategoryCardPreview(){
    CategoryCardView()
}
