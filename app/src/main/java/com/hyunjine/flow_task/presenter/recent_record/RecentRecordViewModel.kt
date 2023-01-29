package com.hyunjine.flow_task.presenter.recent_record

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hyunjine.flow_task.common.loggerD
import com.hyunjine.flow_task.common.loggerE
import com.hyunjine.flow_task.presenter.common.base.BaseViewModel
import com.hyunjine.flow_task.presenter.common.base.util.ListLiveData
import com.hyunjine.flow_task.presenter.recent_record.usecase.DeleteRecentRecordUseCase
import com.hyunjine.flow_task.presenter.recent_record.usecase.GetRecentRecordUseCase
import com.hyunjine.flow_task.presenter.recent_record.vo.SearchRecordDTO
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
    private val _recentRecord = ListLiveData<SearchRecordDTO>()
    val recentRecord: LiveData<MutableList<SearchRecordDTO>> get() = _recentRecord

    fun getRecentRecord() {
        runAsync(methodName, getRecentRecordUseCase(), SINGLE_SCHEDULER)
            .subscribe({ dto ->
                when (dto.size) {
                    0 -> {
                        setState(State.EMPTY_DATA)
                    }
                    in 1.. MAX_DISPLAY_INDEX -> {
                        val sort = dto.sortedByDescending { it.generateTimestamp }
                        _recentRecord.addAll(sort)
                        setState(State.SUCCESS_GET)
                    }
                    else -> {
                        val sort = dto.sortedByDescending { it.generateTimestamp }
                        val standardTimestamp = sort[MAX_DISPLAY_INDEX - 1].generateTimestamp
                        deleteRecentRecord(standardTimestamp)
                        val requireData = sort.slice(0 until MAX_DISPLAY_INDEX)
                        _recentRecord.addAll(requireData)
                        setState(State.SUCCESS_GET)
                    }
                }
            }, {
                setState(State.FAIL_GET)
                loggerE(methodName, it)
            }).addDispose()
    }

    private fun deleteRecentRecord(timestamp: Long) {
        runAsync(methodName, deleteRecentRecordUseCase(timestamp), SINGLE_SCHEDULER)
            .subscribe({
                setState(State.SUCCESS_DELETE)
            }, {
                setState(State.FAIL_DELETE)
                loggerD(methodName, it)
            }).addDispose()
    }

    enum class State {
        EMPTY_DATA,
        SUCCESS_GET,
        FAIL_GET,
        SUCCESS_DELETE,
        FAIL_DELETE
    }
}