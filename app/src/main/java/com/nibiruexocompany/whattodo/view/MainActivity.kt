package com.nibiruexocompany.whattodo.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.nibiruexocompany.whattodo.R
import com.nibiruexocompany.whattodo.model.TodoItem
import com.nibiruexocompany.whattodo.view.adapters.TodoItemsAdapter
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var adapter: TodoItemsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        adapter = TodoItemsAdapter()
        rvTodoItems.adapter = adapter
        rvTodoItems.layoutManager = LinearLayoutManager(this)

        adapter.addItem(TodoItem("Lol", Date(2019, 11, 2)))
        adapter.addItem(TodoItem("kek", Date(2019, 11, 2)))
        adapter.addItem(TodoItem("or", Date(2019, 11, 2)))
        adapter.addItem(TodoItem("heh", Date(2019, 11, 2)))
        adapter.notifyDataSetChanged()
    }
}

