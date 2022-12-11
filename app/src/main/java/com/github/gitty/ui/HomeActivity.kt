package com.github.gitty.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.github.gitty.BuildConfig
import com.github.gitty.ui.viewmodel.MainViewModel
import com.github.gitty.ui.login.LoginScreenEvent
import com.github.gitty.ui.login.LoginViewModel
import com.github.gitty.navigation.AppNavHost
import com.github.gitty.navigation.Screen
import com.github.gitty.ui.theme.GittyTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class HomeActivity : ComponentActivity() {

    private val mainViewModel by viewModels<MainViewModel>()
    private val loginViewModel by viewModels<LoginViewModel>()

    @OptIn(ExperimentalMaterialApi::class, ExperimentalCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen().setKeepOnScreenCondition { !loginViewModel.isReady }

        setContent {
            val mainNavController = rememberNavController()
            val startDestination by loginViewModel.startDestination.collectAsState()

            LaunchedEffect(key1 = Unit) {
                loginViewModel.navEvent.onEach { event ->
                    when (event) {
                        is LoginScreenEvent.StartGitAuth -> login()
                        is LoginScreenEvent.Login -> {
                            loginViewModel.getAccessToken(event.code)
                        }
                        is LoginScreenEvent.StartMain -> {
                            mainNavController.popBackStack()
                            mainNavController.navigate(Screen.MAIN.route) {
                                popUpTo(mainNavController.graph.startDestinationId)
                                launchSingleTop = true
                            }
                        }
                    }
                }.launchIn(this)
            }

            GittyTheme {
                AppNavHost(
                    mainNavController = mainNavController,
                    startDestination = startDestination
                )
            }
        }
    }

    private fun login() {
        val loginUrl = Uri.Builder().scheme("https").authority("github.com")
            .appendPath("login")
            .appendPath("oauth")
            .appendPath("authorize")
            .appendQueryParameter("client_id", BuildConfig.GITHUB_CLIENT_ID)
            .build()
        val browserIntent = Intent(Intent.ACTION_VIEW, loginUrl)
        startActivity(browserIntent)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.data?.getQueryParameter("code")?.let {
            loginViewModel.setEvent(LoginScreenEvent.Login(it))
        }
    }
}
