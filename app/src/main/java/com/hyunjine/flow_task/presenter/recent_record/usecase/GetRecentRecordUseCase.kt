package com.hyunjine.flow_task.presenter.recent_record.usecase

import com.hyunjine.flow_task.data.repository.DataRepository
import com.hyunjine.flow_task.presenter.recent_record.vo.SearchRecordDTO
import io.reactivex.Single
import javax.inject.Inject

class GetRecentRecordUseCase @Inject constructor(
    private val dataRepository: DataRepository
) {
    operator fun invoke(): Single<List<SearchRecordDTO>> =
        dataRepository.getRecentRecordFromDataBase().map { entity ->
            entity.map {
                SearchRecordDTO(it.generateTimestamp, it.word)
            }
        }
}