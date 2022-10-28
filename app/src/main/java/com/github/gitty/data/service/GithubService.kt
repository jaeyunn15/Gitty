package com.github.gitty.data.service

import com.github.gitty.data.model.RepositorySearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubService {

    @GET("search/repositories")
    suspend fun getRepositories(
        @Query("q") query: String,
        @Query("sort") sort: String = "stars",
        @Query("order") order: String = "desc",
        @Query("per_page") perSize: Int = 30
    ): RepositorySearchResponse

}