package com.kej.wordbook.data

import com.kej.wordbook.data.model.Word
import com.kej.wordbook.data.dao.WordDao
import com.kej.wordbook.domain.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val wordDao: WordDao) : Repository {
    override suspend fun getAll(): Flow<List<Word>> {
        return wordDao.getAll()
    }

    override suspend fun getLatestWord(): Word {
        return wordDao.getLatestWord()
    }

    override suspend fun insert(word: Word) {
        wordDao.insert(word)
    }

    override suspend fun delete(word: Word) {
        wordDao.delete(word)
    }

    override suspend fun update(word: Word) {
        wordDao.update(word)
    }
}