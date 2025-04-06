package com.example.correctedlibrary

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.correctedlibrary.databinding.LibraryItemBinding
import javax.xml.transform.ErrorListener


class ItemAdapter : RecyclerView.Adapter<ItemViewHolder>() {
    private val data = mutableListOf<Item>()
    private var onItemClickListener: ((Int) -> Unit)? = null
    fun <T : Item> setNewData(newData: List<out T>) {
        val diffUtil = ItemDiffUtil(data, newData)
        DiffUtil.calculateDiff(diffUtil).dispatchUpdatesTo(this)
        data.clear()
        data.addAll(newData)

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemViewHolder {
        val binding = LibraryItemBinding.inflate(LayoutInflater.from(parent.context))
        return ItemViewHolder(binding).apply {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClickListener?.invoke(position)
                }
            }
        }
    }


    override fun onBindViewHolder(
        holder: ItemViewHolder,
        position: Int
    ) {
        holder.bind(data[position])
    }

    fun setOnClickListener(listener: (Int) -> Unit) {
        onItemClickListener = listener
    }

    override fun getItemCount() = data.size
}