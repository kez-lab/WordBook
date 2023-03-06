package com.kej.wordbook.presenter.main

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
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
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.mainState.collectLatest {
                    when (it) {
                        is MainState.UnInitialized -> {
                            initViews()
                            initRecyclerView()
                            initData()
                        }
                        is MainState.Delete -> {
                            deleteHandler()
                        }
                        is MainState.SuccessWordList -> {
                            successListHandler(it.wordList)
                        }
                        is MainState.SuccessLatestWord -> {
                            successLatestWordHandler(it.lastWord)
                        }
                        is MainState.Error -> {
                            Toast.makeText(this@MainActivity, getString(R.string.error), Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
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

    private fun initRecyclerView() {
        wordAdapter = WordAdapter { word ->
            selectedWord = word
            setScreenWord(word)
        }
        binding.wordRecyclerView.apply {
            adapter = wordAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
            val divider = DividerItemDecoration(this@MainActivity, LinearLayoutManager.VERTICAL)
            addItemDecoration(divider)
        }
    }

    private fun initData() {
        viewModel.getAllList()
    }

    private fun deleteHandler() {
        setScreenWord(null)
        selectedWord = null
        Toast.makeText(this, getString(R.string.delete_success), Toast.LENGTH_SHORT).show()
    }

    private fun successLatestWordHandler(latestWord: Word) {
        setScreenWord(latestWord)
        wordAdapter.submitList(currentWordList)
        wordAdapter.notifyDataSetChanged()
    }

    private fun successListHandler(wordList: List<Word>) {
        currentWordList.clear()
        currentWordList.addAll(wordList)
        wordAdapter.submitList(currentWordList)
        wordAdapter.notifyDataSetChanged()
    }

    private fun deleteData() {
        selectedWord?.let { inSelectedWord ->
            viewModel.deleteData(inSelectedWord)
        }
    }

    private fun updateEditWord(word: Word) {
        setScreenWord(word)
    }

    private fun setScreenWord(word: Word?) {
        if (word == null) {
            binding.textTextView.text = getString(R.string.noun)
            binding.meanTextView.text = getString(R.string.value, "")
            binding.typeTextView.text = getString(R.string.type, "")
        } else {
            binding.textTextView.text = word.text
            binding.meanTextView.text = getString(R.string.value, word.mean)
            binding.typeTextView.text = getString(R.string.type, word.type)
        }
    }

}