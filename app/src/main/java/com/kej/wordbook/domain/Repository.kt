package com.kej.wordbook.domain

import com.kej.wordbook.domain.model.WordModel
import kotlinx.coroutines.flow.Flow

interface Repository {

    suspend fun getAll(): Flow<List<WordModel>>

    suspend fun getLatestWord(): WordModel

    suspend fun insert(word: WordModel)

    suspend fun delete(word: WordModel)

    suspend fun update(word: WordModel)

}