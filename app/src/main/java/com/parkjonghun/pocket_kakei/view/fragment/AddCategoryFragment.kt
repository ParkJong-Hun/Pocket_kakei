package com.parkjonghun.pocket_kakei.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        view.cashButton.setOnClickListener {
            viewModel.category = "cash"
            viewModel.dataReady()
        }
        view.debitCardButton.setOnClickListener {
            viewModel.category = "debitCard"
            viewModel.dataReady()
        }
        view.creditCardButton.setOnClickListener {
            viewModel.category = "creditCard"
            viewModel.dataReady()
        }

        return view.root
    }
}