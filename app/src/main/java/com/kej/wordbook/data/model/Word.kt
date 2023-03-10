package com.kej.wordbook.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kej.wordbook.domain.model.WordModel
import kotlinx.parcelize.Parcelize

@Entity(tableName = "word")
data class Word(
    val text: String,
    val mean: String,
    val type: String,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
)

fun Word.toWordModel() = WordModel(
    id, text, mean, type
)

fun WordModel.toWord() = Word(
    text, mean, type, id
)
