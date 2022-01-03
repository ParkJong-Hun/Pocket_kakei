package com.parkjonghun.pocket_kakei.model

import androidx.room.TypeConverter
import java.util.*

//CalendarをLongに
class Converters {
    @TypeConverter
    fun longToCalendar(value: Long?): Calendar? {
        val date = value?.let { Date(it) }
        val calendar = Calendar.getInstance()
        date?.run {
            calendar.time = this
        }
        return calendar
    }

    @TypeConverter
    fun calendarToLong(calendar: Calendar?): Long? {
        return calendar?.time?.time
    }
}