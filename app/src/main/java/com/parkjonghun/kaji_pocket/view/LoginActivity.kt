package com.parkjonghun.kaji_pocket.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.parkjonghun.kaji_pocket.databinding.ActivityLoginBinding
import com.parkjonghun.kaji_pocket.viewmodel.LoginViewModel

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: LoginViewModel by viewModels()
    }
}