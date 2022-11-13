package com.github.gitty.di

import com.github.gitty.data.datasource.remote.repository.RepositoryDataSource
import com.github.gitty.data.datasource.local.LocalDataSource
import com.github.gitty.data.datasource.remote.user.UserDataSource
import com.github.gitty.data.repository.AccessRepositoryImpl
import com.github.gitty.data.repository.GitRepositoryRepoImpl
import com.github.gitty.data.repository.UserRepositoryImpl
import com.github.gitty.data.service.AccessService
import com.github.gitty.domain.repository.AccessRepository
import com.github.gitty.domain.repository.GitRepositoryRepo
import com.github.gitty.domain.repository.UserRepository
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

    @Singleton
    @Provides
    fun provideAccessRepository (
        @NetworkModule.typeAccess accessService: AccessService,
        localDataSource: LocalDataSource
    ): AccessRepository {
        return AccessRepositoryImpl(
            accessService,
            localDataSource
        )
    }

    @Singleton
    @Provides
    fun provideUserRepository (
        userDataSource: UserDataSource
    ): UserRepository {
        return UserRepositoryImpl (
            userDataSource
        )
    }
}