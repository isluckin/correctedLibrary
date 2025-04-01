package com.example.correctedlibrary

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.correctedlibrary.databinding.LibraryItemBinding


class ItemAdapter : RecyclerView.Adapter<ItemViewHolder>() {
    private val data = mutableListOf<Item>()
    fun setNewData(newData: List<Item>)
    {
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
            binding.root.setOnClickListener{
                handleItemClick(parent.context, adapterPosition)
        }
        }
    }

    private  fun  handleItemClick(context: Context, position: Int)
    {
        if(position!= RecyclerView.NO_POSITION)
        {
            val item = data[position]
            Toast.makeText(context, "Element with id# ${item.itemId}", Toast.LENGTH_SHORT ).show()
            item.isAvailable =!item.isAvailable
            notifyItemChanged(position)
        }
    }
    override fun onBindViewHolder(
        holder: ItemViewHolder,
        position: Int
    ) {
        holder.bind(data[position])
    }

    override fun getItemCount()=    data.size
}