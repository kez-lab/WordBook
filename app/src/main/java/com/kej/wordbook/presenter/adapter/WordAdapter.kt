package com.kej.wordbook.presenter.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kej.wordbook.data.model.Word
import com.kej.wordbook.databinding.ItemWordBinding

class WordAdapter(private val onClick: (Word) -> Unit): ListAdapter<Word, WordAdapter.WordViewHolder>(diffUtil) {
    companion object {
        val diffUtil = object: DiffUtil.ItemCallback<Word>() {
            override fun areItemsTheSame(oldItem: Word, newItem: Word): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Word, newItem: Word): Boolean {
                return oldItem.text == newItem.text
            }

        }
    }

    inner class WordViewHolder(private val binding: ItemWordBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(word: Word){
            binding.apply {
                textTextView.text = word.text
                meanTextTextView.text = word.mean
                typeChip.text = word.type
            }
            itemView.setOnClickListener {
                onClick(word)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val binding = ItemWordBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WordViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
}