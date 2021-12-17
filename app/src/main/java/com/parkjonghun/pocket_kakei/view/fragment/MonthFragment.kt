package com.parkjonghun.pocket_kakei.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.parkjonghun.pocket_kakei.databinding.FragmentMonthBinding
import com.parkjonghun.pocket_kakei.view.AddActivity

class MonthFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = FragmentMonthBinding.inflate(inflater, container, false)
        view.addButton.setOnClickListener {
            startActivity(Intent(activity, AddActivity::class.java))
        }
        return view.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}