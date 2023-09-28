package com.phasit.gamexo.repository

import android.util.Log
import com.phasit.gamexo.database.HistoryPlayDao
import com.phasit.gamexo.models.HistoryPlay
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HistoryPlayRepository(private val historyPlayDao: HistoryPlayDao) {

    val fetchHistoryPlay = historyPlayDao.getAll()

    fun insert(historyPlay: HistoryPlay){
        Log.i("fewfwefwe", "insert: history: ${historyPlay.dataBoardJson}")
        GlobalScope.launch {
            historyPlayDao.insert(historyPlay)
        }
    }
}