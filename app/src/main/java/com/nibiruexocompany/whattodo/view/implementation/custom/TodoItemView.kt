package com.nibiruexocompany.whattodo.view.implementation.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.nibiruexocompany.whattodo.R
import java.util.*

class TodoItemView(context: Context, attr: AttributeSet? = null, defStyle: Int = 0, parent: ViewGroup?) : ConstraintLayout(context, attr, defStyle) {

    constructor (context: Context, attr: AttributeSet? = null, defStyle: Int = 0) : this(context, attr, defStyle, null)

    private val view: View = LayoutInflater.from(context).inflate(R.layout.item_todo, parent, false)
    private val tvTodoTask = view.findViewById<TextView>(R.id.tvTodoTask)
    private val tvDate = view.findViewById<TextView>(R.id.tvDate)


    fun setTask(content: String) {
        tvTodoTask.text = content
    }

    fun setDate(date: Date) {
        tvDate.text = date.toString()
    }
}