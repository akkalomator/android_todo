package com.nibiruexocompany.whattodo.view.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.nibiruexocompany.whattodo.App
import com.nibiruexocompany.whattodo.R
import com.nibiruexocompany.whattodo.model.TodoItem
import com.nibiruexocompany.whattodo.model.TodoItemsContainer
import com.nibiruexocompany.whattodo.view.utils.SwipeToDeleteCallback
import com.nibiruexocompany.whattodo.view.utils.TodoItemsAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var adapter: TodoItemsAdapter

    @Inject
    lateinit var todoItemsContainer: TodoItemsContainer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        App.daggerComponent.inject(this)

        connectAdapterToRecyclerView()

        fab.setOnClickListener {
            startNewTaskActivity()
        }

        val disposable = todoItemsContainer
            .itemsSource
            .mergeWith(todoItemsContainer.itemDeleted)
            .mergeWith(todoItemsContainer.itemChanged)
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                onItemsChanged()
            }
    }

    private fun onItemsChanged() {
        pbCompleted.progress =
            (todoItemsContainer.completedTasksCount().toDouble() / todoItemsContainer.totalTasksCount() * 100).toInt()
    }

    private fun connectAdapterToRecyclerView() {
        rvTodoItems.adapter = adapter
        rvTodoItems.layoutManager = LinearLayoutManager(this)
        val itemTouchHelper = ItemTouchHelper(SwipeToDeleteCallback())
        itemTouchHelper.attachToRecyclerView(rvTodoItems)
        val disposable = adapter.itemChangeRequired
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                startEditTaskActivity(it)
            }
    }

    private fun startNewTaskActivity() {
        intent = Intent(this, EditTaskActivity::class.java)
        startActivity(intent)
    }

    private fun startEditTaskActivity(todoItem: TodoItem) {
        intent = Intent(this, EditTaskActivity::class.java)
        intent.putExtra("item_id", todoItem.id)
        startActivity(intent)
    }
}
