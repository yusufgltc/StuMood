package com.yusufgltc.stumood.studytracker

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yusufgltc.stumood.database.StudyDatabaseDao
import java.lang.IllegalArgumentException

class StudyTrackerViewModelFactory(
    private val dataSource: StudyDatabaseDao,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StudyTrackerViewModel::class.java)) {
            return StudyTrackerViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}

