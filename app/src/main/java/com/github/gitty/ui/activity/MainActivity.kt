package com.github.gitty.ui.activity

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.github.gitty.BuildConfig
import com.github.gitty.R
import com.github.gitty.databinding.ActivityMainBinding
import com.github.gitty.ui.base.BaseActivity
import com.github.gitty.ui.search.ItemList
import com.github.gitty.ui.search.SearchView
import com.github.gitty.ui.search.SearchViewModel
import com.github.gitty.ui.viewmodel.MainViewModel
import com.github.gitty.ui.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    override val viewModel: SearchViewModel by viewModels()

    private val mainViewModel by viewModels<MainViewModel>()
    private val userViewModel by viewModels<UserViewModel>()

    @OptIn(ExperimentalLifecycleComposeApi::class)
    override fun initLayout() {
        setContent {
            val isLoginUser by mainViewModel.userController.userLoginState.collectAsStateWithLifecycle()
            MainScreen(
                viewModel = viewModel,
                mainViewModel = mainViewModel,
                userViewModel = userViewModel
            ) {
                if (isLoginUser) {
                    logout()
                } else {
                    login()
                }
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

    private fun logout() {
        mainViewModel.logOut()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.data?.getQueryParameter("code")?.let {
            mainViewModel.getAccessToken(it)
        }
    }
}

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun MainScreen(
    viewModel: SearchViewModel,
    mainViewModel: MainViewModel,
    userViewModel: UserViewModel,
    onLogInOutClick: () -> Unit
) {
    val isLoginUser by mainViewModel.userController.userLoginState.collectAsStateWithLifecycle()
    Column {
        TopAppBar(isLoginUser, onLogInOutClick)
        SearchView(viewModel)
        ItemList(viewModel = viewModel)
    }
}

@Composable
fun TopAppBar(
    isLogin: Boolean,
    onLogInOutClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .statusBarsPadding()
            .padding(start = 4.dp)
    ) {
        Text(
            text = "Github",
            modifier = Modifier
                .align(Alignment.Center),
            fontStyle = FontStyle.Normal,
            textAlign = TextAlign.Center,
            fontSize = 20.sp
        )
        Button(
            onClick = {
                onLogInOutClick()
            },
            modifier = Modifier
                .align(alignment = Alignment.CenterEnd)
                .width(85.dp)
                .padding(end = 10.dp)
        ) {
            Text(
                text = if (isLogin) "LogOut" else "LogIn",
                fontSize = 10.sp
            )
        }
    }
}

@Preview
@Composable
fun previewTopAppBar() {
    TopAppBar(false) {

    }
}