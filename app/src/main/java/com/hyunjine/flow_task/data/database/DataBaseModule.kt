package com.hyunjine.flow_task.data.database

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {
    @Singleton
    @Provides
    fun provideMovieDao(movieDatabase: RecentRecordDataBase): RecentRecordDao =
        movieDatabase.recentRecordDao()

    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext context: Context): RecentRecordDataBase =
        Room.databaseBuilder(
            context,
            RecentRecordDataBase::class.java,
            "RecentRecord.db"
        ).build()
}