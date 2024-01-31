package com.fzellner.marvelappcompose.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.fzellner.marvelappcompose.designtoken.MarvelAppTheme
import com.fzellner.marvelappcompose.presentation.compose.navigation.MarvelAppComposeNavigation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MarvelAppTheme {
                val navController = rememberNavController()
                MarvelAppComposeNavigation(navController = navController)
            }
        }
    }
}