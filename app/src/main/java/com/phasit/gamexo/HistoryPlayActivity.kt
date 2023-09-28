package com.phasit.gamexo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.phasit.gamexo.adapters.AdapterHistoryPlay
import com.phasit.gamexo.databinding.ActivityHistoryPlayBinding
import com.phasit.gamexo.databinding.ActivityPlayGameBinding
import com.phasit.gamexo.viewmodels.AppViewmodelFactory
import com.phasit.gamexo.viewmodels.HistoryPlayViewModel
import com.phasit.gamexo.viewmodels.PlayGameViewModel

class HistoryPlayActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistoryPlayBinding
    private lateinit var viewModel: HistoryPlayViewModel
    private lateinit var adapter: AdapterHistoryPlay

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("fewgwbke", "onCreate")
        binding = ActivityHistoryPlayBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this, AppViewmodelFactory(this))[HistoryPlayViewModel::class.java]
        setContentView(binding.root)

        setEvent()
        setAdapter()
        setObserve()

    }
    private fun setEvent(){
        binding.backIV.setOnClickListener {
            finish()
        }
    }

    private fun setAdapter(){
        adapter = AdapterHistoryPlay(this)
        binding.historyPlayRCV.adapter = adapter
        binding.historyPlayRCV.layoutManager = GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false)
    }
    private fun setObserve(){
        viewModel.historyPlayList.observe(this){
            adapter.setData(it)
        }

    }

}