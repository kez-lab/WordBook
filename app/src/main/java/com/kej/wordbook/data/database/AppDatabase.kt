package com.kej.wordbook.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kej.wordbook.data.model.Word

@Database(entities = [Word::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun wordDao():WordDao

}