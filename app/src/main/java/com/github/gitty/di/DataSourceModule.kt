package com.github.gitty.di

import com.github.gitty.data.datasource.RepositoryDataSource
import com.github.gitty.data.datasource.RepositoryDataSourceImpl
import com.github.gitty.data.service.GithubService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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

}