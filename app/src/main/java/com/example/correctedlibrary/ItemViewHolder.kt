package com.example.correctedlibrary

import androidx.recyclerview.widget.RecyclerView
import com.example.correctedlibrary.databinding.LibraryItemBinding

class ItemViewHolder(private val binding: LibraryItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Item) = with(binding)
    {
        item.imageRes?.let {
            itemImage.setImageResource(it)
        } ?: itemImage.setImageResource(R.drawable.book_image)
        binding.itemName.text = item.itemName
        binding.itemId.text = "ID: ${item.itemId}"
        with(binding) {
            if (item.isAvailable == false) {

                itemId.alpha = 0.3f
                itemName.alpha = 0.3f
                cardView.elevation = 1f
            } else {
                itemId.alpha = 1f
                itemName.alpha=1f
                cardView.elevation = 10f
            }

        }


        }

    }


