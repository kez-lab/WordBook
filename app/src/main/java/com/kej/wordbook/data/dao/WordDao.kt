package com.kej.wordbook.data.dao

import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.room.*
import com.kej.wordbook.data.model.Word
import kotlinx.coroutines.flow.Flow

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