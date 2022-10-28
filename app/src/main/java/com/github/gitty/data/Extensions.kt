package com.github.gitty.data

import com.github.gitty.data.model.ApiResult
import retrofit2.HttpException
import retrofit2.Response

suspend fun <T> getResponse(apiCall: suspend () -> Response<T>): ApiResult<T> {
    try {
        val response = apiCall()

        if (response.isSuccessful) {
            val body = response.body()
            body?.let {
                return ApiResult.success(body)
            }
        }

        return ApiResult.error(response.errorBody().toString())
    } catch (e: HttpException) {
        return ApiResult.error("httpException")
    } catch (e: Exception) {
        return ApiResult.error("commonException")
    }
}