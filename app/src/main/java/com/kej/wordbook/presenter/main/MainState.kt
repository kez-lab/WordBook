package com.kej.wordbook.presenter.main

import androidx.paging.PagingData
import com.kej.wordbook.domain.model.WordModel
import kotlinx.coroutines.flow.Flow

sealed class MainState {
    object UnInitialized : MainState()

    data class SuccessWordList(
        val wordList: PagingData<WordModel>
    ) : MainState()

    data class SuccessLatestWord(
        val lastWord: WordModel
    ) : MainState()

    object Delete: MainState()

    object Error: MainState()
}
