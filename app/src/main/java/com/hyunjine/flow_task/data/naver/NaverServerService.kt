package com.hyunjine.flow_task.data.naver

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface NaverServerService {
    @GET("gstations")
    fun getSearchMovie(
        @Query("meters") meters: Int,
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double
    ): Single<GasStations>
}