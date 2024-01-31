package com.fzellner.marvelappcompose.presentation.compose.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.fzellner.marvelappcompose.comicdetails.presentation.compose.route.ComicDetailsRoute
import com.fzellner.marvelappcompose.comicdetails.presentation.compose.screen.ComicDetailScreen
import com.fzellner.marvelappcompose.presentation.compose.navigation.route.ComicListRoute
import com.fzellner.marvelappcompose.presentation.compose.screen.ComicListScreen
import com.fzellner.marvelappcompose.presentation.compose.screen.SplashScreen
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun MarvelAppComposeNavigation(
    navController: NavHostController,
) {
    val coroutineScope = rememberCoroutineScope()
    NavHost(navController = navController, startDestination = "splashScreen") {
        composable(route = "splashScreen") {
            SplashScreen()

            LaunchedEffect(key1 = Unit, block = {
                coroutineScope.launch {
                    delay(3000)
                    navController.navigate(ComicListRoute.ComicList.route)
                }
            })


        }

        composable(route = ComicListRoute.ComicList.route) {
            ComicListScreen() {
                navController.navigate("comic_details/$it")
            }
        }

        composable(
            route = ComicDetailsRoute.ComicDetails.route,
            arguments = listOf(navArgument("comicId") { type = NavType.IntType })
        ) {
            ComicDetailScreen()
        }
    }
}