package com.kej.wordbook.presenter.add

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.children
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import com.google.android.material.chip.Chip
import com.kej.wordbook.LibContents.EDIT_WORLD
import com.kej.wordbook.LibContents.IS_UPDATE
import com.kej.wordbook.LibContents.WORLD
import com.kej.wordbook.R
import com.kej.wordbook.data.model.Word
import com.kej.wordbook.databinding.ActivityAddBinding
import com.kej.wordbook.domain.model.WordModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint class AddActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddBinding
    private var editWord: WordModel? = null
    private var isEdit = false
    private val viewModel by viewModels<AddViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initObserve()
    }

    private fun initObserve() {
        lifecycleScope.launch {
            viewModel.addStateFlow.collectLatest {
                when (it) {
                    is AddState.UnInitialized -> {
                        initViews()
                        initChipGroup()
                        getIntentData()
                    }

                    is AddState.InsertSuccess -> {
                        val intent = Intent().putExtra(IS_UPDATE, true)
                        setResult(RESULT_OK, intent)
                        finish()
                    }

                    is AddState.UpdateSuccess -> {
                        val intent = Intent().putExtra(EDIT_WORLD, it.word)
                        setResult(RESULT_OK, intent)
                        finish()
                    }
                    else -> {
                        errorToast()
                    }
                }
            }

        }
    }

    private fun initChipGroup() {
        val types = resources.getStringArray(R.array.chip_group)
        binding.typeChipGroup.apply {
            types.forEach { text ->
                addView(createChip(text))
            }
        }
    }

    private fun initViews() {
        initStatusBar()
        binding.backButton.setOnClickListener {
            finish()
        }
        binding.addButton.apply {
            text = if (!isEdit) {
                getString(R.string.add)
            } else {
                getString(R.string.edit)
            }
            setOnClickListener {
                addData()
            }
        }

        binding.textInputEditText.addTextChangedListener {
            it?.let { text ->
                binding.textInputEditText.error = when (text.length) {
                    0 -> getString(R.string.please_input_word)
                    1 -> getString(R.string.input_second_up)
                    else -> null
                }
            }
        }
    }

    private fun initStatusBar() {
        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = true
        window?.statusBarColor = ContextCompat.getColor(this, R.color.white)
    }

    private fun getIntentData() {
        val word = intent.getParcelableExtra<WordModel>(WORLD)
        word?.let { inWord ->
            editWord = inWord
            isEdit = true
            binding.textInputEditText.setText(inWord.text)
            binding.meanTextInputEditText.setText(inWord.mean)
            val selectChip = binding.typeChipGroup.children.firstOrNull { (it as Chip).text == inWord.type } as? Chip
            selectChip?.isChecked = true
        }
    }


    private fun addData() {
        val text = binding.textInputEditText.text.toString()
        val mean = binding.meanTextInputEditText.text.toString()
        val checkId = binding.typeChipGroup.checkedChipId
        if (!checkValue(text, mean, checkId)) {
            return
        }
        val type = findViewById<Chip>(checkId).text.toString()

        if (!isEdit) {
            val word = Word(text, mean, type)
            viewModel.insertData(word)

        } else {
            editWord?.id?.let { Word(text, mean, type, it) }?.let { word ->
                viewModel.updateData(word)
            } ?: errorToast()
        }
    }

    private fun checkValue(text: String, mean: String, checkId: Int): Boolean {
        when {
            text.isEmpty() -> {
                Toast.makeText(this, getString(R.string.please_input_word), Toast.LENGTH_SHORT).show()
                return false
            }
            mean.isEmpty() -> {
                Toast.makeText(this, getString(R.string.please_input_mean), Toast.LENGTH_SHORT).show()
                return false
            }
            checkId == -1 -> {
                Toast.makeText(this, getString(R.string.please_check_type), Toast.LENGTH_SHORT).show()
                return false
            }
            else -> {
                return true
            }
        }
    }

    private fun createChip(text: String): Chip {
        return Chip(this).apply {
            setText(text)
            isCheckable = true
            isClickable = true
        }
    }

    private fun errorToast() {
        Toast.makeText(this, getString(R.string.error), Toast.LENGTH_SHORT).show()
    }
}