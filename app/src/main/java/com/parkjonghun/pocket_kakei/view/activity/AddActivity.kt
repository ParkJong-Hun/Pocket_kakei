package com.parkjonghun.pocket_kakei.view.activity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.parkjonghun.pocket_kakei.databinding.ActivityAddBinding
import com.parkjonghun.pocket_kakei.viewmodel.AddViewModel

class AddActivity: AppCompatActivity() {

    //TODO: これと説明画面をFragmentとしてこのActivityで使う。

    private lateinit var binding: ActivityAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: AddViewModel by viewModels()
        binding.moneyValue.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                binding.moneyValue.removeTextChangedListener(this)
                binding.moneyValue.apply {
                    setText(viewModel.checkComma(viewModel.checkLength(s.toString()).filter { it.isDigit() }.toInt()))
                    setSelection(binding.moneyValue.text.length)
                }
                binding.moneyValue.addTextChangedListener(this)
            }
        })

        val intent = Intent(this, AddDescriptionActivity::class.java)
        binding.submitExpenditureButton.setOnClickListener {
            intent.let {
                it.putExtra("money", viewModel.moneyValue.value)
                it.putExtra("isIn", true)
                startActivity(it)
            }

        }

        binding.submitIncomeButton.setOnClickListener {
            intent.let {
                it.putExtra("money", viewModel.moneyValue.value)
                it.putExtra("isIn", false)
                startActivity(it)
            }
        }

        binding.closeButton.setOnClickListener {
            finish()
        }
    }
}