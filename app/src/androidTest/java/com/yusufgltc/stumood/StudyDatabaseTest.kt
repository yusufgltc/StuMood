package com.yusufgltc.stumood

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.yusufgltc.stumood.database.Study
import com.yusufgltc.stumood.database.StudyDatabase
import com.yusufgltc.stumood.database.StudyDatabaseDao
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class StudyDatabaseTest {
    private lateinit var studyDao: StudyDatabaseDao
    private lateinit var db: StudyDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, StudyDatabase::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        studyDao = db.studyDatabaseDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
     fun insertAndGetStudy() {
        val study = Study()
        studyDao.insert(study)
        val today = studyDao.getToday()
        Assert.assertEquals(today?.studyQuality, -1)
    }
}