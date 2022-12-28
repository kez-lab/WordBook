package com.kej.wordbook

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.kej.wordbook.LibContents.EDIT_WORLD
import com.kej.wordbook.LibContents.IS_UPDATE
import com.kej.wordbook.LibContents.WORLD
import com.kej.wordbook.database.AppDatabase
import com.kej.wordbook.database.Word
import com.kej.wordbook.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var wordAdapter: WordAdapter
    private var selectedWord: Word? = null
    private val updateAddWordResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val isUpdate = result.data?.getBooleanExtra(IS_UPDATE, false) ?: false
        val editWord = result.data?.getParcelableExtra<Word>(EDIT_WORLD)
        if (result.resultCode == RESULT_OK) {
            if (isUpdate) {
                updateAddWord()
            } else if (editWord != null) {
                updateEditWord(editWord)
            }

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecyclerView()
        initViews()
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
            Thread {
                AppDatabase.getInstance(this)?.wordDao()?.delete(inSelectedWord)
                runOnUiThread {
                    wordAdapter.list.remove(inSelectedWord)
                    wordAdapter.notifyDataSetChanged()
                    setScreenWord(null)
                    selectedWord = null
                    Toast.makeText(this, getString(R.string.delete_success), Toast.LENGTH_SHORT).show()
                }
            }.start()
        }
    }

    private fun initRecyclerView() {
        wordAdapter = WordAdapter(mutableListOf()) { word ->
            selectedWord = word
            setScreenWord(word)
        }
        binding.wordRecyclerView.apply {
            adapter = wordAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
            val divider = DividerItemDecoration(this@MainActivity, LinearLayoutManager.VERTICAL)
            addItemDecoration(divider)
        }

        Thread {
            val list = AppDatabase.getInstance(this)?.wordDao()?.getAll() ?: emptyList()
            wordAdapter.list = list as MutableList<Word>
            wordAdapter.notifyDataSetChanged()
        }.start()
    }

    private fun updateAddWord() {
        Thread {
            AppDatabase.getInstance(this)?.wordDao()?.getLatestWord()?.let { word ->
                wordAdapter.list.add(0, word)
                runOnUiThread {
                    wordAdapter.notifyDataSetChanged()
                }
            }
        }.start()
    }

    private fun updateEditWord(word: Word) {
        val index = wordAdapter.list.indexOfFirst {
            it.id == word.id
        }
        setScreenWord(word)
        wordAdapter.list[index] = word
        wordAdapter.notifyItemChanged(index)

    }

    private fun setScreenWord(word: Word?){
        if (word == null) {
            binding.textTextView.text = ""
            binding.meanTextView.text = ""
            binding.typeTextView.text = ""
        } else {
            binding.textTextView.text = word?.text
            binding.meanTextView.text = "뜻: ${word?.mean}"
            binding.typeTextView.text = "품사: ${word?.type}"
        }
    }

}