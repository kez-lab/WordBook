package com.kej.wordbook.presenter.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kej.wordbook.databinding.ItemWordBinding
import com.kej.wordbook.domain.model.WordModel

class WordAdapter(private val onClick: (WordModel) -> Unit): PagingDataAdapter<WordModel, WordAdapter.WordViewHolder>(diffUtil) {
    companion object {
        val diffUtil = object: DiffUtil.ItemCallback<WordModel>() {
            override fun areItemsTheSame(oldItem: WordModel, newItem: WordModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: WordModel, newItem: WordModel): Boolean {
                return oldItem.text == newItem.text
            }

        }
    }

    inner class WordViewHolder(private val binding: ItemWordBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(word: WordModel){
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
        val item = getItem(position)?: return
        holder.bind(item)
    }
}