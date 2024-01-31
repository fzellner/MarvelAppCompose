package com.fzellner.marvelappcompose.designtoken.typography

import androidx.compose.ui.unit.sp
import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.fzellner.marvelappcompose.designtoken.R


private val gilroyFontFamily = FontFamily(
    Font(R.font.gilroy_light),
)


val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = gilroyFontFamily,
        fontWeight = FontWeight.Light,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
)