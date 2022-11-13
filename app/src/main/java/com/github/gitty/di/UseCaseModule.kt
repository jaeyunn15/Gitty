package com.github.gitty.di

import com.github.gitty.domain.user.UserController
import com.github.gitty.domain.user.UserControllerImpl
import com.github.gitty.domain.repository.AccessRepository
import com.github.gitty.domain.repository.GitRepositoryRepo
import com.github.gitty.domain.repository.UserRepository
import com.github.gitty.domain.usecase.GetRepositoriesUseCase
import com.github.gitty.domain.usecase.GetUserInfoUseCase
import com.github.gitty.domain.usecase.RequestAccessTokenUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Provides
    @Singleton
    fun provideGetRepositoriesUseCase(
        repo: GitRepositoryRepo,
        @IoDispatcher dispatcher: CoroutineDispatcher
    ) = GetRepositoriesUseCase(repo, dispatcher)

    @Provides
    @Singleton
    fun provideGetUserInfoUseCase(
        repo: UserRepository,
        @IoDispatcher dispatcher: CoroutineDispatcher
    ) = GetUserInfoUseCase(repo, dispatcher)

    @Provides
    @Singleton
    fun provideRequestAccessTokenUseCase(
        accessRepository: AccessRepository
    ) = RequestAccessTokenUseCase(accessRepository = accessRepository)

    @Provides
    @Singleton
    fun provideUserController(): UserController = UserControllerImpl()
}