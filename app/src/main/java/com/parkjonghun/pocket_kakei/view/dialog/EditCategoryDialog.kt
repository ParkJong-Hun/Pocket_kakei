package com.parkjonghun.pocket_kakei.view.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.DialogFragment
import com.parkjonghun.pocket_kakei.databinding.DialogEditCategoryBinding

class EditCategoryDialog: DialogFragment() {
    private var _binding: DialogEditCategoryBinding? = null
    private val binding get() = _binding!!
    private lateinit var onClickListener: OnClickListener

    interface OnClickListener {
        fun onClick(inputData: String)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogEditCategoryBinding.inflate(inflater, container, false)
        val view = binding.root



        val buttons: List<Button> = listOf(binding.editCashButton, binding.editDebitCardButton, binding.editCreditCardButton)
        val values: List<String> = listOf("deposit", "debitCard", "creditCard")
        //ボタンをクリックしたら
        fun onClickButton(button: Button, inputData: String) {
            button.setOnClickListener {
                onClickListener.onClick(inputData)
                dismiss()
            }
        }
        for (i in buttons.indices) {
            onClickButton(buttons[i], values[i])
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }
}