package com.kej.wordbook.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.kej.wordbook.data.dao.WordDao
import com.kej.wordbook.data.model.toWordModel
import com.kej.wordbook.domain.model.WordModel
import javax.inject.Inject

class WordPagingSource @Inject constructor(private val wordDao: WordDao) : PagingSource<Int, WordModel>() {
    override fun getRefreshKey(state: PagingState<Int, WordModel>): Int {
        return 0
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, WordModel> {
        return try {
            val currentPage = params.key ?: 0
            val result: List<WordModel> = wordDao.getAll().map { wordList ->
                wordList.toWordModel()
            }
            val prevKey = if (currentPage == result.size) 1 else currentPage - 1
            val nextKey = if (result.size < 7) 1 else currentPage + 1
            LoadResult.Page(
                data = result, nextKey = prevKey, prevKey = nextKey
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}