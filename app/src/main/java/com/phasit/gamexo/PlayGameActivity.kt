package com.phasit.gamexo

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.phasit.gamexo.adapters.AdapterBoard
import com.phasit.gamexo.databinding.ActivityPlayGameBinding
import com.phasit.gamexo.models.HistoryPlay
import com.phasit.gamexo.viewmodels.AppViewmodelFactory
import com.phasit.gamexo.viewmodels.PlayGameViewModel
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class PlayGameActivity : AppCompatActivity() {


    private lateinit var binding: ActivityPlayGameBinding
    private lateinit var viewModel: PlayGameViewModel
    private lateinit var adapterBoard: AdapterBoard

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPlayGameBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this, AppViewmodelFactory(this))[PlayGameViewModel::class.java]
        setContentView(binding.root)

        init()
        setEvent()
    }

    private fun init(){
        setObServe()

        viewModel.setSize(intent.getIntExtra("size", 0))
        viewModel.createBoard()
        updateDisplayPlayer()
        updateDisplayMessage()

        setAdapter()
    }


    private fun setAdapter(){
        adapterBoard = AdapterBoard(this)
        adapterBoard.setOnItemClick { index ->
            if(!viewModel.gameAction.value!!){
                return@setOnItemClick
            }
            viewModel.onItemBoardClick(index)
            binding.boardRCV.adapter!!.notifyItemChanged(index)
        }

        binding.boardRCV.adapter = adapterBoard
        binding.boardRCV.layoutManager = GridLayoutManager(this, viewModel.getSize(), GridLayoutManager.VERTICAL, false)
    }

    private fun setEvent(){
        binding.resetGameBTN.setOnClickListener {
            viewModel.onResetGameClick()
        }

        binding.historyIV.setOnClickListener {
            val intent = Intent(this, HistoryPlayActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setObServe() {
        viewModel.boxList.observe(this){
            adapterBoard.setData(viewModel.boxList.value!!)
        }
        viewModel.currentPlayer.observe(this){ player->
            if(viewModel.gameAction.value!!){
                binding.messageTV.text = "ถึงตาผู้เล่น $player แล้ว"
                updateDisplayPlayer()
            }
        }
        viewModel.gameAction.observe(this){ gameAction->
            updateDisplayMessage()
        }

    }

    private fun updateDisplayPlayer(){
        val player = viewModel.currentPlayer.value!!
        when(player){
            "X"->{
                binding.xPlayerCV.setCardBackgroundColor(getColor(R.color.purple_200))
                binding.xPlayerTV.setTextColor(getColor(R.color.white))
                binding.oPlayerCV.setCardBackgroundColor(Color.WHITE)
                binding.oPlayerTV.setTextColor(getColor(R.color.black))

            }
            "O"->{
                binding.xPlayerCV.setCardBackgroundColor(Color.WHITE)
                binding.xPlayerTV.setTextColor(getColor(R.color.black))
                binding.oPlayerCV.setCardBackgroundColor(getColor(R.color.purple_200))
                binding.oPlayerTV.setTextColor(getColor(R.color.white))
            }
        }
    }

    private fun updateDisplayMessage(){
        val gameAction = viewModel.gameAction.value!!
        when(gameAction){
            true->{
                binding.messageTV.text = "ผู้เล่น ${viewModel.currentPlayer.value} เริ่มก่อน"
            }
            false->{
                if(viewModel.winner.value!!.isNotEmpty()){
                    binding.messageTV.text = "จบเกม ผู้ชนะคือผู้เล่น ${viewModel.winner.value}"
                }else{
                    binding.messageTV.text = "จบเกม เสมอ"
                }

                val historyPlay = HistoryPlay(
                    id = null,
                    size = viewModel.getSize(),
                    dataBoardJson = Json{ encodeDefaults = true }.encodeToString(viewModel.boxList.value!!),
                    winner = viewModel.winner.value!!,
                    updateDate = System.currentTimeMillis(),
                    createDate = System.currentTimeMillis()
                )
                viewModel.insertHistoryPlay(historyPlay)
            }
        }
    }


}