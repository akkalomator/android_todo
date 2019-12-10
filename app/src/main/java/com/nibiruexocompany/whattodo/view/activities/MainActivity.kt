package com.nibiruexocompany.whattodo.view.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.nibiruexocompany.whattodo.R
import com.nibiruexocompany.whattodo.view.utils.SwipeToDeleteCallback
import com.nibiruexocompany.whattodo.view.utils.TodoItemsAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var adapter: TodoItemsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        adapter = TodoItemsAdapter()
        rvTodoItems.adapter = adapter
        rvTodoItems.layoutManager = LinearLayoutManager(this)
        val itemTouchHelper = ItemTouchHelper(SwipeToDeleteCallback())
        itemTouchHelper.attachToRecyclerView(rvTodoItems)

        fab.setOnClickListener {
            startNewTaskActivity()
        }
    }

    private fun startNewTaskActivity() {
        intent = Intent(this, EditTaskActivity::class.java)
        startActivity(intent)
    }
}
