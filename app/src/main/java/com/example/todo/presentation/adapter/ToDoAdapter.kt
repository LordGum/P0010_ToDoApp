package com.example.todo.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.todo.R
import com.example.todo.domain.entities.ToDoItem

class ToDoAdapter: ListAdapter<ToDoItem, ToDoViewHolder>(ToDoDiffCallback()) {
    var onItemLongClickListener: ((ToDoItem) -> Unit)? = null
    var onItemClickListener: ((ToDoItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoViewHolder {
        val layout = when(viewType) {
            VIEW_TYPE_DISABLED -> R.layout.item_to_do_disable
            VIEW_TYPE_ENABLED -> R.layout.item_to_do_enabled
            else -> throw RuntimeException("Unknown view type: $viewType")
        }
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return ToDoViewHolder(view)
    }

    override fun onBindViewHolder(holder: ToDoViewHolder, position: Int) {
        val item = getItem(position)
        holder.view.setOnClickListener {
            onItemLongClickListener?.invoke(item)
        }
        holder.view.setOnClickListener {
            onItemClickListener?.invoke(item)
        }

        holder.tvName.text = item.name
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return if(item.done) {
            VIEW_TYPE_DISABLED
        } else VIEW_TYPE_ENABLED
    }

    companion object{
        const val VIEW_TYPE_ENABLED = 1
        const val VIEW_TYPE_DISABLED = 0
        const val MAX_POOl_SIZE = 30
    }

}