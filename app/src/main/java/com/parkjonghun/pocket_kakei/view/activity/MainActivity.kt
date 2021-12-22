package com.parkjonghun.pocket_kakei.view.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.parkjonghun.pocket_kakei.R
import com.parkjonghun.pocket_kakei.databinding.ActivityMainBinding
import com.parkjonghun.pocket_kakei.view.fragment.DayFragment
import com.parkjonghun.pocket_kakei.view.fragment.MonthFragment
import com.parkjonghun.pocket_kakei.view.fragment.WeekFragment
import com.parkjonghun.pocket_kakei.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: MainViewModel by viewModels()

        supportFragmentManager.beginTransaction().add(binding.fragmentLayout.id, MonthFragment()).commit()
        binding.bottomNavigationView.run {
            setOnItemSelectedListener { item ->
                when(item.itemId) {
                    R.id.page_month -> {
                        supportFragmentManager.beginTransaction().replace(binding.fragmentLayout.id, MonthFragment()).commitAllowingStateLoss()
                    }
                    R.id.page_week -> {
                        supportFragmentManager.beginTransaction().replace(binding.fragmentLayout.id, WeekFragment()).commitAllowingStateLoss()
                    }
                    R.id.page_day -> {
                        supportFragmentManager.beginTransaction().replace(binding.fragmentLayout.id, DayFragment()).commitAllowingStateLoss()
                    }
                }
                true
            }
        }
    }
}