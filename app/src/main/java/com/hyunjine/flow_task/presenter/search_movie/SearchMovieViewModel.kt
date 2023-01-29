package com.hyunjine.flow_task.presenter.search_movie

import android.content.res.Resources.NotFoundException
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hyunjine.flow_task.common.loggerD
import com.hyunjine.flow_task.common.loggerE
import com.hyunjine.flow_task.presenter.common.base.BaseViewModel
import com.hyunjine.flow_task.presenter.common.base.util.ListLiveData
import com.hyunjine.flow_task.presenter.search_movie.usecase.CheckQueryLengthUseCase
import com.hyunjine.flow_task.presenter.search_movie.usecase.GetMoviesUseCase
import com.hyunjine.flow_task.presenter.search_movie.vo.MovieItemDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchMovieViewModel @Inject constructor(
    private val getMoviesUseCase: GetMoviesUseCase,
    private val checkQueryLengthUseCase: CheckQueryLengthUseCase
): BaseViewModel<SearchMovieViewModel.State>() {
    companion object {
        private const val DISPLAY_ITEM_COUNT: Int = 15
        private const val QUERY_MIN_LENGTH: Int = 1
    }

    private val _movieItems = ListLiveData<MovieItemDTO>()
    val movieItems: LiveData<MutableList<MovieItemDTO>> get() = _movieItems

    private var nextPage: Int = 1
    private var total: Int = 0

    val query = MutableLiveData<String>()

    fun getMovies() {
        clearMovieItemsInfo()
        if (!checkQueryLengthUseCase(query.value, QUERY_MIN_LENGTH)) {
            setState(State.SHORT_QUERY_LENGTH_ERROR)
        } else {
            fetchMovies(1)
        }
    }

    fun getNextPageMovies() {
        fetchMovies(nextPage)
    }

    private fun fetchMovies(start: Int) {
        if (isLastPage()) {
            setState(State.LAST_LOAD_MOVIE_ITEMS)
            return
        }
        setState(State.HIDE_KEYBOARD)
        setState(State.SHOW_LOADING)
        runAsync(methodName, getMoviesUseCase(query.value!!, DISPLAY_ITEM_COUNT, start))
            .subscribe({
                total = it.total
                nextPage = it.start + DISPLAY_ITEM_COUNT
                setState(State.HIDE_LOADING)
                setState(State.LOAD_MOVIE_ITEMS)
                _movieItems.addAll(it.items)
            }, { e ->
                setState(State.HIDE_LOADING)
                when (e) {
                    is NotFoundException -> {
                        setState(State.EMPTY_LOAD_MOVIE_ITEMS)
                    }
                    else -> {
                        setState(State.NETWORK_ERROR)
                    }
                }
                loggerE(methodName, e)
            }).addDispose()
    }

    private fun clearMovieItemsInfo() {
        _movieItems.clear()
        nextPage = 1
        total = 0
    }

    private fun isLastPage(): Boolean = nextPage > 1 && total < nextPage

    enum class State {
        SHOW_LOADING,
        HIDE_LOADING,
        SHORT_QUERY_LENGTH_ERROR,
        NETWORK_ERROR,
        LOAD_MOVIE_ITEMS,
        EMPTY_LOAD_MOVIE_ITEMS,
        LAST_LOAD_MOVIE_ITEMS,
        HIDE_KEYBOARD
    }
}