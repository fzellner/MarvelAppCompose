package com.fzellner.marvelappcompose.presentation.compose.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.fzellner.marvelappcompose.presentation.compose.navigation.route.ComicListRoute
import com.fzellner.marvelappcompose.presentation.compose.screen.ComicListScreen

@Composable
fun ComicListNavGraph(
    navController: NavHostController,
    navigateToComicDetails: (comicId: Int) -> Unit
) {
    NavHost(navController = navController, startDestination =  ComicListRoute.ComicList.route) {
        composable(route = ComicListRoute.ComicList.route) {
            ComicListScreen() {
                navigateToComicDetails.invoke(it)
            }
        }
    }
}