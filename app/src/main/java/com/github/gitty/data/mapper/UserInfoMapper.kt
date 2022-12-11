package com.github.gitty.data.mapper

import com.github.gitty.data.model.EntityMapper
import com.github.gitty.data.model.UserInfoResponse
import com.github.gitty.domain.entity.UserInfoItem

object UserInfoMapper: EntityMapper<UserInfoResponse, UserInfoItem> {
    override fun mapFromRemote(model: UserInfoResponse): UserInfoItem {
        return UserInfoItem(
            id = model.id,
            userId = model.userId,
            url = model.url,
            imgUrl = model.imgUrl,
            company = model.company,
            blog = model.blog,
            localization = model.localization,
            followers = model.followers,
            following = model.following,
            name = model.name,
            email = model.email,
            bio = model.bio
        )
    }
}