package com.parkjonghun.pocket_kakei.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.parkjonghun.pocket_kakei.databinding.ActivityAddBinding

class AddActivity: AppCompatActivity() {

    private lateinit var binding: ActivityAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.closeButton.setOnClickListener {
            finish()
        }
    }
}