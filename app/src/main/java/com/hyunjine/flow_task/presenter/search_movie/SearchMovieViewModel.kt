package com.hyunjine.flow_task.presenter.search_movie

import com.hyunjine.flow_task.common.loggerD
import com.hyunjine.flow_task.common.loggerE
import com.hyunjine.flow_task.presenter.common.base.BaseViewModel
import com.hyunjine.flow_task.presenter.search_movie.usecase.GetMoviesUseCase
import com.hyunjine.flow_task.presenter.search_movie.vo.MovieItemDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchMovieViewModel @Inject constructor(
    private val getMoviesUseCase: GetMoviesUseCase
): BaseViewModel() {
    fun getMovies(
        query: String,
        display: Int,
        onSuccess: (List<MovieItemDTO>) -> Unit,
        onFail: () -> Unit
    ) {
        runAsync(methodName, getMoviesUseCase(query, display))
            .subscribe({
                onSuccess(it.items)
                loggerD(methodName, it)
            }, {
                onFail()
                loggerE(methodName, it)
            }).addDispose()
    }
}