package com.yusufgltc.stumood.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "daily_study_quality_table")
data class Study(
    @PrimaryKey(autoGenerate = true)
    var studyId: Long = 0L,

    @ColumnInfo(name = "start_time")
    val startTime: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "end_time")
    var endTime: Long = startTime,

    @ColumnInfo(name = "quality_rating")
    var studyQuality: Int = -1
)