package com.kej.wordbook.presenter.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kej.wordbook.domain.Repository
import com.kej.wordbook.domain.model.WordModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private var _mainState = MutableStateFlow<MainState>(MainState.UnInitialized)
    val mainState: StateFlow<MainState> get() = _mainState


    fun deleteData(inSelectedWord: WordModel) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(inSelectedWord)
            _mainState.value = MainState.Delete
        }
    }

    fun getAllList() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAll().collectLatest { wordList ->
                _mainState.value = MainState.SuccessWordList(wordList)
            }
        }
    }

    fun getLatestWord() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getLatestWord().let {
                _mainState.value = MainState.SuccessLatestWord(it)
            }
        }
    }
}