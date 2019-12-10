package com.nibiruexocompany.whattodo.view.implementation.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.nibiruexocompany.whattodo.App
import com.nibiruexocompany.whattodo.R
import com.nibiruexocompany.whattodo.model.TodoItem
import com.nibiruexocompany.whattodo.view.adapters.TodoItemsAdapter
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    lateinit var adapter: TodoItemsAdapter

    @Inject
    lateinit var items: PublishSubject<TodoItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        adapter = TodoItemsAdapter()
        rvTodoItems.adapter = adapter
        rvTodoItems.layoutManager = LinearLayoutManager(this)

        App.daggerComponent.inject(this)
        items.onNext(TodoItem("Lol", Date(2019, 11, 2)))
        items.onNext(TodoItem("Lol", Date(2019, 11, 2)))

        var i = 0
        fab.setOnClickListener {
            items.onNext(TodoItem("$i", Date(2018, 11, 2 + i++)))
        }
    }
}
