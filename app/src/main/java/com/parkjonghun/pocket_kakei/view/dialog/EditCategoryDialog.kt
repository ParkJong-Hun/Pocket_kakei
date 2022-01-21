package com.parkjonghun.pocket_kakei.view.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        //ボタンをクリックしたら
        binding.editCashButton.setOnClickListener{
            onClickListener.onClick("現金")
            dismiss()
        }
        binding.editDebitCardButton.setOnClickListener{
            onClickListener.onClick("デビットカード")
            dismiss()
        }
        binding.editCreditCardButton.setOnClickListener{
            onClickListener.onClick("クレジットカード")
            dismiss()
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