package com.hyunjine.flow_task.presenter.search_movie

import android.os.Bundle
import com.hyunjine.flow_task.R
import com.hyunjine.flow_task.databinding.ActivitySearchMovieBinding
import com.hyunjine.flow_task.presenter.common.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchMovieActivity : BaseActivity<ActivitySearchMovieBinding>(R.layout.activity_search_movie) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
}