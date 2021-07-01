package com.yusufgltc.stumood.studyquality

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yusufgltc.stumood.database.StudyDatabaseDao
import kotlinx.coroutines.launch

class StudyQualityViewModel(
    private val studyKey: Long = 0L,
    val database: StudyDatabaseDao
) : ViewModel() {

    private val _navigateToStudyTracker = MutableLiveData<Boolean?>()
    val navigateToStudyTracker: LiveData<Boolean?>
        get() = _navigateToStudyTracker

    fun doneNavigating() {
        _navigateToStudyTracker.value = null
    }

    fun onSetStudyQuality(quality: Int) {
        viewModelScope.launch {
            val today = database.get(studyKey) ?: return@launch
            today.studyQuality = quality
            database.update(today)

            _navigateToStudyTracker.value = true
        }
    }

}