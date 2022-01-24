package com.parkjonghun.pocket_kakei.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.parkjonghun.pocket_kakei.databinding.FragmentDayArticleBinding
import com.parkjonghun.pocket_kakei.model.Sheet
import com.parkjonghun.pocket_kakei.view.activity.SheetActivity
import com.parkjonghun.pocket_kakei.view.recylcerview.DayOfMonth2Adapter
import com.parkjonghun.pocket_kakei.viewmodel.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//FragmentDayから実際使うViewPagerの部分
class DayFragmentArticle(val activityResult: ActivityResultLauncher<Intent>): Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = FragmentDayArticleBinding.inflate(inflater, container, false)



        val viewModel: MainViewModel by activityViewModels()



        val adapters: List<DayOfMonth2Adapter> = listOf(DayOfMonth2Adapter(), DayOfMonth2Adapter())
        val managers: List<LinearLayoutManager> = listOf(LinearLayoutManager(inflater.context), LinearLayoutManager(inflater.context))
        val lists: List<RecyclerView> = listOf(view.dayIncomeList, view.dayExpenditureList)
        val listsIsAdd: List<Boolean> = listOf(true, false)
        val titles: List<TextView> = listOf(view.dayIncomeListTitle, view.dayExpenditureListTitle)
        for (i in lists.indices) {
            lists[i].layoutManager = managers[i]
            lists[i].adapter = adapters[i]
        }


        //UI更新
        suspend fun updateUI() {
            val switch: MutableList<Boolean> = mutableListOf(false, false)
            for (i in lists.indices) {
                CoroutineScope(Dispatchers.Main).launch {
                    //選択した日の情報を利用し、データを加工
                    val sheets = viewModel.optimizeForDay(listsIsAdd[i])
                    //データがないわけじゃないと
                    if (sheets != null) {
                        if (sheets.isNotEmpty()) {
                            //情報を表示
                            lists[i].visibility = View.VISIBLE
                            titles[i].visibility = View.VISIBLE
                        } else {
                            //情報を非表示
                            lists[i].visibility = View.GONE
                            titles[i].visibility = View.GONE
                        }
                        lists[i].adapter = adapters[i]
                        adapters[i].submitList(sheets)

                    }
                    //データが全くないと
                    if (sheets.isNullOrEmpty()) {
                        switch[i] = true
                    }
                }.join()
            }
            if (switch[0] && switch[1]) {
                view.dayNoDataNotification.visibility = View.VISIBLE
            } else {
                view.dayNoDataNotification.visibility = View.GONE
            }
        }
        //選択した日が変わったら
        viewModel.selectedDay.observe(viewLifecycleOwner) {
            CoroutineScope(Dispatchers.Main).launch {
                updateUI()
            }
        }
        //シートが変わったら
        viewModel.sheets.observe(viewLifecycleOwner) {
            CoroutineScope(Dispatchers.Main).launch {
                updateUI()
            }
        }

        //シートリストをクリックしたら
        for (i in lists.indices) {
            adapters[i].setOnClickListener(object : DayOfMonth2Adapter.OnItemCLickListener {
                override fun onItemClick(v: View, sheet: Sheet) {
                    Intent(requireContext(), SheetActivity::class.java).apply {
                        putExtra("sheet", sheet)
                        activityResult.launch(this)
                    }
                }
            })
        }

        return view.root
    }
}