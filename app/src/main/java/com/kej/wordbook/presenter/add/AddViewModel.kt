package com.kej.wordbook.presenter.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kej.wordbook.Application
import com.kej.wordbook.data.model.Word
import com.kej.wordbook.domain.RepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddViewModel : ViewModel() {

    private val repository = RepositoryImpl(Application.database.wordDao())

    fun insertData(word: Word) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(word)
        }
    }

    fun updateData(word: Word) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.update(word)
        }
    }

}