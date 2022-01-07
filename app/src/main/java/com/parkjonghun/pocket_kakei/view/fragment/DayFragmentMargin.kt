package com.parkjonghun.pocket_kakei.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.parkjonghun.pocket_kakei.databinding.FragmentDayMarginBinding

//Dragのために使う空の画面
class DayFragmentMargin: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = FragmentDayMarginBinding.inflate(inflater, container, false)
        return view.root
    }
}