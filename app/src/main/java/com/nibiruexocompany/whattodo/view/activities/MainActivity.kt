package com.nibiruexocompany.whattodo.view.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.amitshekhar.DebugDB
import com.nibiruexocompany.whattodo.App
import com.nibiruexocompany.whattodo.R
import com.nibiruexocompany.whattodo.model.DBWriter
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

    @Inject
    lateinit var dbWriter: DBWriter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        App.daggerComponent.inject(this)

        connectAdapterToRecyclerView()

        getDataFromDB()

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

    private fun getDataFromDB() {
        val disposable = dbWriter
            .getItems()
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { list: List<TodoItem> ->
                todoItemsContainer.setItems(list)
            }
    }

    private fun onItemsChanged() {
        pbCompleted.progress =
            (todoItemsContainer.completedTasksCount().toDouble() / todoItemsContainer.totalTasksCount() * 100).toInt()
    }

    private fun connectAdapterToRecyclerView() {
        rvTodoItems.adapter = adapter
        rvTodoItems.layoutManager = LinearLayoutManager(this)
        rvTodoItems.setHasFixedSize(true)
        val itemTouchHelper = ItemTouchHelper(SwipeToDeleteCallback())
        itemTouchHelper.attachToRecyclerView(rvTodoItems)
        rvTodoItems.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    fab.hide()
                } else {
                    fab.show()
                }
            }
        })
        val disposable = adapter.itemChangeRequired
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                startEditTaskActivity(it)
            }
    }

    private fun startNewTaskActivity() {
        Log.i("MA", DebugDB.getAddressLog())
        intent = Intent(this, EditTaskActivity::class.java)
        startActivity(intent)
    }

    private fun startEditTaskActivity(todoItem: TodoItem) {
        intent = Intent(this, EditTaskActivity::class.java)
        intent.putExtra("item_id", todoItem.id)
        startActivity(intent)
    }
}
