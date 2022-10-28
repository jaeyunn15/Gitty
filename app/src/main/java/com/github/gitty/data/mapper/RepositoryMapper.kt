package com.github.gitty.data.mapper

import com.github.gitty.data.model.EntityMapper
import com.github.gitty.data.model.RepositoryResponse
import com.github.gitty.domain.entity.RepositoryItem

object RepositoryMapper : EntityMapper<RepositoryResponse, RepositoryItem> {
    override fun mapFromRemote(model: RepositoryResponse) = RepositoryItem (
        id = model.id,
        title = model.title,
        fullTitle = model.full_title,
        description = model.description,
        language = model.language,
        user = model.user?.let { UserInfoMapper.mapFromRemote(it) },
        score = model.score,
        latestUpdateTime = model.latestUpdateTime,
        gitUrl = model.git_url
    )
}