package com.kej.wordbook.presenter.main

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kej.wordbook.data.database.AppDatabase
import com.kej.wordbook.data.model.Word
import com.kej.wordbook.domain.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    fun deleteData(inSelectedWord: Word) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(inSelectedWord)
        }
    }
}