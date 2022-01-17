package com.parkjonghun.pocket_kakei.view.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.parkjonghun.pocket_kakei.databinding.FragmentWeekArticleBinding
import com.parkjonghun.pocket_kakei.viewmodel.MainViewModel

class WeekFragmentArticle : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = FragmentWeekArticleBinding.inflate(inflater, container, false)

        val viewModel: MainViewModel by activityViewModels()

        //TODO: この設定を利用して格週を実装
        val testData = listOf(Entry(0F, 0F), Entry(1F,0F), Entry(2F,1F), Entry(3F, 1F), Entry(4F, 0F), Entry(5F, 2F), Entry(6F, 2F))
        val testDataSet = LineDataSet(testData, "testDataSet")
        val lineData = LineData(testDataSet)

        testDataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
        testDataSet.setDrawFilled(true)
        testDataSet.setDrawCircleHole(false)
        testDataSet.color = Color.GREEN
        testDataSet.setCircleColor(Color.GREEN)
        testDataSet.fillColor = Color.GREEN
        testDataSet.valueTextSize = 14F
        testDataSet.valueTextColor = Color.GREEN

        view.firstWeekChart.data = lineData
        view.firstWeekChart.setExtraOffsets(0F, 0F, 0F, -50F)
        view.firstWeekChart.animateY(300)
        view.firstWeekChart.xAxis.isEnabled = false
        view.firstWeekChart.axisLeft.isEnabled = false
        view.firstWeekChart.axisRight.isEnabled = false
        view.firstWeekChart.description.isEnabled = false
        view.firstWeekChart.legend.isEnabled = false

        fun initDetails() {
            view.firstWeekDetails.visibility = View.GONE
            view.secondWeekDetails.visibility = View.GONE
            view.thirdWeekDetails.visibility = View.GONE
            view.fourthWeekDetails.visibility = View.GONE
            view.fifthWeekDetails.visibility = View.GONE
            view.sixthWeekDetails.visibility = View.GONE
        }

        view.firstWeekLayout.setOnClickListener{
            initDetails()
            view.firstWeekDetails.visibility = View.VISIBLE
        }
        view.secondWeekLayout.setOnClickListener{
            initDetails()
            view.secondWeekDetails.visibility = View.VISIBLE
        }
        view.thirdWeekLayout.setOnClickListener{
            initDetails()
            view.thirdWeekDetails.visibility = View.VISIBLE
        }
        view.fourthWeekLayout.setOnClickListener{
            initDetails()
            view.fourthWeekDetails.visibility = View.VISIBLE
        }
        view.fifthWeekLayout.setOnClickListener{
            initDetails()
            view.fifthWeekDetails.visibility = View.VISIBLE
        }
        view.sixthWeekLayout.setOnClickListener{
            initDetails()
            view.sixthWeekDetails.visibility = View.VISIBLE
        }

        return view.root
    }
}