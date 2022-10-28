package com.github.gitty.di

import com.github.gitty.domain.repository.GitRepositoryRepo
import com.github.gitty.domain.usecase.GetRepositoriesUseCase
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
}