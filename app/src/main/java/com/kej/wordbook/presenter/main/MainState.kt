package com.kej.wordbook.presenter.main

import com.kej.wordbook.data.model.Word

sealed class MainState {
    object UnInitialized : MainState()

    data class SuccessWordList(
        val wordList: List<Word>
    ) : MainState()

    data class SuccessLatestWord(
        val lastWord: Word
    ) : MainState()

    object Delete: MainState()

    object Error: MainState()
}
