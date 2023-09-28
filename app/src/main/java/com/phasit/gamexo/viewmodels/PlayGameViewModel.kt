package com.phasit.gamexo.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.phasit.gamexo.database.AppDatabase
import com.phasit.gamexo.models.HistoryPlay
import com.phasit.gamexo.models.ModelBox
import com.phasit.gamexo.repository.HistoryPlayRepository

class PlayGameViewModel(private val context: Context): ViewModel() {

    private var _boxList : MutableLiveData<ArrayList<ModelBox>> = MutableLiveData(arrayListOf())
    val boxList : LiveData<ArrayList<ModelBox>> get() = _boxList

    private var _currentPlayer = MutableLiveData("X")
    val currentPlayer: LiveData<String> get() = _currentPlayer

    private var _gameAction = MutableLiveData(true)
    val gameAction: LiveData<Boolean> get() = _gameAction

    private var _winner = MutableLiveData("")
    val winner: LiveData<String> get() = _winner

    private var size = 0
    fun setSize(s: Int){
        this.size = s
    }
    fun getSize(): Int{
        return size
    }
    fun createBoard(){
        if(boxList.value!!.isNotEmpty()){
            return
        }
        val dataList = ArrayList<ModelBox>()
        for(i in 1..size){
            for (i in 1..size){
                dataList.add(ModelBox())
            }
        }
        _boxList.value = dataList
    }

    fun onItemBoardClick(index: Int){
        if( boxList.value!![index].player.isNotEmpty()){
            return
        }
        boxList.value!![index].player = currentPlayer.value!!
        val (isWin, indexHighlights) = checkWin()
        if(isWin){
            _winner.value = currentPlayer.value!!
            updateBoxHighlight(indexHighlights)
            _gameAction.value = false
        }else{
            if(!isBoardFull()){
                _currentPlayer.value = if(currentPlayer.value == "X") "O" else "X"
            }else{
                _gameAction.value = false
            }
        }
    }

    fun onResetGameClick(){
        val oldList = boxList.value!!
        for (i in 0 until oldList.size){
            oldList[i].player = ""
            oldList[i].isHighlight = false
        }
        _boxList.value = oldList
        _currentPlayer.value = "X"
        _winner.value = ""
        _gameAction.value = true
    }

    fun insertHistoryPlay(historyPlay: HistoryPlay){
        val db = AppDatabase.getDatabase(context)
        val historyPlayDao = db.historyPlayDao()
        val historyPlayRepository = HistoryPlayRepository(historyPlayDao)
        historyPlayRepository.insert(historyPlay)
    }

    private fun checkWin(): ModelWin{
        val currentPlay = currentPlayer.value!!
        val boxList = boxList.value!!
        for(i in 0 until boxList.size){

            if(boxList[i].player.isNotEmpty()){
                //แนวนอน -
                if((i + 2) < boxList.size && boxList[i].player == currentPlay
                    && boxList[i + 1].player == currentPlay
                    && boxList[i + 2].player == currentPlay
                    && (i + 1) % size != 0 //diagonal2 don't new line
                    && (i + 2) % size != 0)//diagonal2 don't new line
                {
                    Log.i("fewwgw", "แนวนอน")
                    return ModelWin(isWin = true, intArrayOf(i, i + 1, i + 2))
                }

                //แนวตั้ง |
                if((i + size * 2) < boxList.size
                    && boxList[i].player == currentPlay
                    && boxList[i + size].player == currentPlay
                    && boxList[i + size * 2].player == currentPlay)
                {
                    Log.i("fewwgw", "แนวตั้ง")
                    return ModelWin(isWin = true, intArrayOf(i, i + size, i + size*2))
                }

                //แนวทแยง \
                if((i + size * 2 + 2) < boxList.size
                    && boxList[i].player == currentPlay
                    && boxList[i + (size + 1) ].player == currentPlay
                    && boxList[i + (size + 1) * 2].player == currentPlay)
                {
                    Log.i("fewwgw", "แนวทะแยงขวา")
                    return ModelWin(isWin = true, intArrayOf(i, i + size + 1, i + size * 2 + 2))
                }
                //แนวทแยงซ้าย /

                if((i + size * 2 - 2) < boxList.size
                    && boxList[i].player == currentPlay
                    && boxList[i + size - 1].player == currentPlay
                    && boxList[i + size * 2 - 2].player == currentPlay
                    && ((i + size - 1)/ size) != (i/size) //position row of diagonal1 != diagonal2
                    && (i + size * 2 - 2)/size != ((i + size - 1)/ size) //position row of diagonal2 != diagonal3
                )
                {
                    Log.i("fewwgw", "แนวทะแยงซ้าย")
                    return ModelWin(isWin = true, intArrayOf(i, i + size - 1, i + size * 2 - 2))
                }
            }
        }

        return ModelWin(isWin = false, intArrayOf())

    }

    private fun updateBoxHighlight(indexHighlights: IntArray){
        val oldList = boxList.value!!
        for(i in indexHighlights){
            oldList[i].isHighlight = true
        }
        _boxList.value = oldList
    }

    private fun isBoardFull(): Boolean {
        for (item in boxList.value!!) {
            if (item.player == "") {
                return false
            }
        }
        return true
    }

    data class ModelWin(
        var isWin: Boolean = false,
        var indexList: IntArray = intArrayOf()
    )
}