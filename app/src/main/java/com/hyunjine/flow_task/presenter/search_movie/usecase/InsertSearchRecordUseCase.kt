package com.hyunjine.flow_task.presenter.search_movie.usecase

import com.hyunjine.flow_task.data.database.vo.SearchRecordEntity
import com.hyunjine.flow_task.data.repository.DataRepository
import io.reactivex.Completable
import javax.inject.Inject

class InsertSearchRecordUseCase @Inject constructor(
    private val dataRepository: DataRepository
) {
    operator fun invoke(word: String): Completable =
        dataRepository.insertSearchRecordFromDataBase(
            SearchRecordEntity(word, System.currentTimeMillis())
        )
}