package com.hyunjine.flow_task.presenter.recent_record.vo

import com.google.gson.annotations.SerializedName

data class SearchRecordDTO(
    @SerializedName("generateTimestamp")
    val generateTimestamp: Long,
    @SerializedName("word")
    val word: String
)
