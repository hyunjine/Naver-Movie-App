package com.hyunjine.flow_task.data.naver.vo

data class MoviesEntity(
    val display: Int,
    val lastBuildDate: String,
    val start: Int,
    val total: Int,
    val items: List<Item>
)