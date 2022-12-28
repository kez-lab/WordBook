package com.kej.wordbook

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kej.wordbook.database.Word
import com.kej.wordbook.databinding.ItemWordBinding

class WordAdapter(var list: MutableList<Word>, private val onClick: (Word) -> Unit): RecyclerView.Adapter<WordAdapter.WordViewHolder>() {

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
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size
}