package com.kej.wordbook.presenter.add

import com.kej.wordbook.data.model.Word

sealed class AddState {
    object UnInitialized : AddState()

    object InsertSuccess : AddState()

    data class UpdateSuccess(
        val word: Word
    ) : AddState()

    object Error : AddState()
}
