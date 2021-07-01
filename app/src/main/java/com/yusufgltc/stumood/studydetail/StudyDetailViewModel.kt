package com.yusufgltc.stumood.studydetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yusufgltc.stumood.database.Study
import com.yusufgltc.stumood.database.StudyDatabaseDao

class StudyDetailViewModel(
    private val studyKey: Long = 0L,
    dataSource: StudyDatabaseDao
) : ViewModel() {

    val database = dataSource

    private val study: LiveData<Study>

    fun getStudy() = study

    init {
        study = database.getStudyWithId(studyKey)
    }


    private val _navigateToStudyTracker = MutableLiveData<Boolean?>()

    val navigateToStudyTracker: LiveData<Boolean?>
        get() = _navigateToStudyTracker

    fun doneNavigating() {
        _navigateToStudyTracker.value = null
    }

    fun onClose() {
        _navigateToStudyTracker.value = true
    }

}