package com.hyunjine.flow_task.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hyunjine.flow_task.data.database.vo.SearchRecordEntity

@Database(entities = [SearchRecordEntity::class], version = 1)
abstract class RecentRecordDataBase : RoomDatabase() {
    abstract fun recentRecordDao(): RecentRecordDao
}