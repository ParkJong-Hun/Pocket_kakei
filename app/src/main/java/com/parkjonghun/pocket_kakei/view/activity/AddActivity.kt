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

        binding.backButton.visibility = View.GONE

        supportFragmentManager.beginTransaction().add(binding.addFragmentLayout.id, AddMoneyFragment()).commit()

        val intent = intent

        val viewModel: AddViewModel by viewModels()
        val selectedCalendarDay = SimpleDateFormat("yyyy MM dd", Locale.JAPANESE).parse(intent.getStringExtra("calendar")!!)
        viewModel.calendar.time = selectedCalendarDay!!
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
        viewModel.currentStep.observe(this) {
            if(it == 0) {
                binding.backButton.visibility = View.GONE
            } else {
                binding.backButton.visibility = View.VISIBLE
            }
        }
        viewModel.dataIsReady.observe(this) {
            if(it) {
                viewModel.addOnDatabase()
                setResult(RESULT_OK)
                finish()
            }
        }

        binding.backButton.setOnClickListener {
            viewModel.previousStep()
        }

        binding.closeButton.setOnClickListener {
            finish()
        }
    }
}