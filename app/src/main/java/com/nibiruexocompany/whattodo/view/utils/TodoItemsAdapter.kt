package com.nibiruexocompany.whattodo.view.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nibiruexocompany.whattodo.App
import com.nibiruexocompany.whattodo.R
import com.nibiruexocompany.whattodo.model.TodoItem
import com.nibiruexocompany.whattodo.model.TodoItemsContainer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*
import kotlin.collections.ArrayList
import javax.inject.Inject

class TodoItemsAdapter : RecyclerView.Adapter<TodoItemsAdapter.ItemTaskViewHolder>() {
    inner class ItemTaskViewHolder(private val context: Context, parent: ViewGroup) :
        RecyclerView.ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_todo,
                parent,
                false
            )
        ) {
        private var cbIsDone: CheckBox
        private var tvTask: TextView
        private var tvDate: TextView

        lateinit var item: TodoItem

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
            val startDate = item.startDate
            tvDate.text =
                """${startDate!!.get(Calendar.HOUR_OF_DAY)}:${startDate!!.get(Calendar.MINUTE)}
                    ${startDate!!.get(Calendar.DAY_OF_MONTH)}.${startDate!!.get(Calendar.MONTH) + 1}}"""
        }
    }

    @Inject
    lateinit var todoItemsContainer: TodoItemsContainer

    private var todos: MutableList<TodoItem> = ArrayList()

    init {
        App.daggerComponent.inject(this)
        val disposable = todoItemsContainer
            .itemsSource
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                todos.add(it)
                notifyDataSetChanged()
            }
        val disposable2 = todoItemsContainer
            .itemChanged
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { changedItem ->
                val index = todos.indexOfFirst { it.id == changedItem.id }
                todos[index] = changedItem
                notifyDataSetChanged()
            }
        val disposable3 = todoItemsContainer
            .itemDeleted
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                todos.remove(it)
                notifyDataSetChanged()
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemTaskViewHolder {
        val context = parent.context
        return ItemTaskViewHolder(context, parent)
    }

    override fun getItemCount(): Int = todos.size

    override fun onBindViewHolder(holderItemTask: ItemTaskViewHolder, position: Int) {
        holderItemTask.setState(todos[position])
    }
}

