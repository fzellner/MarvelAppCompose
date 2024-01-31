package com.fzellner.marvelappcompose.presentation.compose.screen

import android.window.SplashScreen
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.fzellner.marvelappcompose.R
import com.fzellner.marvelappcompose.designtoken.MarvelAppTheme

@Composable
fun SplashScreen() {
    MarvelAppTheme {
        Scaffold {
            Box(
                modifier = Modifier.fillMaxSize().padding(it),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.marvel_comics_old_logo_vector),
                    contentDescription = null
                )
            }
        }

    }

}

@Composable
@Preview(showBackground = true)
fun SplashScreenPreview() {
    SplashScreen()
}