package com.hyunjine.flow_task.presenter.recent_record.usecase

import com.hyunjine.flow_task.data.repository.DataRepository
import io.reactivex.Completable
import javax.inject.Inject

class DeleteRecentRecordUseCase @Inject constructor(
    private val dataRepository: DataRepository
) {
    operator fun invoke(/** timestamp보다 작은 값*/timestamp: Long): Completable =
        dataRepository.deleteRecentRecordFromDataBase(timestamp)
}