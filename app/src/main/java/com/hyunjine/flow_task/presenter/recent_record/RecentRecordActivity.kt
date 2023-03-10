package com.hyunjine.flow_task.presenter.recent_record

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.hyunjine.flow_task.R
import com.hyunjine.flow_task.databinding.ActivityRecentRecordBinding
import com.hyunjine.flow_task.presenter.common.base.BaseActivity
import com.hyunjine.flow_task.presenter.recent_record.adapter.RecentRecordListAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class RecentRecordActivity : BaseActivity<ActivityRecentRecordBinding>(R.layout.activity_recent_record) {
    private val viewModel: RecentRecordViewModel by viewModels()
    @Inject lateinit var rvAdapter: RecentRecordListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setRecyclerViewAdapter()
        viewModel.getRecentRecord()
        onEvent()
        onUiStateEvent()
    }
    
    private fun onEvent() = binding.run { 
        rvAdapter.setOnItemClickListener { searchRecordDTO, i ->
            Intent().apply {
                putExtra("word", searchRecordDTO.word)
                setResult(RESULT_OK, this)
                finish()
            }

        }
    }
    
    private fun onUiStateEvent() = viewModel.run {
        recentRecord.observe(this@RecentRecordActivity) {
            rvAdapter.submitList(it.toList())
        }
        state.observe(this@RecentRecordActivity) { eventWrapper ->
            val event = eventWrapper.getContentIfNotHandled() ?: return@observe
            when (event) {
                RecentRecordViewModel.State.EMPTY_DATA -> {
                    toggleDataStateLayout(false)
                }
                RecentRecordViewModel.State.SUCCESS_GET -> {
                    toggleDataStateLayout(true)
                }
                RecentRecordViewModel.State.FAIL_GET -> {

                }
                RecentRecordViewModel.State.SUCCESS_DELETE -> {

                }
                RecentRecordViewModel.State.FAIL_DELETE -> {

                }
            }
        }
    }

    private fun setRecyclerViewAdapter() = binding.rvMovieList.apply {
        layoutManager = LinearLayoutManager(this@RecentRecordActivity, LinearLayoutManager.VERTICAL, false)
        adapter = rvAdapter
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
}