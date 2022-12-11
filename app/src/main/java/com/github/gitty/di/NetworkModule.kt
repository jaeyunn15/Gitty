package com.github.gitty.di

import com.github.gitty.BuildConfig
import com.github.gitty.data.service.AccessService
import com.github.gitty.data.service.GithubService
import com.github.gitty.data.datasource.local.ITokenDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    val BASE_URL = "https://api.github.com/"
    val GIT_BASE_URL = "https://github.com/"

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class typeAccess

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class typeApi

    @Singleton
    @Provides
    @typeApi
    fun provideHttpClient(
        iTokenDataStore: ITokenDataStore
    ): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG){
            interceptor.level = HttpLoggingInterceptor.Level.BODY
        }else{
            interceptor.level = HttpLoggingInterceptor.Level.NONE
        }

        val userToken = iTokenDataStore.getToken()

        return OkHttpClient
            .Builder()
            .addInterceptor(OAuthInterceptor("Bearer", userToken))
            .addInterceptor(interceptor)
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    @typeAccess
    fun provideAccessHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG){
            interceptor.level = HttpLoggingInterceptor.Level.BASIC
        }else{
            interceptor.level = HttpLoggingInterceptor.Level.NONE
        }

        return OkHttpClient
            .Builder()
            .addInterceptor(interceptor)
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Singleton
    @Provides
    @typeApi
    fun provideApiRetrofit(
        @typeApi okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Singleton
    @Provides
    @typeAccess
    fun provideAccessRetrofit(@typeAccess okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(GIT_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    @typeApi
    fun provideGithubService(@typeApi retrofit: Retrofit): GithubService = retrofit.create(GithubService::class.java)

    @Singleton
    @Provides
    @typeAccess
    fun provideAccessService(@typeAccess retrofit: Retrofit): AccessService = retrofit.create(AccessService::class.java)
}