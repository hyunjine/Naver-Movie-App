package com.hyunjine.flow_task.presenter.search_movie.vo

import com.google.gson.annotations.SerializedName

data class MoviesDTO(
    @SerializedName("display")
    val display: Int,
    @SerializedName("start")
    val start: Int,
    @SerializedName("total")
    val total: Int,
    @SerializedName("items")
    val items: List<MovieItemDTO>
)