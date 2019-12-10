package com.nibiruexocompany.whattodo.view.activities

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.amitshekhar.DebugDB
import com.nibiruexocompany.whattodo.App
import com.nibiruexocompany.whattodo.R
import com.nibiruexocompany.whattodo.model.TodoItem
import com.nibiruexocompany.whattodo.model.TodoItemsContainer
import com.nibiruexocompany.whattodo.viewmodels.activities.EditTaskActivityViewModel
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_edit_task.*
import java.util.*
import javax.inject.Inject

class EditTaskActivity : AppCompatActivity() {

    private lateinit var viewModel: EditTaskActivityViewModel

    @Inject
    lateinit var todoItemsContainer: TodoItemsContainer

    private var item: TodoItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_task)

        App.daggerComponent.inject(this)

        initiateViewModel()

        bindViewsListeners()

        if (intent.extras?.containsKey("item_id") == true) {
            restoreData()
        }
    }

    private fun restoreData() {
        val itemId = intent.getIntExtra("item_id", 0)
        item = todoItemsContainer.get(itemId)!!
        viewModel.taskContent.value = item!!.content
        viewModel.startDate.value = item!!.startDate
        viewModel.endDate.value = item!!.endDate
        etContent.setText(item!!.content)
    }

    private fun bindViewsListeners() {
        etStartDay.setOnClickListener {
            onStartDateRequired()
        }
        etStartMonth.setOnClickListener {
            onStartDateRequired()
        }
        etStartYear.setOnClickListener {
            onStartDateRequired()
        }

        etFinishDay.setOnClickListener {
            onEndDateRequired()
        }
        etFinishMonth.setOnClickListener {
            onEndDateRequired()
        }
        etFinishYear.setOnClickListener {
            onEndDateRequired()
        }

        etStartHours.setOnClickListener {
            onStartTimeRequired()
        }
        etStartMinutes.setOnClickListener {
            onStartTimeRequired()
        }

        etFinishHours.setOnClickListener {
            onFinishTimeRequired()
        }
        etFinishMinutes.setOnClickListener {
            onFinishTimeRequired()
        }

        bCancel.setOnClickListener { finish() }
        bSave.setOnClickListener {
            saveTodo()
            finish()
        }

        etContent.doAfterTextChanged {
            viewModel.taskContent.value = etContent.text.toString()
        }
    }

    private fun initiateViewModel() {
        viewModel = ViewModelProviders.of(this).get(EditTaskActivityViewModel::class.java)
        viewModel.startDate.observe(
            this,
            Observer {
                etStartYear.setText(it.get(Calendar.YEAR).toString())
                etStartMonth.setText((it.get(Calendar.MONTH) + 1).toString())
                etStartDay.setText(it.get(Calendar.DAY_OF_MONTH).toString())
                etStartHours.setText(it.get(Calendar.HOUR_OF_DAY).toString())
                etStartMinutes.setText(it.get(Calendar.MINUTE).toString())
            }
        )
        viewModel.endDate.observe(
            this,
            Observer {
                etFinishYear.setText(it.get(Calendar.YEAR).toString())
                etFinishMonth.setText((it.get(Calendar.MONTH) + 1).toString())
                etFinishDay.setText(it.get(Calendar.DAY_OF_MONTH).toString())
                etFinishHours.setText(it.get(Calendar.HOUR_OF_DAY).toString())
                etFinishMinutes.setText(it.get(Calendar.MINUTE).toString())
            }
        )
    }

    private fun onStartDateRequired() {
        val subject = PublishSubject.create<Calendar>()
        val disposable = subject.subscribe {
            val newInfo = viewModel.startDate.value!!
            newInfo.set(Calendar.DAY_OF_YEAR, it.get(Calendar.DAY_OF_YEAR))
            newInfo.set(Calendar.MONTH, it.get(Calendar.MONTH))
            newInfo.set(Calendar.YEAR, it.get(Calendar.YEAR))
            viewModel.startDate.value = newInfo
        }
        sendDateRequestToUser(viewModel.startDate.value!!, subject)
    }

    private fun onStartTimeRequired() {
        val subject = PublishSubject.create<Calendar>()
        val disposable = subject.subscribe {
            val newInfo = viewModel.startDate.value!!
            newInfo.set(Calendar.HOUR_OF_DAY, it.get(Calendar.HOUR_OF_DAY))
            newInfo.set(Calendar.MINUTE, it.get(Calendar.MINUTE))
            viewModel.startDate.value = newInfo
        }
        sendTimeRequestToUser(viewModel.startDate.value!!, subject)
    }

    private fun onEndDateRequired() {
        val subject = PublishSubject.create<Calendar>()
        val disposable = subject.subscribe {
            val newInfo = viewModel.endDate.value!!
            newInfo.set(Calendar.DAY_OF_YEAR, it.get(Calendar.DAY_OF_YEAR))
            newInfo.set(Calendar.MONTH, it.get(Calendar.MONTH))
            newInfo.set(Calendar.YEAR, it.get(Calendar.YEAR))
            viewModel.endDate.value = newInfo
        }
        sendDateRequestToUser(viewModel.endDate.value!!, subject)
    }

    private fun onFinishTimeRequired() {
        val subject = PublishSubject.create<Calendar>()
        val disposable = subject.subscribe {
            val newInfo = viewModel.endDate.value!!
            newInfo.set(Calendar.HOUR_OF_DAY, it.get(Calendar.HOUR_OF_DAY))
            newInfo.set(Calendar.MINUTE, it.get(Calendar.MINUTE))
            viewModel.endDate.value = newInfo
        }
        sendTimeRequestToUser(viewModel.endDate.value!!, subject)
    }

    private fun sendTimeRequestToUser(fromTime: Calendar, stream: PublishSubject<Calendar>) {
        val hour = fromTime.get(Calendar.HOUR_OF_DAY)
        val timePickerDialog = TimePickerDialog(
            this, TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                val response = Calendar.getInstance()
                response.set(Calendar.HOUR_OF_DAY, hourOfDay)
                response.set(Calendar.MINUTE, minute)
                stream.onNext(response)
            },
            hour + 1, 0, true
        )
        timePickerDialog.show()
    }

    private fun sendDateRequestToUser(fromDate: Calendar, stream: PublishSubject<Calendar>) {
        val year = fromDate.get(Calendar.YEAR)
        val month = fromDate.get(Calendar.MONTH)
        val day = fromDate.get(Calendar.DAY_OF_MONTH)
        val datePickerDialog = DatePickerDialog(
            this,
            OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                val response = Calendar.getInstance()
                response.set(
                    year,
                    monthOfYear,
                    dayOfMonth
                )
                stream.onNext(response)
            },
            year, month, day
        )
        datePickerDialog.show()
    }

    private fun saveTodo() {
        if (item != null) {
            item!!.content = viewModel.taskContent.value!!
            item!!.startDate = viewModel.startDate.value
            item!!.endDate = viewModel.endDate.value
            todoItemsContainer.replaceItem(item!!)
            return
        }
        val item = TodoItem(
            content = viewModel.taskContent.value!!,
            startDate = viewModel.startDate.value,
            endDate = viewModel.endDate.value
        )
        todoItemsContainer.addItem(item)
    }
}
