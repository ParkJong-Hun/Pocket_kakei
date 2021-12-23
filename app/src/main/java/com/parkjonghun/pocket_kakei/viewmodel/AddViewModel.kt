package com.parkjonghun.pocket_kakei.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.text.DecimalFormat

class AddViewModel:ViewModel() {
    private var _moneyValue = 0
    private var _moneyValueLiveData: MutableLiveData<Int> = MutableLiveData()
    val moneyValue:LiveData<Int> = _moneyValueLiveData
    private var _currentStep = 0
    var currentStep:MutableLiveData<Int> = MutableLiveData()

    init {
        _currentStep = 0
    }

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
}