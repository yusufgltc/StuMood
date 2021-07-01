package com.yusufgltc.stumood.studyquality

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yusufgltc.stumood.database.StudyDatabaseDao
import java.lang.IllegalArgumentException

class StudyQualityViewModelFactory(
    private val studyKey: Long,
    private val dataSource: StudyDatabaseDao
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StudyQualityViewModel::class.java)) {
            return StudyQualityViewModel(studyKey, dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}