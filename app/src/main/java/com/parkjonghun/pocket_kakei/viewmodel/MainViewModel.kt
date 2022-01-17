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
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*

class MainViewModel(application: Application): AndroidViewModel(application) {
    @SuppressLint("StaticFieldLeak")
    private val context = getApplication<Application>().applicationContext
    //Roomデータベース
    private val db = AppDatabase.getInstance(context)
    //シートデータ
    private var _sheetsList: List<Sheet>? = null
    private val _sheets:MutableLiveData<List<Sheet>> = MutableLiveData()
    val sheets:LiveData<List<Sheet>> = _sheets
    //選択した日
    private var _mutableSelectedDay: CalendarDay = CalendarDay.today()
    private val _selectedDay: MutableLiveData<CalendarDay> = MutableLiveData()
    val selectedDay:LiveData<CalendarDay> = _selectedDay
    //選択した月
    private var _mutableSelectedMonth: CalendarDay = CalendarDay.today()
    private val _selectedMonth: MutableLiveData<CalendarDay> = MutableLiveData()
    val selectedMonth:LiveData<CalendarDay> = _selectedMonth
    //ダブルクリック
    private var _mutableDoubleClicked:Boolean = false
    private val _doubleClicked: MutableLiveData<Boolean> = MutableLiveData()
    val doubleClicked:LiveData<Boolean> = _doubleClicked



    init {
        loadSheets()
        _selectedDay.value = _mutableSelectedDay
        _selectedMonth.value = _mutableSelectedMonth
    }


    //データベースからデータ全部を持ってくる
    fun loadSheets() {
        CoroutineScope(Dispatchers.IO).launch {
            _sheetsList = db?.sheetDao()?.load()
            _sheets.postValue(_sheetsList)
        }
    }


    //MonthFragmentのカレンダーの選択した日に合わせてデータ情報を加工
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
    //TODO: WeekFragmentのカレンダーの選択した日に合わせてデータ情報を加工
    fun optimizeForWeek() {

    }
    //DayFragmentのカレンダーの選択した日に合わせてデータ情報を加工
    suspend fun optimizeForDay(isAdd: Boolean): List<Sheet>? {
        return if(selectedDay.value != null) {
            val day: Calendar = Calendar.getInstance()
            day.set(selectedDay.value!!.calendar.get(Calendar.YEAR), selectedDay.value!!.calendar.get(Calendar.MONTH), selectedDay.value!!.calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0)
            val job = CoroutineScope(Dispatchers.IO).async {
                db?.sheetDao()?.readThatDay(day.time.toString() + "%")
            }
            job.await()?.filter { it.isAdd == isAdd }
        } else {
            null
        }
    }


    //CalendarDayをStringに変換
    fun calendarDayToString(calendar: CalendarDay): String {
        return SimpleDateFormat("yyyy MM dd", Locale.JAPANESE).format(calendar.date)
    }


    //選択した日更新
    fun selectDay(date: CalendarDay) {
        _mutableSelectedDay = date
        _selectedDay.value = _mutableSelectedDay
    }
    //選択した月更新
    fun selectMonth(date: CalendarDay) {
        _mutableSelectedMonth = date
        _selectedMonth.value = _mutableSelectedMonth
    }


    //選択した月の収入を計算
    fun getAllIncomeMoney(): Int {
        var result = 0
        sheets.value?.let {
            val thisMonthSheets = it.filter { sheet ->
                sheet.date.get(Calendar.MONTH) == selectedMonth.value?.month
            }
            for(sheet in thisMonthSheets) {
                if(sheet.isAdd) {
                    result += sheet.money
                }
            }
        }
        return result
    }
    //選択した月の支出を計算
    fun getAllExpenditureMoney(): Int {
        var result = 0
        sheets.value?.let {
            val thisMonthSheets = it.filter { sheet ->
                sheet.date.get(Calendar.MONTH) == selectedMonth.value?.month
            }
            for(sheet in thisMonthSheets) {
                if(!sheet.isAdd) {
                    result += sheet.money
                }
            }
        }
        return result
    }
    //選択した月の現金を計算
    fun getCashExpenditureMoney(): Int {
        var result = 0
        sheets.value?.let {
            val thisMonthSheets = it.filter { sheet ->
                sheet.date.get(Calendar.MONTH) == selectedMonth.value?.month
            }
            for(sheet in thisMonthSheets) {
                if(sheet.category == "cash") {
                    result += sheet.money
                }
            }
        }
        return result
    }
    //選択した月のカードを計算
    fun getCardExpenditureMoney(): Int {
        var result = 0
        sheets.value?.let {
            val thisMonthSheets = it.filter { sheet ->
                sheet.date.get(Calendar.MONTH) == selectedMonth.value?.month
            }
            for(sheet in thisMonthSheets) {
                if(sheet.category == "debitCard" || sheet.category == "creditCard") {
                    result += sheet.money
                }
            }
        }
        return result
    }
    //ダブルクリックチェック
    fun doubleClicked() {
        _mutableDoubleClicked = true
        _doubleClicked.value = _mutableDoubleClicked
    }
    fun doubleClickEventDone() {
        _mutableDoubleClicked = false
        _doubleClicked.value = _mutableDoubleClicked
    }
    //選択する日を次の日に
    fun selectNextDay() {
        _mutableSelectedDay.calendar.apply {
            add(Calendar.DATE, 1)
            _mutableSelectedDay.date.time = this.timeInMillis
        }
        _selectedDay.value = _mutableSelectedDay
    }
    //選択する日を前の日に
    fun selectPreviousDay() {
        _mutableSelectedDay.calendar.apply {
            add(Calendar.DATE, -1)
            _mutableSelectedDay.date.time = this.timeInMillis
        }
        _selectedDay.value = _mutableSelectedDay
    }
    //一週間の日を更新
    fun loadOneWeekDayOfMonth(): List<Int> {
        val currentWeek:MutableList<Int> = mutableListOf(_mutableSelectedDay.calendar.get(Calendar.DAY_OF_MONTH))
        val over = 7 - _mutableSelectedDay.calendar.get(Calendar.DAY_OF_WEEK)
        val under = _mutableSelectedDay.calendar.get(Calendar.DAY_OF_WEEK) - 1
        val tempUnder: Calendar = _mutableSelectedDay.calendar.clone() as Calendar
        for (i in 1..under) {
            tempUnder.add(Calendar.DATE, -1)
            currentWeek.add(0, tempUnder.get(Calendar.DAY_OF_MONTH))
        }
        val tempOver: Calendar = _mutableSelectedDay.calendar.clone() as Calendar
        for (i in 1..over) {
            tempOver.add(Calendar.DATE, 1)
            currentWeek.add(tempOver.get(Calendar.DAY_OF_MONTH))
        }
        return currentWeek
    }
    //選択した日の収入を計算
    fun getSelectedDayIncomeMoney(): Int {
        var result = 0
        sheets.value?.let {
            val thisDaySheets = it.filter { sheet ->
                sheet.date.get(Calendar.DATE) == selectedDay.value?.calendar?.get(Calendar.DATE)
            }
            for(sheet in thisDaySheets) {
                if(sheet.isAdd) {
                    result += sheet.money
                }
            }
        }
        return result
    }
    //選択した日の支出を計算
    fun getSelectedDayExpenditureMoney(): Int {
        var result = 0
        sheets.value?.let {
            val thisDaySheets = it.filter { sheet ->
                sheet.date.get(Calendar.DATE) == selectedDay.value?.calendar?.get(Calendar.DATE)
            }
            for(sheet in thisDaySheets) {
                if(!sheet.isAdd) {
                    result += sheet.money
                }
            }
        }
        return result
    }
    //選択した日の現金を計算
    fun getSelectedDayCashExpenditureMoney(): Int {
        var result = 0
        sheets.value?.let {
            val thisMonthSheets = it.filter { sheet ->
                sheet.date.get(Calendar.DATE) == selectedDay.value?.calendar?.get(Calendar.DATE)
            }
            for(sheet in thisMonthSheets) {
                if(sheet.category == "cash") {
                    result += sheet.money
                }
            }
        }
        return result
    }
    //選択した日のカードを計算
    fun getSelectedDayCardExpenditureMoney(): Int {
        var result = 0
        sheets.value?.let {
            val thisDaySheets = it.filter { sheet ->
                sheet.date.get(Calendar.DATE) == selectedDay.value?.calendar?.get(Calendar.DATE)
            }
            for(sheet in thisDaySheets) {
                if(sheet.category == "debitCard" || sheet.category == "creditCard") {
                    result += sheet.money
                }
            }
        }
        return result
    }
    //選択した日が含めてる1週間のカレンダーを返す
    fun loadOneWeekCalendar(): List<Calendar> {
        val day = _mutableSelectedDay.calendar
        val result:MutableList<Calendar> = mutableListOf(day)
        val over = 7 - _mutableSelectedDay.calendar.get(Calendar.DAY_OF_WEEK)
        val under = _mutableSelectedDay.calendar.get(Calendar.DAY_OF_WEEK) - 1
        for (i in 1..under) {
            val tempUnder: Calendar = _mutableSelectedDay.calendar.clone() as Calendar
            tempUnder.add(Calendar.DATE, -i)
            result.add(0, tempUnder)
        }
        for (i in 1..over) {
            val tempOver: Calendar = _mutableSelectedDay.calendar.clone() as Calendar
            tempOver.add(Calendar.DATE, i)
            result.add(tempOver)
        }
        return result
    }
    //選択する日を次の日に
    fun selectNextMonth() {
        _mutableSelectedMonth.calendar.apply {
            add(Calendar.MONTH, 1)
            _mutableSelectedMonth.date.time = this.timeInMillis
        }
        _selectedMonth.value = _mutableSelectedMonth
    }
    //選択する日を前の日に
    fun selectPreviousMonth() {
        _mutableSelectedMonth.calendar.apply {
            add(Calendar.MONTH, -1)
            _mutableSelectedMonth.date.time = this.timeInMillis
        }
        _selectedMonth.value = _mutableSelectedMonth
    }
    //選択した月の該当週に含まれる日全部を返す
    fun getWeekOnMonth(weekCount: Int):List<Calendar> {
        val result: MutableList<Calendar> = mutableListOf()
        var plusDay = 0
        do {
            val temp = _mutableSelectedMonth.calendar.clone() as Calendar
            temp.set(temp.get(Calendar.YEAR), temp.get(Calendar.MONTH), 1, 0, 0, 0)
            temp.add(Calendar.DATE, plusDay)
            plusDay += 1
            if (temp.get(Calendar.WEEK_OF_MONTH) == weekCount && temp.get(Calendar.MONTH) == _mutableSelectedMonth.calendar.get(Calendar.MONTH)) {
                result.add(temp)
            }
        } while (temp.get(Calendar.MONTH) == _mutableSelectedMonth.calendar.get(Calendar.MONTH))
        return result
    }
}