package com.kej.wordbook.presenter.add

import com.kej.wordbook.data.model.Word
import com.kej.wordbook.domain.model.WordModel

sealed class AddState {
    object UnInitialized : AddState()

    object InsertSuccess : AddState()

    data class UpdateSuccess(
        val word: WordModel
    ) : AddState()

    object Error : AddState()
}
