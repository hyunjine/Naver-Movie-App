package com.hyunjine.flow_task.presenter.search_movie.vo

import com.google.gson.annotations.SerializedName

data class MovieItemDTO(
    @SerializedName("image")
    val image: String,
    @SerializedName("link")
    val link: String,
    @SerializedName("pubDate")
    val pubDate: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("userRating")
    val userRating: String
)