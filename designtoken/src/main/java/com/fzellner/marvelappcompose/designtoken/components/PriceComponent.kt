package com.fzellner.marvelappcompose.designtoken.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fzellner.marvelappcompose.designtoken.colors.MarvelAppGradients

@Composable
fun GradientPriceIndicatorComponent(
    modifier:Modifier = Modifier,
    price: String,
    colors: List<Color> = MarvelAppGradients.gradientRed,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(80.dp)
            .background(
                brush = Brush.radialGradient(colors = colors),
                shape = CircleShape
            )
    ) {
        Text(
            text = price,
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.ExtraBold)
    }
}