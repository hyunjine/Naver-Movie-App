package com.hyunjine.flow_task.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hyunjine.flow_task.data.database.vo.SearchRecordEntity
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface RecentRecordDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSearchRecord(searchRecord: SearchRecordEntity): Completable

    @Query("DELETE FROM RecentRecord WHERE generateTimestamp < :timestamp")
    fun deleteRecentRecord(timestamp: Long): Completable

    @Query("SELECT * FROM RecentRecord")
    fun getRecentRecord(): Single<List<SearchRecordEntity>>
}