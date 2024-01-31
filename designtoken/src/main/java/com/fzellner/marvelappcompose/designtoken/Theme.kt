package com.fzellner.marvelappcompose.designtoken

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.fzellner.marvelappcompose.designtoken.colors.MarvelAppColor
import com.fzellner.marvelappcompose.designtoken.typography.Typography

private val DarkColorScheme = darkColorScheme(
    background = MarvelAppColor.primaryDark,
    primary = MarvelAppColor.primaryRed,
    secondary = MarvelAppColor.primaryBlack,
    tertiary = MarvelAppColor.primarySilver
)
@Composable
fun MarvelAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = DarkColorScheme

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}