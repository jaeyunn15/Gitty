package com.github.gitty.navigation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.github.gitty.ui.login.LoginScreen
import com.github.gitty.ui.login.LoginViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalMaterialApi::class, ExperimentalCoroutinesApi::class)
@Composable
fun AppNavHost(
    mainNavController: NavController,
    startDestination: String,
    loginViewModel: LoginViewModel = hiltViewModel()
) {

    val navBarNavController = rememberNavController()

    NavHost(
        navController = mainNavController as NavHostController,
        startDestination = startDestination
    ) {

        composable(route = Screen.LOGIN.route) {
            LoginScreen(mainNavController = mainNavController, loginViewModel = loginViewModel)
        }

        composable(route = Screen.MAIN.route) {
            MainHost(
                mainNavController = mainNavController,
                navBarNavController
            )
        }

        composable(route = Screen.Search.route) {
            Text(text = "this is search", color = MaterialTheme.colors.primary)
        }

    }

}

sealed class Screen(val route: String) {
    object MAIN: Screen("MAIN_SCREEN")
    object LOGIN: Screen("LOGIN_SCREEN")
    object Search: Screen("SEARCH_SCREEN")
}