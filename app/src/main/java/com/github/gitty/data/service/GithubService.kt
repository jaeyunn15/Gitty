package com.github.gitty.data.service

import com.github.gitty.data.model.RepositoryResponse
import com.github.gitty.data.model.RepositorySearchResponse
import com.github.gitty.data.model.UserInfoResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubService {

    @GET("search/repositories")
    suspend fun getRepositories(
        @Query("q") query: String,
        @Query("sort") sort: String = "stars",
        @Query("order") order: String = "desc",
        @Query("per_page") perSize: Int = 30
    ): RepositorySearchResponse

    @GET("/user")
    suspend fun getUserInfo(): UserInfoResponse

    @GET("/users/{userId}/repos")
    suspend fun getUserRepositories(
        @Path("userId") userId: String
    ): List<RepositoryResponse>
}