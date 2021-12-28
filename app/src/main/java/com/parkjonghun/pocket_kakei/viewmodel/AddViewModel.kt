package com.parkjonghun.pocket_kakei.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.parkjonghun.pocket_kakei.model.AppDatabase
import com.parkjonghun.pocket_kakei.model.Sheet
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import java.util.*

class AddViewModel(application: Application):AndroidViewModel(application) {
    @SuppressLint("StaticFieldLeak")
    private val context = getApplication<Application>().applicationContext

    val calendar = Calendar.getInstance()
    private var _moneyValue = 0
    private var _moneyValueLiveData: MutableLiveData<Int> = MutableLiveData()
    val moneyValue:LiveData<Int> = _moneyValueLiveData
    private var _currentStep = 0
    var currentStep:MutableLiveData<Int> = MutableLiveData()
    var isAdd: Boolean = false
    var description: String = ""
    private var _dataIsReady: Boolean = false
    var category: String = ""
    var dataIsReady: MutableLiveData<Boolean> = MutableLiveData()

    fun checkComma(value:Int): String {
        val formatter = DecimalFormat("###,###")
        _moneyValue = value
        _moneyValueLiveData.value = _moneyValue
        Log.d("money", "money: ${moneyValue.value}")
        return formatter.format(value)
    }

    fun checkLength(value:String): String {
        return if (value.filter { it.isDigit() }.length > 9) {
            value.drop(1)
        } else if (value.filter { it.isDigit() }.isEmpty()) {
            "0"
        } else {
            value
        }
    }

    fun nextStep() {
        _currentStep += 1
        currentStep.value = _currentStep
    }

    fun previousStep() {
        _currentStep -= 1
        currentStep.value = _currentStep
    }

    fun dataReady() {
        _dataIsReady = true
        dataIsReady.value = _dataIsReady
    }

    fun addOnDatabase() {
        val db = AppDatabase.getInstance(context)
        val newSheet = moneyValue.value?.let {
            Sheet(
                id =  calendar.time.toString() + "_" + description,
                date = calendar,
                isAdd = isAdd,
                money = it,
                category = "deposit"
            )
        }
        if (newSheet != null) {
            CoroutineScope(Dispatchers.IO).launch {
                db!!.sheetDao().insert(newSheet)
            }
        }
    }
}