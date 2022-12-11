package com.github.gitty.di

import android.content.Context
import com.github.gitty.data.datasource.remote.repository.RepositoryDataSource
import com.github.gitty.data.datasource.remote.repository.RepositoryDataSourceImpl
import com.github.gitty.data.datasource.remote.user.UserDataSource
import com.github.gitty.data.datasource.remote.user.UserDataSourceImpl
import com.github.gitty.data.service.GithubService
import com.github.gitty.data.datasource.local.ITokenDataStore
import com.github.gitty.data.datasource.local.TokenDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    @Singleton
    fun provideRepositoryDataSource(@NetworkModule.typeApi service: GithubService): RepositoryDataSource {
        return RepositoryDataSourceImpl(service)
    }

    @Provides
    @Singleton
    fun provideUserDataSource(@NetworkModule.typeApi service: GithubService): UserDataSource {
        return UserDataSourceImpl(service)
    }

    @Provides
    @Singleton
    fun provideITokenDataStore(@ApplicationContext context: Context): ITokenDataStore {
        return TokenDataStore(context)
    }
}