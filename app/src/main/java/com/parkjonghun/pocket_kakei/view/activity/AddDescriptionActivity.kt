package com.parkjonghun.pocket_kakei.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.parkjonghun.pocket_kakei.databinding.ActivityAddDescriptionBinding

class AddDescriptionActivity: AppCompatActivity() {

    private lateinit var binding: ActivityAddDescriptionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityAddDescriptionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.submitDescriptionButton.setOnClickListener {

        }

        binding.closeButton2.setOnClickListener {
            finish()
        }
    }
}