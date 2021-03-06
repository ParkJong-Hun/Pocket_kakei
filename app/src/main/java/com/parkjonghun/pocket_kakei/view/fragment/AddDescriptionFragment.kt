package com.parkjonghun.pocket_kakei.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.parkjonghun.pocket_kakei.databinding.FragmentAddDescriptionBinding
import com.parkjonghun.pocket_kakei.viewmodel.AddViewModel

class AddDescriptionFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = FragmentAddDescriptionBinding.inflate(inflater, container, false)



        val viewModel: AddViewModel by activityViewModels()


        //ViewModelからデータを持ってきてUI表示
        view.descriptionValue.setText(viewModel.description)


        //ボタンをクリックしたら
        view.submitDescriptionButton.setOnClickListener {
            //入力したのをViewModelのデータに更新
            viewModel.description = view.descriptionValue.text.toString()
            //収入ならデータ追加、じゃないと次の画面
            if(viewModel.isAdd) { viewModel.dataReady() } else { viewModel.nextStep() }
        }



        return view.root
    }
}