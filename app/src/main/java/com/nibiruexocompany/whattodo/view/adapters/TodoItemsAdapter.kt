package com.nibiruexocompany.whattodo.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nibiruexocompany.whattodo.R
import com.nibiruexocompany.whattodo.model.TodoItem
import java.util.*
import kotlin.collections.ArrayList

class TodoItemsAdapter : RecyclerView.Adapter<TodoItemsAdapter.ViewHolder>() {
    class ViewHolder(context: Context, parent: ViewGroup) : RecyclerView.ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_todo, parent, false)) {
        private var tvTask: TextView
        private var tvDate: TextView

        init {
            val view = itemView
            tvTask = view.findViewById(R.id.tvTodoTask)
            tvDate = view.findViewById(R.id.tvDate)
        }

        fun setTask(content: String) {
            tvTask.text = content
        }

        fun setDate(date: Date) {
            tvDate.text = date.toString()
        }
    }

    private val todos = ArrayList<TodoItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        return ViewHolder(context, parent)
    }

    override fun getItemCount(): Int = todos.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setTask(todos[position].content)
        holder.setDate(todos[position].date)
    }

    fun addItem(item: TodoItem) {
        todos.add(item)
    }
}