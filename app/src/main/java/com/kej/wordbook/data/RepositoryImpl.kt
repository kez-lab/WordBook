package com.kej.wordbook.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.kej.wordbook.data.dao.WordDao
import com.kej.wordbook.data.model.Word
import com.kej.wordbook.data.model.toWord
import com.kej.wordbook.data.model.toWordModel
import com.kej.wordbook.data.paging.WordPagingSource
import com.kej.wordbook.domain.Repository
import com.kej.wordbook.domain.model.WordModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val wordDao: WordDao, private val wordPagingSource: WordPagingSource) : Repository {
    override suspend fun getAll(): Flow<PagingData<WordModel>> {
        return Pager(
            config = PagingConfig(5, enablePlaceholders = false),
            pagingSourceFactory = { wordPagingSource }
        ).flow
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