package com.hyunjine.flow_task.presenter.search_movie

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hyunjine.flow_task.R
import com.hyunjine.flow_task.common.loggerD
import com.hyunjine.flow_task.databinding.ActivitySearchMovieBinding
import com.hyunjine.flow_task.presenter.common.base.BaseActivity
import com.hyunjine.flow_task.presenter.search_movie.adapter.SearchMovieListAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SearchMovieActivity : BaseActivity<ActivitySearchMovieBinding>(R.layout.activity_search_movie) {
    private val viewModel: SearchMovieViewModel by viewModels()
    @Inject lateinit var rvAdapter: SearchMovieListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel
        setRecyclerViewAdapter()
        onEvent()
        onUiStateEvent()
    }

    private fun setRecyclerViewAdapter() = binding.rvMovieList.apply {
        layoutManager = LinearLayoutManager(this@SearchMovieActivity, LinearLayoutManager.VERTICAL, false)
        adapter = rvAdapter
        itemAnimator = null
    }

    private fun onEvent() = binding.run {
        onRecyclerViewLastIndex()
        btnSearch.setOnClickListener {
            viewModel.getMovies()
        }
    }

    private fun onUiStateEvent() = viewModel.run {
        movieItems.observe(this@SearchMovieActivity) { movieItems ->
            rvAdapter.submitList(movieItems.toList())
        }
        state.observe(this@SearchMovieActivity) { eventWrapper ->
            val event = eventWrapper.getContentIfNotHandled() ?: return@observe
            when (event) {
                SearchMovieViewModel.State.SHORT_QUERY_LENGTH_ERROR -> {
                    showToast(R.string.search_movie_min_query_length_error)
                }
                SearchMovieViewModel.State.NETWORK_ERROR -> {
                    showToast(R.string.network_error)
                }
                SearchMovieViewModel.State.SHOW_LOADING -> {
                    setProgressBar(true)
                }
                SearchMovieViewModel.State.HIDE_LOADING -> {
                    setProgressBar(false)
                }
                SearchMovieViewModel.State.LOAD_MOVIE_ITEMS -> {
                    toggleDataStateLayout(true)
                }
                SearchMovieViewModel.State.LAST_LOAD_MOVIE_ITEMS -> {
                    showToast(R.string.search_movie_last_page)
                }
                SearchMovieViewModel.State.EMPTY_LOAD_MOVIE_ITEMS -> {
                    toggleDataStateLayout(false)
                }
                SearchMovieViewModel.State.HIDE_KEYBOARD -> {
                    hideKeyboard()
                }
            }
        }
    }

    private fun onRecyclerViewLastIndex() = binding.rvMovieList.apply {
        addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val lastVisiblePosition = (recyclerView.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
                val totalCount = rvAdapter.itemCount - 1
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisiblePosition == totalCount) {
                    viewModel.getNextPageMovies()
                }
            }
        })
    }

    private fun toggleDataStateLayout(isExist: Boolean) = binding.run {
        if (isExist) {
            clExistData.visibility = View.VISIBLE
            clEmptyData.visibility = View.GONE
        } else {
            clExistData.visibility = View.GONE
            clEmptyData.visibility = View.VISIBLE
        }
    }

    private fun setProgressBar(isShow: Boolean) {
        binding.pbLoading.visibility = if (isShow) View.VISIBLE else View.GONE
    }

    private fun hideKeyboard() {
        WindowInsetsControllerCompat(
            window,
            window.decorView
        ).hide(WindowInsetsCompat.Type.ime())
    }
}