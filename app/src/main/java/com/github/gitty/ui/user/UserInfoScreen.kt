package com.github.gitty.ui.user

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.ImagePainter.State.Empty.painter
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import coil.transform.GrayscaleTransformation
import com.github.gitty.R
import com.github.gitty.domain.entity.RepositoryItem
import com.github.gitty.domain.entity.UserInfoItem
import com.github.gitty.navigation.Screen
import com.github.gitty.ui.search.ItemListItem
import com.github.gitty.ui.viewmodel.UserViewModel

@Composable
fun UserInfoScreen(
    mainNavController: NavHostController,
    navController: NavController,
    userViewModel: UserViewModel
) {
    val userInfoState by userViewModel.userInfoState.collectAsState()
    val userInfo = userInfoState.userInfo
    val userRepositories = userInfoState.userRepositories

    LaunchedEffect(key1 = Unit){
        if (userInfo == null) {
            userViewModel.getUserInfo()
        }
    }

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 10.dp)
        .padding(10.dp)
    ) {
        if (userInfo != null) {
            TopUserInfoAppBar(navController = mainNavController, modifier = Modifier)
            UserProfileScreen(userInfo, modifier = Modifier)
            UserCommentScreen(userInfo, modifier = Modifier)
            UserBioFileScreen(userInfo, modifier = Modifier)
            Spacer(modifier = Modifier
                .height(20.dp)
                .background(color = Color.White))
        }
        if (userRepositories != null) {
            UserRepositoriesScreen(userRepositories, modifier = Modifier)
        }
    }
}

@Composable
fun UserRepositoriesScreen(
    userRepositories: List<RepositoryItem>,
    modifier: Modifier
) {
    Text(text = "Repositories", fontSize = 24.sp, modifier = modifier.fillMaxWidth())
    LazyColumn(modifier = modifier.fillMaxWidth()) {
        items(userRepositories) { item ->
            ItemListItem(
                item = item,
                onItemClick = {}
            )
        }
    }
}

@Composable
fun TopUserInfoAppBar (
    navController: NavHostController,
    modifier: Modifier
) {
    Row(
        modifier = modifier
            .height(48.dp)
            .fillMaxWidth()
            .padding(horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Profile", fontSize = 28.sp)
        Image(
            painter = painterResource(id = R.drawable.ic_baseline_search_24),
            contentDescription = null,
            modifier = modifier
                .width(32.dp)
                .height(32.dp)
                .weight(1f)
                .clickable {
                    navController.navigate(Screen.Search.route)
                },
            alignment = Alignment.CenterEnd,
        )
    }
    Spacer(modifier = modifier.height(10.dp))
}

@Composable
fun UserProfileScreen(
    userInfo: UserInfoItem,
    modifier: Modifier
) {
    val painter = rememberImagePainter(
        data = userInfo.imgUrl,
        builder = {
            transformations(CircleCropTransformation())
        })

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painter,
            contentDescription = null,
            modifier = modifier
                .width(75.dp)
                .height(75.dp)
        )
        Spacer(modifier = modifier.width(10.dp))
        Column (
            modifier = modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = userInfo.name ?: "",
                fontSize = 24.sp,
                modifier = modifier.align(Alignment.Start)
            )
            Text(
                text = userInfo.userId ?: "",
                fontSize = 18.sp,
                modifier = modifier.align(Alignment.Start)
            )
        }
    }
}

@Composable
fun UserCommentScreen(
    userInfo: UserInfoItem,
    modifier: Modifier
) {
    Column (
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 10.dp)
            .background(
                color = MaterialTheme.colors.background,
                shape = RoundedCornerShape(6.dp)
            )
    ) {
        Text(
            text = userInfo.bio ?: "",
            fontSize = 18.sp,
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
        )
    }
}

@Composable
fun UserBioFileScreen(
    userInfo: UserInfoItem,
    modifier: Modifier
) {
   Column(modifier = modifier
       .fillMaxWidth()
       .padding(horizontal = 10.dp)) {
       if (!userInfo.localization.isNullOrEmpty()) {
           addBioItem(modifier, R.drawable.ic_baseline_location_on_24, userInfo.localization)
       }
       if (!userInfo.company.isNullOrEmpty()) {
           addBioItem(modifier, R.drawable.ic_baseline_company_24, userInfo.company)
       }
       if (!userInfo.email.isNullOrEmpty()) {
           addBioItem(modifier, R.drawable.ic_baseline_email_24, userInfo.email)
       }
       if (userInfo.followers != null && userInfo.following != null) {
           addFollowerItem(modifier, R.drawable.ic_baseline_person_outline_24, userInfo.followers.toString(), userInfo.following.toString())
       }
   }
}

@Composable
fun addBioItem (
    modifier: Modifier,
    @DrawableRes drawableRes: Int,
    text: String
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(painter = painterResource(id = drawableRes), contentDescription = null)
        Spacer(modifier = modifier.width(5.dp))
        Text(text = text, fontSize = 16.sp)
    }
    Spacer(modifier = modifier.height(5.dp))
}

@Composable
fun addFollowerItem (
    modifier: Modifier,
    @DrawableRes drawableRes: Int,
    follower: String,
    following: String
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(painter = painterResource(id = drawableRes), contentDescription = null)
        Spacer(modifier = modifier.width(5.dp))
        Text(text = "$follower followers", fontSize = 16.sp)
        Spacer(modifier = modifier.width(5.dp))
        Text(text = " $following following", fontSize = 16.sp)
    }
    Spacer(modifier = modifier.height(5.dp))
}


@Composable
@Preview
fun testImg() {
    fun getFakeUserInfoItem(): UserInfoItem {
        return UserInfoItem(
            id = 1,
            userId = "fakeUser",
            url = "https://api.github.com/users/octocat",
            imgUrl = null,
            company = "GitHub",
            blog = "https://github.com/blog",
            localization = "San Francisco",
            following = 20,
            followers = 5,
            name = "jeremy",
            email = "wodbs135@gmial.com",
            bio = "There once was..."
        )
    }

    UserProfileScreen(userInfo = getFakeUserInfoItem(), modifier = Modifier)
}