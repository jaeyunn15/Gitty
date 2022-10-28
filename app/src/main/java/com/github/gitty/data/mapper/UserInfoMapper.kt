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
            imgUrl = model.imgUrl
        )
    }
}