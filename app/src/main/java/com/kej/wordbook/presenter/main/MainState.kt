package com.kej.wordbook.presenter.main

import com.kej.wordbook.domain.model.WordModel

sealed class MainState {
    object UnInitialized : MainState()

    data class SuccessWordList(
        val wordList: List<WordModel>
    ) : MainState()

    data class SuccessLatestWord(
        val lastWord: WordModel
    ) : MainState()

    object Delete: MainState()

    object Error: MainState()
}
