package com.github.gitty.di

import com.github.gitty.data.datasource.RepositoryDataSource
import com.github.gitty.data.repository.GitRepositoryRepoImpl
import com.github.gitty.domain.repository.GitRepositoryRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideGitRepositoryRepo (
        repositoryDataSource: RepositoryDataSource
    ): GitRepositoryRepo {
        return GitRepositoryRepoImpl(
            repositoryDataSource = repositoryDataSource
        )
    }

}