package com.nibiruexocompany.whattodo.viewmodels.activities

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*

class EditTaskActivityViewModel : ViewModel() {
    val taskContent by lazy {
        val mld = MutableLiveData<String>()
        mld.value = ""
        mld
    }

    val startDate by lazy {
        val calendar = GregorianCalendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + 1)
        calendar.set(Calendar.MINUTE, 0)
        val mld = MutableLiveData<Calendar>()
        mld.value = calendar
        mld
    }
    val endDate: MutableLiveData<Calendar?> by lazy {
        MutableLiveData<Calendar?>()
    }
}