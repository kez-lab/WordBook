package com.kej.wordbook.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WordModel(
    val id: Int,
    val text: String,
    val mean: String,
    val type: String
) : Parcelable