package com.parkjonghun.pocket_kakei.view.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.parkjonghun.pocket_kakei.databinding.ActivitySheetBinding
import com.parkjonghun.pocket_kakei.model.Sheet
import com.parkjonghun.pocket_kakei.view.dialog.EditCategoryDialog
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
        when(sheet.category) {
            "deposit" -> binding.sheetCategoryValue.text = "現金"
            "debitCard" -> binding.sheetCategoryValue.text = "デビットカード"
            "creditCard" -> binding.sheetCategoryValue.text = "クレジットカード"
        }
        binding.sheetMoneyValue.setText(sheet.money.toString())
        binding.sheetTodayTextView.text = "${sheet.date.get(Calendar.YEAR)}年 ${sheet.date.get(Calendar.MONTH)}月 ${sheet.date.get(Calendar.DAY_OF_MONTH)}日"
        binding.sheetMemoValue.setText(sheet.memo)


        //入力をするたび「,」を追加するかチェック
        binding.sheetMoneyValue.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                binding.sheetMoneyValue.removeTextChangedListener(this)
                binding.sheetMoneyValue.apply {
                    setText(viewModel.checkComma(viewModel.checkLength(s.toString()).filter { it.isDigit() }.toInt()))
                    setSelection(binding.sheetMoneyValue.text.length)
                }
                binding.sheetMoneyValue.addTextChangedListener(this)
            }
        })


        //バックボタンをクリックしたら
        binding.sheetBackButton.setOnClickListener{
            //変化なし
            if(binding.sheetCategoryValue.text == sheet.category &&
                binding.sheetMoneyValue.text.toString() == sheet.money.toString() &&
                binding.sheetMemoValue.text.toString() == sheet.memo
            ) {
                setResult(RESULT_CANCELED)
                finish()
            }
            //変化あり
            else {
                var categoryValue = sheet.category
                when(binding.sheetCategoryValue.text) {
                    "現金" -> categoryValue = "cash"
                    "デビットカード" -> categoryValue = "debitCard"
                    "クレジットカード" -> categoryValue = "creditCard"
                }
                CoroutineScope(Dispatchers.IO).launch {
                    viewModel.updateSheet(
                        Sheet(
                            id =  sheet.id,
                            date = sheet.date,
                            isAdd = sheet.isAdd,
                            money = viewModel.moneyValue.value?: 0,
                            category = categoryValue,
                            description = sheet.description,
                            memo = binding.sheetMemoValue.text.toString()
                        )
                    )
                }
                setResult(RESULT_OK)
                finish()
            }
        }
        //削除ボタンをクリックしたら
        binding.sheetDeleteButton.setOnClickListener{
            CoroutineScope(Dispatchers.IO).launch {
                viewModel.deleteSheet(sheet)
            }
            setResult(RESULT_OK)
            finish()
        }

        if (!sheet.isAdd) {
            binding.sheetCategoryValue.setOnClickListener{
                val dialog = EditCategoryDialog()
                //作ったDialogFragmentの中のボタンを押したらMainActivityにDialogのEditTextのTextを返す
                dialog.setOnClickListener(object: EditCategoryDialog.OnClickListener{
                    override fun onClick(inputData: String) {
                        when(inputData) {
                            "deposit" -> binding.sheetCategoryValue.text = "現金"
                            "debitCard" -> binding.sheetCategoryValue.text = "デビットカード"
                            "creditCard" -> binding.sheetCategoryValue.text = "クレジットカード"
                        }
                    }
                })
                //作ったDialogオブジェクトをsupportFragmentManagerを使って画面に表示する。
                dialog.show(supportFragmentManager, "DialogFragment")
            }
        }
    }
}