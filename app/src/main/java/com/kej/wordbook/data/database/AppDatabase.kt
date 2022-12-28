package com.kej.wordbook.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kej.wordbook.data.model.Word

@Database(entities = [Word::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun wordDao():WordDao

    companion object {
        private var appDatabase: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return appDatabase ?: synchronized(this) {
                val newAppDatabase = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "app-database.db").build()
                appDatabase = newAppDatabase
                newAppDatabase
            }
        }
    }
}