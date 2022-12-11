package com.github.gitty.navigation

import android.annotation.SuppressLint
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.github.gitty.R
import com.github.gitty.ui.repository.RepositoryView
import com.github.gitty.ui.search.SearchViewModel
import com.github.gitty.ui.user.UserInfoScreen
import com.github.gitty.ui.viewmodel.UserViewModel
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
            BottomNavigationScreens.PROFILE
        )
    }

    BottomNavigation(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        screens.forEach { screen ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        painter = painterResource(id = screen.icon),
                        contentDescription = null,
                        modifier = Modifier
                            .width(36.dp)
                            .height(36.dp)
                    )
                },
                label = { Text(stringResource(screen.resourceId), fontSize = 15.sp) },
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
    navController: NavController,
    searchViewModel: SearchViewModel = hiltViewModel(),
    userViewModel: UserViewModel = hiltViewModel()
) {
    val mainNav = remember { mainNavController as NavHostController }

    NavHost(
        navController as NavHostController,
        startDestination = BottomNavigationScreens.Repos.route
    ) {
        composable(
            route = BottomNavigationScreens.Repos.route
        ) {
            Box(modifier = Modifier.fillMaxWidth()) {
                RepositoryView(
                    mainNavController = mainNavController,
                    navController = navController,
                    searchViewModel = searchViewModel
                )
            }
        }

        composable(
            route = BottomNavigationScreens.PROFILE.route
        ) {
            UserInfoScreen(
                mainNavController = mainNav,
                navController = navController,
                userViewModel = userViewModel
            )
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

    object PROFILE : BottomNavigationScreens(
        "PROFILE_ROUTE",
        R.string.users,
        R.drawable.ic_launcher_foreground
    )

}