package com.hyunjine.flow_task.presenter.search_movie

import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.hyunjine.flow_task.R
import com.hyunjine.flow_task.databinding.ActivitySearchMovieBinding
import com.hyunjine.flow_task.presenter.common.base.BaseActivity
import com.hyunjine.flow_task.presenter.search_movie.adapter.SearchMovieListAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SearchMovieActivity : BaseActivity<ActivitySearchMovieBinding>(R.layout.activity_search_movie) {
    companion object {
        private const val DISPLAY_ITEM_COUNT: Int = 15
    }

    private val viewModel: SearchMovieViewModel by viewModels()
    @Inject lateinit var rvAdapter: SearchMovieListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setRecyclerViewAdapter()
        onEvent()
        getMovies("아이", DISPLAY_ITEM_COUNT)
    }

    private fun setRecyclerViewAdapter() = binding.rvMovieList.apply {
        layoutManager = LinearLayoutManager(this@SearchMovieActivity, LinearLayoutManager.VERTICAL, false)
        adapter = rvAdapter
    }

    private fun onEvent() = binding.run {

    }

    private fun getMovies(query: String, display: Int) {
        viewModel.getMovies(query, display,
            onSuccess = {
                rvAdapter.submitList(it)
            }, onFail = {

            })
    }

    private fun toggleDataStateLayout(isExist: Boolean) = binding.run {

    }
}