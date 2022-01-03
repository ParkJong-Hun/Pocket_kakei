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
import kotlinx.coroutines.async
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

    private var _mutableSelectedDay: CalendarDay? = CalendarDay.today()
    private val _selectedDay: MutableLiveData<CalendarDay> = MutableLiveData()
    val selectedDay:LiveData<CalendarDay> = _selectedDay

    private var _mutableSelectedMonth: CalendarDay = CalendarDay.today()
    private val _selectedMonth: MutableLiveData<CalendarDay> = MutableLiveData()
    val selectedMonth:LiveData<CalendarDay> = _selectedMonth

    init {
        loadSheets()
        _selectedDay.value = _mutableSelectedDay
        _selectedMonth.value = _mutableSelectedMonth
    }

    fun loadSheets() {
        CoroutineScope(Dispatchers.IO).launch {
            _sheetsList = db?.sheetDao()?.load()
            _sheets.postValue(_sheetsList)
        }
    }

    suspend fun optimizeForMonth(): List<Sheet>? {
        return if(selectedDay.value != null) {
            val day: Calendar = Calendar.getInstance()
            day.set(selectedDay.value!!.year, selectedDay.value!!.month, selectedDay.value!!.day, 0, 0, 0)
            val job = CoroutineScope(Dispatchers.IO).async {
                db?.sheetDao()?.readThatDay(day.time.toString() + "%")
            }
            job.await()
        } else {
            null
        }
    }

    fun optimizeForWeek() {

    }

    fun optimizeForDay() {

    }

    fun calendarDayToString(calendar: CalendarDay): String {
        return SimpleDateFormat("yyyy MM dd", Locale.JAPANESE).format(calendar.date)
    }

    fun selectDay(date: CalendarDay) {
        _mutableSelectedDay = date
        _selectedDay.value = _mutableSelectedDay
    }

    fun selectMonth(date: CalendarDay) {
        _mutableSelectedMonth = date
        _selectedMonth.value = _mutableSelectedMonth
        Log.d("", selectedMonth.value.toString())
    }

    fun getAllDepositMoney(): Int {
        var result = 0
        sheets.value?.let {
            Log.d("1", it.toString())
            val thisMonthSheets = it.filter { sheet ->
                sheet.date.get(Calendar.MONTH) == selectedMonth.value?.month
            }
            Log.d("2", thisMonthSheets.toString())
            for(sheet in thisMonthSheets) {
                if(sheet.category == "deposit") {
                    result += sheet.money
                }
            }
        }
        return result
    }
}