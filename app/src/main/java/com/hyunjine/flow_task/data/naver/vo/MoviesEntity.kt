package com.hyunjine.flow_task.data.naver.vo

import com.google.gson.annotations.SerializedName

data class MoviesEntity(
    @SerializedName("display")
    val display: Int,
    @SerializedName("lastBuildDate")
    val lastBuildDate: String,
    @SerializedName("start")
    val start: Int,
    @SerializedName("total")
    val total: Int,
    @SerializedName("items")
    val items: List<MovieItemEntity>
)