package com.kej.wordbook.presenter.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kej.wordbook.Application
import com.kej.wordbook.data.model.Word
import com.kej.wordbook.data.RepositoryImpl
import com.kej.wordbook.data.database.AppDatabase
import com.kej.wordbook.domain.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

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