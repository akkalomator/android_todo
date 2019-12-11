package com.nibiruexocompany.whattodo.room

import androidx.room.TypeConverter
import java.util.*

object CalendarConverter {

    @TypeConverter
    @JvmStatic
    fun fromCalendar(calendar: Calendar?) : Long? {
        return calendar?.time?.time
    }

    @TypeConverter
    @JvmStatic
    fun toCalendar(long: Long?) : Calendar? {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = long ?: return null
        return calendar
    }
}