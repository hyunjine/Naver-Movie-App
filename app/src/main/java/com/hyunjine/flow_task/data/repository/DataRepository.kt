package com.hyunjine.flow_task.data.repository

import com.hyunjine.flow_task.data.database.vo.SearchRecordEntity
import com.hyunjine.flow_task.presenter.recent_record.vo.SearchRecordDTO
import com.hyunjine.flow_task.data.naver.vo.MoviesEntity
import io.reactivex.Completable
import io.reactivex.Single

interface DataRepository {
    fun getMovies(query: String, display: Int, start: Int): Single<MoviesEntity>

    fun getRecentRecordFromDataBase(): Single<List<SearchRecordEntity>>
    fun insertSearchRecordFromDataBase(searchRecord: SearchRecordEntity): Completable
    fun deleteRecentRecordFromDataBase(/** timestamp보다 작은 값*/timestamp: Long): Completable
}