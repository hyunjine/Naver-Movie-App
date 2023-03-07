package com.hyunjine.flow_task.presenter.search_movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hyunjine.flow_task.common.loggerE
import com.hyunjine.flow_task.presenter.common.base.BaseViewModel
import com.hyunjine.flow_task.presenter.common.base.util.ListLiveData
import com.hyunjine.flow_task.presenter.search_movie.usecase.CheckQueryLengthUseCase
import com.hyunjine.flow_task.presenter.search_movie.usecase.GetMoviesUseCase
import com.hyunjine.flow_task.presenter.search_movie.usecase.InsertSearchRecordUseCase
import com.hyunjine.flow_task.presenter.search_movie.vo.MovieItemDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchMovieViewModel @Inject constructor(
    private val getMoviesUseCase: GetMoviesUseCase,
    private val checkQueryLengthUseCase: CheckQueryLengthUseCase,
    private val insertSearchRecordUseCase: InsertSearchRecordUseCase
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

    fun getMovies(_query: String? = null) {
        clearMovieItemsInfo()
        val word = _query ?: query.value
        if (!checkQueryLengthUseCase(word, QUERY_MIN_LENGTH)) {
            setState(State.SHORT_QUERY_LENGTH_ERROR)
        } else {
            query.value = word!!
            fetchMovies(1, word)
            insertSearchRecord(word)
        }
    }

    fun getNextPageMovies() {
        fetchMovies(nextPage, query.value!!)
    }

    private fun fetchMovies(start: Int, word: String) {
        if (isLastPage()) {
            setState(State.LAST_LOAD_MOVIE_ITEMS)
            return
        }
        setState(State.HIDE_KEYBOARD)
        setState(State.SHOW_LOADING)
        viewModelScope.launch(Dispatchers.Main) {
            getMoviesUseCase(word, DISPLAY_ITEM_COUNT, start)
        }
//        runAsync(methodName, getMoviesUseCase(word, DISPLAY_ITEM_COUNT, start))
//            .subscribe({
//                total = it.total
//                nextPage = it.start + DISPLAY_ITEM_COUNT
//                setState(State.HIDE_LOADING)
//                setState(State.LOAD_MOVIE_ITEMS)
//                _movieItems.addAll(it.items)
//            }, { e ->
//                setState(State.HIDE_LOADING)
//                when (e) {
//                    is NotFoundException -> {
//                        setState(State.EMPTY_LOAD_MOVIE_ITEMS)
//                    }
//                    else -> {
//                        setState(State.NETWORK_ERROR)
//                    }
//                }
//                loggerE(methodName, e)
//            }).addDispose()
    }

    private fun clearMovieItemsInfo() {
        _movieItems.clear()
        nextPage = 1
        total = 0
    }

    private fun isLastPage(): Boolean = nextPage > 1 && total < nextPage

    private fun insertSearchRecord(word: String) {
        runAsync(methodName, insertSearchRecordUseCase(word), SINGLE_SCHEDULER)
            .subscribe({
                setState(State.SUCCESS_INSERT)
            }, {
                setState(State.FAIL_INSERT)
                loggerE(methodName, it)
            }).addDispose()
    }
    enum class State {
        SHOW_LOADING,
        HIDE_LOADING,
        SHORT_QUERY_LENGTH_ERROR,
        NETWORK_ERROR,
        LOAD_MOVIE_ITEMS,
        EMPTY_LOAD_MOVIE_ITEMS,
        LAST_LOAD_MOVIE_ITEMS,
        HIDE_KEYBOARD,
        SUCCESS_INSERT,
        FAIL_INSERT
    }
}