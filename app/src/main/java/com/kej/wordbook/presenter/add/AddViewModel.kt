package com.kej.wordbook.presenter.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kej.wordbook.Application
import com.kej.wordbook.data.model.Word
import com.kej.wordbook.data.RepositoryImpl
import com.kej.wordbook.data.database.AppDatabase
import com.kej.wordbook.data.model.toWordModel
import com.kej.wordbook.domain.Repository
import com.kej.wordbook.domain.model.WordModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val _addStateFlow = MutableStateFlow<AddState>(AddState.UnInitialized)
    val addStateFlow:StateFlow<AddState> get() = _addStateFlow
    fun insertData(word: Word) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(word.toWordModel())
            _addStateFlow.value = AddState.InsertSuccess
        }
    }

    fun updateData(word: Word) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.update(word.toWordModel())
            _addStateFlow.value = AddState.UpdateSuccess(word.toWordModel())
        }
    }

}