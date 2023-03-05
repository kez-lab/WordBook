package com.kej.wordbook.domain

import com.kej.wordbook.data.model.Word
import kotlinx.coroutines.flow.Flow

interface Repository {

    suspend fun getAll(): Flow<List<Word>?>

    suspend fun getLatestWord(): Word?

    suspend fun insert(word: Word)

    suspend fun delete(word: Word)

    suspend fun update(word: Word)

}