package com.hyunjine.flow_task.data.repository

import com.hyunjine.flow_task.data.database.RecentRecordDao
import com.hyunjine.flow_task.data.database.vo.SearchRecordEntity
import com.hyunjine.flow_task.data.naver.NaverServerService
import com.hyunjine.flow_task.data.naver.vo.MoviesEntity
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class DataRepositoryImpl @Inject constructor(
    private val naverServerService: NaverServerService,
    private val recentRecordDao: RecentRecordDao
) : DataRepository {

    override suspend fun getMovies(query: String, display: Int, start: Int): MoviesEntity =
        naverServerService.getSearchMovie(query, display, start)

    override fun getRecentRecordFromDataBase(): Single<List<SearchRecordEntity>> =
        recentRecordDao.getRecentRecord()

    override fun insertSearchRecordFromDataBase(searchRecord: SearchRecordEntity): Completable =
        recentRecordDao.insertSearchRecord(searchRecord)

    override fun deleteRecentRecordFromDataBase(timestamp: Long): Completable =
        recentRecordDao.deleteRecentRecord(timestamp)

    @Module
    @InstallIn(ViewModelComponent::class)
    abstract class DataRepositoryModule() {
        @Binds
        abstract fun bindDataRepository(impl: DataRepositoryImpl): DataRepository
    }
}