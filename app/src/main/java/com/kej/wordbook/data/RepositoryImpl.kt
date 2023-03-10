package com.kej.wordbook.data

import com.kej.wordbook.data.dao.WordDao
import com.kej.wordbook.data.model.toWord
import com.kej.wordbook.data.model.toWordModel
import com.kej.wordbook.domain.Repository
import com.kej.wordbook.domain.model.WordModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val wordDao: WordDao) : Repository {
    override suspend fun getAll(): Flow<List<WordModel>> {
        return wordDao.getAll().map { wordList-> wordList.map { it.toWordModel() } }
    }

    override suspend fun getLatestWord(): WordModel {
        return wordDao.getLatestWord().toWordModel()
    }

    override suspend fun insert(word: WordModel) {
        wordDao.insert(word.toWord())
    }

    override suspend fun delete(word: WordModel) {
        wordDao.delete(word.toWord())
    }

    override suspend fun update(word: WordModel) {
        wordDao.update(word.toWord())
    }
}