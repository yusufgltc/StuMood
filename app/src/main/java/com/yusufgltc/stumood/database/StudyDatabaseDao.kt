package com.yusufgltc.stumood.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface StudyDatabaseDao {

    @Insert
    suspend fun insert(study: Study)

    @Update
    suspend fun update(study: Study)

    @Query("SELECT * FROM daily_study_quality_table WHERE studyId = :key")
    suspend fun get(key: Long): Study

    @Query("DELETE FROM daily_study_quality_table")
    suspend fun clear()

    //Room already uses a background thread for that specific @Query which returns LiveData.
    @Query("SELECT * FROM daily_study_quality_table ORDER BY studyId DESC")
    fun getAllStudies(): LiveData<List<Study>>

    @Query("SELECT * FROM daily_study_quality_table ORDER BY studyId DESC LIMIT 1")
    suspend fun getToday(): Study?

    @Query("SELECT * from daily_study_quality_table WHERE studyId = :key")
    fun getStudyWithId(key: Long): LiveData<Study>

}