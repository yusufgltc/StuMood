package com.yusufgltc.stumood.studydetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yusufgltc.stumood.database.StudyDatabaseDao
import java.lang.IllegalArgumentException

class StudyDetailViewModelFactory(
    private val studyKey: Long,
    private val dataSource: StudyDatabaseDao
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StudyDetailViewModel::class.java)) {
            return StudyDetailViewModel(studyKey, dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}