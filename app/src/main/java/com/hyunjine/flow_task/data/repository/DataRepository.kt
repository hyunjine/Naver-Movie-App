package com.hyunjine.flow_task.data.repository

import com.hyunjine.flow_task.data.naver.vo.MoviesEntity
import io.reactivex.Single

interface DataRepository {
    fun getMovies(query: String, display: Int): Single<MoviesEntity>
//    fun getMoviesFromDataBase(): Single<MoviesEntity>
}