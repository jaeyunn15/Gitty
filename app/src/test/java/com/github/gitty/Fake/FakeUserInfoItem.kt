package com.github.gitty.Fake

import com.github.gitty.domain.entity.UserInfoItem

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