package com.kej.wordbook.presenter.main

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.kej.wordbook.LibContents.EDIT_WORLD
import com.kej.wordbook.LibContents.IS_UPDATE
import com.kej.wordbook.LibContents.WORLD
import com.kej.wordbook.R
import com.kej.wordbook.data.model.Word
import com.kej.wordbook.databinding.ActivityMainBinding
import com.kej.wordbook.presenter.adapter.WordAdapter
import com.kej.wordbook.presenter.add.AddActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@SuppressLint("NotifyDataSetChanged")
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var wordAdapter: WordAdapter
    private var selectedWord: Word? = null
    private val currentWordList = arrayListOf<Word>()
    private val updateAddWordResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val isUpdate = result.data?.getBooleanExtra(IS_UPDATE, false) ?: false
        val editWord = result.data?.getParcelableExtra<Word>(EDIT_WORLD)
        if (result.resultCode == RESULT_OK) {
            if (isUpdate) {
                viewModel.getLatestWord()
            } else if (editWord != null) {
                updateEditWord(editWord)
            }

        }
    }
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initObserve()
    }

    private fun initObserve() {
        lifecycleScope.launch {
            viewModel.mainState.collectLatest {
                when (it) {
                    is MainState.UnInitialized -> {
                        initRecyclerView()
                        initViews()
                    }
                    is MainState.Delete -> {
                        deleteHandling()
                        wordAdapter.notifyDataSetChanged()
                    }
                    is MainState.SuccessWordList -> {
                        currentWordList.addAll(it.wordList)
                        wordAdapter.submitList(currentWordList)
                    }
                    is MainState.SuccessLatestWord -> {
                        currentWordList.add(0, it.lastWord)
                        wordAdapter.submitList(currentWordList)
                        wordAdapter.notifyDataSetChanged()
                    }
                    is MainState.Error -> {
                        Toast.makeText(this@MainActivity, "에러가 발생하였습니다.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun deleteHandling() {
        wordAdapter.notifyDataSetChanged()
        setScreenWord(null)
        selectedWord = null
        Toast.makeText(this, getString(R.string.delete_success), Toast.LENGTH_SHORT).show()
    }

    private fun initViews() {
        binding.addButton.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)
            updateAddWordResult.launch(intent)
        }

        binding.deleteButton.setOnClickListener {
            deleteData()
        }

        binding.editButton.setOnClickListener {
            selectedWord?.let {
                val intent = Intent(this, AddActivity::class.java)
                intent.putExtra(WORLD, it)
                updateAddWordResult.launch(intent)
            }
        }
    }

    private fun deleteData() {
        selectedWord?.let { inSelectedWord ->
            viewModel.deleteData(inSelectedWord)
        }
    }

    private fun initRecyclerView() {
        wordAdapter = WordAdapter() { word ->
            selectedWord = word
            setScreenWord(word)
        }
        binding.wordRecyclerView.apply {
            adapter = wordAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
            val divider = DividerItemDecoration(this@MainActivity, LinearLayoutManager.VERTICAL)
            addItemDecoration(divider)
        }
        viewModel.getAllList()
    }

    private fun updateEditWord(word: Word) {
        val index = wordAdapter.currentList.indexOfFirst {
            it.id == word.id
        }
        setScreenWord(word)
        currentWordList[index] = word
        wordAdapter.submitList(currentWordList)
        wordAdapter.notifyItemChanged(index)
    }

    private fun setScreenWord(word: Word?) {
        if (word == null) {
            binding.textTextView.text = ""
            binding.meanTextView.text = ""
            binding.typeTextView.text = ""
        } else {
            binding.textTextView.text = word.text
            binding.meanTextView.text = "뜻: ${word?.mean}"
            binding.typeTextView.text = "품사: ${word?.type}"
        }
    }

}