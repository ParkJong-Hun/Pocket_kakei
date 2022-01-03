package com.parkjonghun.pocket_kakei.view.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.parkjonghun.pocket_kakei.databinding.FragmentAddMoneyBinding
import com.parkjonghun.pocket_kakei.viewmodel.AddViewModel

class AddMoneyFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = FragmentAddMoneyBinding.inflate(inflater, container, false)

        val viewModel: AddViewModel by activityViewModels()
        //ViewModelの金額を使ってUI更新
        view.moneyValue.setText(viewModel.checkComma(viewModel.checkLength(viewModel.moneyValue.value.toString()).filter { it.isDigit() }.toInt()))
        //入力をするたび「,」を追加するかチェック
        view.moneyValue.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                view.moneyValue.removeTextChangedListener(this)
                view.moneyValue.apply {
                    setText(viewModel.checkComma(viewModel.checkLength(s.toString()).filter { it.isDigit() }.toInt()))
                    setSelection(view.moneyValue.text.length)
                }
                view.moneyValue.addTextChangedListener(this)
            }
        })
        //収入をクリックしたら
        view.submitIncomeButton.setOnClickListener {
            viewModel.isAdd = true
            viewModel.nextStep()
        }
        //支出をクリックしたら
        view.submitExpenditureButton.setOnClickListener {
            viewModel.isAdd = false
            viewModel.nextStep()
        }



        return view.root
    }
}