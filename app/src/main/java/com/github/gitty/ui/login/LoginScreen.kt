package com.github.gitty.ui.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun LoginScreen(
    mainNavController: NavController,
    loginViewModel: LoginViewModel
) {

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(
            text = "Log-In",
            modifier = Modifier.clickable {
                loginViewModel.setEvent(LoginScreenEvent.StartGitAuth)
            }
        )
    }

}