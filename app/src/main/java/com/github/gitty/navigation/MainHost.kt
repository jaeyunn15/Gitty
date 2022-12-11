package com.github.gitty.navigation

import android.annotation.SuppressLint
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.github.gitty.R
import kotlinx.coroutines.ExperimentalCoroutinesApi

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@ExperimentalCoroutinesApi
@ExperimentalMaterialApi
@Composable
fun MainHost(
    mainNavController: NavController,
    navController: NavHostController,
) {
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        bottomBar = { BottomNavigationScreens(navController) },
        scaffoldState = rememberScaffoldState(snackbarHostState = snackbarHostState)
    ) {
        MainBody(
            mainNavController = mainNavController,
            navController = navController
        )
    }
}

@Composable
fun BottomNavigationScreens(
    navController: NavController
) {
    val screens = remember {
        listOf(
            BottomNavigationScreens.Repos,
            BottomNavigationScreens.Users,
            BottomNavigationScreens.Liked,
        )
    }

    BottomNavigation(
        elevation = 5.dp
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        screens.forEach { screen ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        painter = painterResource(id = screen.icon),
                        contentDescription = null
                    )
                },
                label = { Text(stringResource(screen.resourceId)) },
                selected = currentRoute == screen.route,
                onClick = {
                    navController.popBackStack(navController.graph.startDestinationId, false)

                    if (currentRoute != screen.route)
                    {
                        navController.navigate(screen.route)
                    }
                },
                alwaysShowLabel = false
            )
        }
    }
}

@ExperimentalCoroutinesApi
@ExperimentalMaterialApi
@Composable
fun MainBody(
    mainNavController: NavController,
    navController: NavController
) {
    val mainNav = remember { mainNavController as NavHostController }

    NavHost(
        navController as NavHostController,
        startDestination = BottomNavigationScreens.Repos.route
    ) {
        composable(
            route = BottomNavigationScreens.Repos.route
        ) {
            Text(text = "this is Repository !!", Modifier.clickable {
                mainNav.navigate(Screen.Search.route) {
                    popUpTo(navController.graph.startDestinationId)
                }
            })
        }

        composable(
            route = BottomNavigationScreens.Users.route
        ) {
            Text(text = "this is Users !!")
        }

        composable(
            route = BottomNavigationScreens.Liked.route
        ) {
            Text(text = "this is Liked !!")
        }
    }
}

sealed class BottomNavigationScreens(
    val route: String,
    @StringRes val resourceId: Int,
    @DrawableRes val icon: Int
)
{
    object Repos : BottomNavigationScreens(
        "REPO_ROUTE",
        R.string.repos,
        R.drawable.ic_launcher_foreground
    )

    object Users : BottomNavigationScreens(
        "USER_ROUTE",
        R.string.users,
        R.drawable.ic_launcher_foreground
    )

    object Liked : BottomNavigationScreens(
        "LIKED_ROUTE",
        R.string.liked,
        R.drawable.ic_launcher_foreground
    )
}