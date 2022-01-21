package com.parkjonghun.pocket_kakei.view.activity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.parkjonghun.pocket_kakei.databinding.ActivitySheetBinding
import com.parkjonghun.pocket_kakei.model.Sheet
import com.parkjonghun.pocket_kakei.viewmodel.SheetViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

@SuppressLint("SetTextI18n")
class SheetActivity: AppCompatActivity() {

    private lateinit var binding: ActivitySheetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivitySheetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: SheetViewModel by viewModels()

        val sheet = intent.getSerializableExtra("sheet") as Sheet
        binding.sheetDescription.text = sheet.description
        binding.sheetCategoryValue.text = sheet.category
        binding.sheetMoneyValue.setText(sheet.money.toString())
        binding.sheetTodayTextView.text = "${sheet.date.get(Calendar.YEAR)}年 ${sheet.date.get(Calendar.MONTH)}月 ${sheet.date.get(Calendar.DAY_OF_MONTH)}日"
        //バックボタンをクリックしたら
        binding.sheetBackButton.setOnClickListener{
            setResult(RESULT_CANCELED)
            finish()
        }
        //削除ボタンをクリックしたら
        binding.sheetDeleteButton.setOnClickListener{
            val job = CoroutineScope(Dispatchers.IO).launch {
                viewModel.deleteSheet(sheet)
            }
            setResult(RESULT_OK)
            finish()
        }
    }
}