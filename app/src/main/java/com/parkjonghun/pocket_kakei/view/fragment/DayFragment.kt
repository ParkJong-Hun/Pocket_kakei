package com.parkjonghun.pocket_kakei.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.HorizontalScrollView
import androidx.core.view.marginEnd
import androidx.fragment.app.Fragment
import com.parkjonghun.pocket_kakei.databinding.FragmentDayBinding

class DayFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = FragmentDayBinding.inflate(inflater, container, false)
        //TODO: スクロールして日付が変わるように
        view.dayScroll.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            Log.d("", "$scrollX,$oldScrollX")
        }

        return view.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentDayBinding.bind(view)
    }
}