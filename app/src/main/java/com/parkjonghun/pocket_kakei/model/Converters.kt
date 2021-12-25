package com.parkjonghun.pocket_kakei.model

import androidx.room.TypeConverter
import java.util.*

class Converters {
    @TypeConverter
    fun timeStampToDate(value: Long?): Calendar? {
        val date = value?.let { Date(it) }
        val calendar = Calendar.getInstance()
        date?.run {
            calendar.time = this
        }
        return calendar
    }

    @TypeConverter
    fun dateToTimeStamp(calendar: Calendar?): Long? {
        return calendar?.time?.time
    }
}