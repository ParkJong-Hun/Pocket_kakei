package com.parkjonghun.pocket_kakei.view.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.github.mikephil.charting.charts.LineChart
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
        
        val testData = listOf(Entry(0F, 0F), Entry(1F,0F), Entry(2F,1F), Entry(3F, 1F), Entry(4F, 0F), Entry(5F, 2F), Entry(6F, 2F))

        fun loadChart(chart: LineChart, entry: List<Entry>) {
            val dataSet = LineDataSet(entry, "dataSet")
            val lineData = LineData(dataSet)

            dataSet.apply {
                mode = LineDataSet.Mode.CUBIC_BEZIER
                setDrawFilled(true)
                setDrawCircleHole(false)
                color = Color.GREEN
                setCircleColor(Color.GREEN)
                fillColor = Color.GREEN
                valueTextSize = 14F
                valueTextColor = Color.GREEN
            }

            chart.apply {
                data = lineData
                animateY(300)
                xAxis.isEnabled = false
                axisLeft.isEnabled = false
                axisRight.isEnabled = false
                description.isEnabled = false
                legend.isEnabled = false
            }
        }

        loadChart(view.firstWeekChart, testData)

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