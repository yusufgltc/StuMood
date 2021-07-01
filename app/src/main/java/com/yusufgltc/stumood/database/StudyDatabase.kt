package com.yusufgltc.stumood.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Study::class], version = 1, exportSchema = false)
abstract class StudyDatabase : RoomDatabase() {

    abstract val studyDatabaseDao: StudyDatabaseDao

    companion object {

        @Volatile
        private var INSTANCE: StudyDatabase? = null

        fun getInstance(context: Context): StudyDatabase? {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        StudyDatabase::class.java,
                        "study_history_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }
                return instance
            }
        }
    }
}