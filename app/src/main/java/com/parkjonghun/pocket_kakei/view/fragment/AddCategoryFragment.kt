package com.parkjonghun.pocket_kakei.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.parkjonghun.pocket_kakei.databinding.FragmentAddCategoryBinding
import com.parkjonghun.pocket_kakei.viewmodel.AddViewModel

class AddCategoryFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = FragmentAddCategoryBinding.inflate(inflater, container, false)



        val viewModel: AddViewModel by activityViewModels()



        val buttons: List<Button> = listOf(view.cashButton, view.debitCardButton, view.creditCardButton)
        val values: List<String> = listOf("cash", "debitCard", "creditCard")
        fun clickButton(button: Button, data: String) {
            button.setOnClickListener {
                viewModel.category = data
                viewModel.dataReady()
            }
        }
        //カテゴリー選択してデータ追加
        for (i in buttons.indices) {
            clickButton(buttons[i], values[i])
        }



        return view.root
    }
}