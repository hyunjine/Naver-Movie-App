package com.hyunjine.flow_task.data.database.vo

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "RecentRecord")
data class SearchRecordEntity(
    @PrimaryKey(autoGenerate = false)
    @SerializedName("generateTimestamp")
    val generateTimestamp: Long,
    @SerializedName("word")
    val word: String
)
