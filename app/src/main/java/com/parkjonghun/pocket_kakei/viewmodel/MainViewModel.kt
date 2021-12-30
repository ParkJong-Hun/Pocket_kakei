package com.parkjonghun.pocket_kakei.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.parkjonghun.pocket_kakei.model.AppDatabase
import com.parkjonghun.pocket_kakei.model.Sheet
import com.prolificinteractive.materialcalendarview.CalendarDay
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class MainViewModel(application: Application): AndroidViewModel(application) {
    @SuppressLint("StaticFieldLeak")
    private val context = getApplication<Application>().applicationContext

    private val db = AppDatabase.getInstance(context)

    private var _sheetsList: List<Sheet>? = null
    private val _sheets:MutableLiveData<List<Sheet>> = MutableLiveData()
    val sheets:LiveData<List<Sheet>> = _sheets

    private var _selectedDayList: CalendarDay? = CalendarDay.today()
    private val _selectedDay: MutableLiveData<CalendarDay> = MutableLiveData()
    val selectedDay:LiveData<CalendarDay> = _selectedDay

    init {
        Log.d("초기화됨", "초기화됨")
        loadSheets()
    }

    fun loadSheets() {
        CoroutineScope(Dispatchers.IO).launch {
            _sheetsList = db?.sheetDao()?.load()
            _sheets.postValue(_sheetsList)
        }
    }

    fun optimizeForMonth(): List<Sheet>? {
        return null
    }

    fun optimizeForWeek() {

    }

    fun optimizeForDay() {

    }

    fun calendarDayToString(calendar: CalendarDay): String {
        return SimpleDateFormat("yyyy MM dd", Locale.JAPANESE).format(calendar.date)
    }

    fun selectDay(date: CalendarDay) {
        _selectedDayList = date
        _selectedDay.value = _selectedDayList
    }
}