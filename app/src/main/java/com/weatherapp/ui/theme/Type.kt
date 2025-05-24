package com.weatherapp.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.weatherapp.R

val ubuntu = FontFamily(
    listOf(
        Font(R.font.ubuntu_regular, FontWeight.Normal),
        Font(R.font.ubuntu_medium, FontWeight.Medium),
        Font(R.font.ubuntu_bold, FontWeight.Bold)
    )
)

// Set of Material typography styles to start with
val Typography = Typography(
    bodySmall = TextStyle(
        fontFamily = ubuntu,
        fontWeight = FontWeight.Medium,
        color = White,
        fontSize = 12.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = ubuntu,
        fontWeight = FontWeight.Normal,
        color = White,
        fontSize = 14.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = ubuntu,
        fontWeight = FontWeight.Normal,
        color = White,
        fontSize = 16.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = ubuntu,
        fontWeight = FontWeight.Bold,
        color = White,
        fontSize = 18.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = ubuntu,
        fontWeight = FontWeight.Bold,
        color = White,
        fontSize = 22.sp
    ),
    headlineLarge = TextStyle(
        fontFamily = ubuntu,
        fontWeight = FontWeight.Bold,
        color = White,
        fontSize = 26.sp
    )
)
