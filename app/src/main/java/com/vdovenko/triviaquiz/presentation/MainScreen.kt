package com.vdovenko.triviaquiz.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.vdovenko.triviaquiz.presentation.game.GameScreen
import com.vdovenko.triviaquiz.presentation.start.SelectCategoryScreen
import com.vdovenko.triviaquiz.presentation.start.StartScreen
import kotlinx.serialization.Serializable

@Composable
fun MainScreen(
    modifier: Modifier = Modifier
) {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Start) {

        composable<Screen.Start> {
            StartScreen(
                onClickStart = { navController.navigate(Screen.SelectCategory) }
            )
        }
        composable<Screen.SelectCategory> {
            SelectCategoryScreen(
                onClickCategory = { navController.navigate(Screen.Game(it)) }
            )
        }
        composable<Screen.Game> { backStackEntry ->
            val route = backStackEntry.toRoute<Screen.Game>()
            GameScreen(
                selectCategoryId = route.selectedCategoryId,
                onTryAgainClick = { navController.popBackStack(route = Screen.Start, inclusive = false) }
            )
        }
    }
}

@Serializable
sealed interface Screen {

    @Serializable
    data object Start : Screen

    @Serializable
    data object SelectCategory : Screen

    @Serializable
    data class Game(val selectedCategoryId: Int?) : Screen
}