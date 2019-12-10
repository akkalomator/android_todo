package com.nibiruexocompany.whattodo.view.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nibiruexocompany.whattodo.App
import com.nibiruexocompany.whattodo.R
import com.nibiruexocompany.whattodo.model.TodoItem
import com.nibiruexocompany.whattodo.model.TodoItemsContainer
import kotlin.collections.ArrayList
import javax.inject.Inject

class TodoItemsAdapter : RecyclerView.Adapter<TodoItemsAdapter.ViewHolder>() {
    inner class ViewHolder(private val context: Context, parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(context).inflate(
            R.layout.item_todo,
            parent,
            false
        )
    ) {
        private var cbIsDone: CheckBox
        private var tvTask: TextView
        private var tvDate: TextView

        private lateinit var item: TodoItem

        init {
            val view = itemView
            cbIsDone = view.findViewById(R.id.cbIsDone)
            tvTask = view.findViewById(R.id.tvTodoTask)
            tvDate = view.findViewById(R.id.tvDate)

            cbIsDone.setOnCheckedChangeListener { _, isChecked ->
                if (item.isDone != isChecked) {
                    item.isDone = isChecked
                }
            }
        }

        fun setState(item: TodoItem) {
            this.item = item
            cbIsDone.isChecked = item.isDone
            if (item.isDone) {
                itemView.setBackgroundColor(context.getColor(R.color.colorPrimaryDark))
            } else {
                itemView.setBackgroundColor(context.getColor(R.color.colorPrimary))
            }
            tvTask.text = item.content
            tvDate.text = item.startDate.toString()
        }
    }

    @Inject
    lateinit var items: TodoItemsContainer

    private var todos: List<TodoItem> = ArrayList()

    private val TAG = "TodoItemsAdapter"

    init {
        App.daggerComponent.inject(this)
        val disposable = items.itemsDistributor.subscribe({
            todos = it
            notifyDataSetChanged()
        }, {
            Log.e(TAG, it.localizedMessage!!)
        })

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        return ViewHolder(context, parent)
    }

    override fun getItemCount(): Int = todos.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setState(todos[position])
    }
}