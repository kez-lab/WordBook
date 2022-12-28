package com.kej.wordbook.data.database

import androidx.room.*
import com.kej.wordbook.data.model.Word

@Dao
interface WordDao {
    @Query("SELECT * from word ORDER BY id DESC")
    fun getAll(): List<Word>

    @Query("SELECT * from word ORDER BY id DESC LIMIT 1")
    fun getLatestWord(): Word

    @Insert
    fun insert(word: Word)

    @Delete
    fun delete(word: Word)

    @Update
    fun update(word: Word)

}