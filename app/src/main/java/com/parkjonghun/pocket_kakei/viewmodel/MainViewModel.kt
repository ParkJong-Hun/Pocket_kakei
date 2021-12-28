package com.parkjonghun.pocket_kakei.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.parkjonghun.pocket_kakei.model.AppDatabase
import com.parkjonghun.pocket_kakei.model.Sheet
import com.prolificinteractive.materialcalendarview.CalendarDay
import java.text.SimpleDateFormat
import java.util.*

class MainViewModel(application: Application): AndroidViewModel(application) {
    @SuppressLint("StaticFieldLeak")
    private val context = getApplication<Application>().applicationContext

    private val db = AppDatabase.getInstance(context)

    private var _sheetsList: List<Sheet>? = null
    private val _sheets:MutableLiveData<List<Sheet>> = MutableLiveData()
    val sheets:LiveData<List<Sheet>> = _sheets

    init {
        loadSheets()
    }

    fun loadSheets() {
        _sheetsList = db?.sheetDao()?.load()
        _sheets.value = _sheetsList
    }

    fun optimizeForMonth() {

    }

    fun optimizeForWeek() {

    }

    fun optimizeForDay() {

    }

    fun calendarDayToString(calendar: CalendarDay): String {
        return SimpleDateFormat("yyyy MM dd", Locale.JAPANESE).format(calendar.date)
    }
}