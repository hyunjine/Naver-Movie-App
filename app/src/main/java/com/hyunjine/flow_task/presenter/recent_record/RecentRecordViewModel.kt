package com.hyunjine.flow_task.presenter.recent_record

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hyunjine.flow_task.common.loggerD
import com.hyunjine.flow_task.common.loggerE
import com.hyunjine.flow_task.presenter.common.base.BaseViewModel
import com.hyunjine.flow_task.presenter.common.base.util.ListLiveData
import com.hyunjine.flow_task.presenter.recent_record.usecase.DeleteRecentRecordUseCase
import com.hyunjine.flow_task.presenter.recent_record.usecase.GetRecentRecordUseCase
import com.hyunjine.flow_task.presenter.search_movie.usecase.InsertSearchRecordUseCase
import com.hyunjine.flow_task.presenter.search_movie.vo.MovieItemDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RecentRecordViewModel @Inject constructor(
    private val getRecentRecordUseCase: GetRecentRecordUseCase,
    private val deleteRecentRecordUseCase: DeleteRecentRecordUseCase
) : BaseViewModel<RecentRecordViewModel.State>() {
    companion object {
        private const val MAX_DISPLAY_INDEX: Int = 10
    }
    private val _recentRecord = ListLiveData<String>()
    val recentRecord: LiveData<MutableList<String>> get() = _recentRecord

    fun getRecentRecord() {
        runAsync(methodName, getRecentRecordUseCase(), SINGLE_SCHEDULER)
            .subscribe({ dto ->
                val sort = dto.sortedByDescending { it.generateTimestamp }
                if (sort.size > MAX_DISPLAY_INDEX) {
                    val standardTimestamp = sort[MAX_DISPLAY_INDEX - 1].generateTimestamp
                    deleteRecentRecord(standardTimestamp)
                    val requireData = sort
                        .slice(0 until MAX_DISPLAY_INDEX)
                        .map { it.word }
                    _recentRecord.addAll(requireData)
                } else {
                    val requireData = sort.map { it.word }
                    _recentRecord.addAll(requireData)
                }
            }, {
                loggerE(methodName, it)
            }).addDispose()
    }

    private fun deleteRecentRecord(timestamp: Long) {
        runAsync(methodName, deleteRecentRecordUseCase(timestamp), SINGLE_SCHEDULER)
            .subscribe({
                loggerD(methodName, "Delete Success")
            }, {
                loggerD(methodName, it)
            }).addDispose()
    }

    enum class State {

    }
}