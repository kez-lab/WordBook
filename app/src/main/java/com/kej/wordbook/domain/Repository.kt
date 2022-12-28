package com.kej.wordbook.domain

import com.kej.wordbook.data.model.Word

interface Repository {

    suspend fun getAll(): List<Word>?

    suspend fun getLatestWord(): Word?

    suspend fun insert(word: Word)

    suspend fun delete(word: Word)

    suspend fun update(word: Word)

}