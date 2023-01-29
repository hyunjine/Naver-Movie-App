package com.hyunjine.flow_task.presenter.recent_record

import android.os.Bundle
import androidx.activity.viewModels
import com.hyunjine.flow_task.R
import com.hyunjine.flow_task.common.loggerD
import com.hyunjine.flow_task.databinding.ActivityRecentRecordBinding
import com.hyunjine.flow_task.presenter.common.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecentRecordActivity : BaseActivity<ActivityRecentRecordBinding>(R.layout.activity_recent_record) {
    private val viewModel: RecentRecordViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getRecentRecord()

        viewModel.recentRecord.observe(this) {
            it.forEach { a->
                loggerD("zz", a)
            }
        }
    }
}