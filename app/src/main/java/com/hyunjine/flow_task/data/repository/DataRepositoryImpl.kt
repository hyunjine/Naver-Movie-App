package com.hyunjine.flow_task.data.repository

import com.hyunjine.flow_task.data.naver.NaverServerService
import com.hyunjine.flow_task.data.naver.vo.MoviesEntity
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import io.reactivex.Single
import javax.inject.Inject

class DataRepositoryImpl @Inject constructor(
    private val naverServerService: NaverServerService
) : DataRepository {

    override fun getMovies(query: String, display: Int): Single<MoviesEntity> =
        naverServerService.getSearchMovie(query, display)

//    override fun getMoviesFromDataBase(): Single<MoviesEntity> {
//
//    }

    @Module
    @InstallIn(ViewModelComponent::class)
    abstract class DataRepositoryModule() {
        @Binds
        abstract fun bindDataRepository(impl: DataRepositoryImpl): DataRepository
    }
}