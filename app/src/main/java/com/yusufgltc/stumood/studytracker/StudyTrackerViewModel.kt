package com.yusufgltc.stumood.studytracker

import android.app.Application
import androidx.lifecycle.*
import com.yusufgltc.stumood.database.Study
import com.yusufgltc.stumood.database.StudyDatabaseDao
import com.yusufgltc.stumood.formatStudies
import kotlinx.coroutines.launch

class StudyTrackerViewModel(
    dataSource: StudyDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    /**
     * Hold a reference to SleepDatabase via SleepDatabaseDao.
     */
    val database = dataSource

    private var today = MutableLiveData<Study?>()

    val studies = database.getAllStudies()

    val studiesString = Transformations.map(studies) { studies ->
        formatStudies(studies, application.resources)
    }

    val startButtonVisible = Transformations.map(today) {
        null == it
    }
    val stopButtonVisible = Transformations.map(today) {
        null != it
    }
    val clearButtonVisible = Transformations.map(studies) {
        it?.isNotEmpty()
    }

    private var _showSnackbarEvent = MutableLiveData<Boolean>()
    val showSnackBarEvent: LiveData<Boolean>
        get() = _showSnackbarEvent


    private val _navigateToStudyQuality = MutableLiveData<Study?>()
    val navigateToStudyQuality: MutableLiveData<Study?>
        get() = _navigateToStudyQuality

    private val _navigateToStudyDetail = MutableLiveData<Long?>()
    val navigateToStudyDetail
        get() = _navigateToStudyDetail

    fun doneShowingSnackbar() {
        _showSnackbarEvent.value = false
    }

    fun doneNavigating() {
        _navigateToStudyQuality.value = null
    }

    fun onStudyClicked(id: Long) {
        _navigateToStudyDetail.value = id
    }

    fun onStudyDetailNavigated() {
        _navigateToStudyDetail.value = null
    }

    init {
        initializeToday()
    }

    //get
    private fun initializeToday() {
        viewModelScope.launch {
            today.value = getTodayFromDatabase()
        }
    }

    private suspend fun getTodayFromDatabase(): Study? {
        var study = database.getToday()
        if (study?.endTime != study?.startTime) {
            study = null
        }
        return study
    }

    //start
    fun onStart() {
        viewModelScope.launch {
            val newStudy = Study()
            insert(newStudy)
            today.value = getTodayFromDatabase()
        }
    }

    private suspend fun insert(study: Study) {
        database.insert(study)
    }

    //stop
    fun onStop() {
        viewModelScope.launch {
            val oldStudy = today.value ?: return@launch
            oldStudy.endTime = System.currentTimeMillis()
            update(oldStudy)
            _navigateToStudyQuality.value = oldStudy
        }
    }

    private suspend fun update(study: Study) {
        database.update(study)
    }

    //clear
    fun onClear() {
        viewModelScope.launch {
            clear()
            today.value = null
            _showSnackbarEvent.value = true
        }
    }

    suspend fun clear() {
        database.clear()
    }

}