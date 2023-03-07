package com.hyunjine.flow_task.data.naver

import com.hyunjine.flow_task.data.naver.vo.MoviesEntity
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface NaverServerService {
    @GET("movie.json")
    suspend fun getSearchMovie(
        @Query("query", encoded = true) query: String,
        @Query("display") display: Int,
        @Query("start") start: Int,
    ): MoviesEntity
}