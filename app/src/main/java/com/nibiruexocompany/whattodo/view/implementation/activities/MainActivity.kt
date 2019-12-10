package com.nibiruexocompany.whattodo.view.implementation.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.nibiruexocompany.whattodo.App
import com.nibiruexocompany.whattodo.R
import com.nibiruexocompany.whattodo.model.TodoItem
import com.nibiruexocompany.whattodo.model.TodoItemsContainer
import com.nibiruexocompany.whattodo.view.adapters.TodoItemsAdapter
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    lateinit var adapter: TodoItemsAdapter

    @Inject
    lateinit var items: TodoItemsContainer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        adapter = TodoItemsAdapter()
        rvTodoItems.adapter = adapter
        rvTodoItems.layoutManager = LinearLayoutManager(this)

        App.daggerComponent.inject(this)

        var i = 0
        fab.setOnClickListener {
            startNewTaskActivity()
        }
    }

    private fun startNewTaskActivity() {
        intent = Intent()

    }
}
