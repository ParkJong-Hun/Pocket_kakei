package com.parkjonghun.pocket_kakei.view.activity

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.parkjonghun.pocket_kakei.databinding.ActivityAddBinding
import com.parkjonghun.pocket_kakei.view.fragment.*
import com.parkjonghun.pocket_kakei.viewmodel.AddViewModel
import java.text.SimpleDateFormat
import java.util.*

class AddActivity: AppCompatActivity() {

    private lateinit var binding: ActivityAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: AddViewModel by viewModels()

        //初期はバックボタンを見えないように
        binding.backButton.visibility = View.GONE
        //初期Fragment
        supportFragmentManager.beginTransaction().add(binding.addFragmentLayout.id, AddMoneyFragment()).commit()
        //持ってきたカレンダーデータ
        val selectedCalendarDay = SimpleDateFormat("yyyy MM dd", resources.configuration.locales.get(0)).parse(intent.getStringExtra("calendar")!!)
        viewModel.calendar.time = selectedCalendarDay!!
        //選択した日付
        binding.todayTextView.text = viewModel.getToday()


        //ステップによって画面が変わる
        viewModel.currentStep.observe(this) {
            when(it) {
                0 ->
                    supportFragmentManager.beginTransaction().replace(
                        binding.addFragmentLayout.id,
                        AddMoneyFragment()
                    ).commitAllowingStateLoss()
                1->
                    supportFragmentManager.beginTransaction ().replace(
                        binding.addFragmentLayout.id,
                        AddDescriptionFragment()
                    ).commitAllowingStateLoss()
                2->
                    supportFragmentManager.beginTransaction().replace(
                        binding.addFragmentLayout.id,
                        AddCategoryFragment()
                    ).commitAllowingStateLoss()
            }
        }
        //ステップが０ならバックボタンが見えないように
        viewModel.currentStep.observe(this) {
            if(it == 0) {
                binding.backButton.visibility = View.GONE
            } else {
                binding.backButton.visibility = View.VISIBLE
            }
        }
        //データを追加する準備できたら
        viewModel.dataIsReady.observe(this) {
            if(it) {
                //データをデータベースに追加する
                viewModel.addOnDatabase()
                //データを追加した合図を送る
                setResult(RESULT_OK)
                finish()
            }
        }


        //バックボタン
        binding.backButton.setOnClickListener {
            viewModel.previousStep()
        }
        //クローズボタン
        binding.closeButton.setOnClickListener {
            finish()
        }
    }
}