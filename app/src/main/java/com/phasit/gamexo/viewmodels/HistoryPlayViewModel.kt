package com.phasit.gamexo.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.phasit.gamexo.database.AppDatabase
import com.phasit.gamexo.models.HistoryPlay
import com.phasit.gamexo.repository.HistoryPlayRepository

class HistoryPlayViewModel(context: Context): ViewModel() {

    private val _historyPlayList: MutableLiveData<ArrayList<HistoryPlay>> = MutableLiveData(arrayListOf())
    val historyPlayList: LiveData<ArrayList<HistoryPlay>> get() = _historyPlayList

    private lateinit var appDb: AppDatabase
    private lateinit var historyPlayRepository: HistoryPlayRepository

    init {

        appDb = AppDatabase.getDatabase(context)
        historyPlayRepository = HistoryPlayRepository(appDb.historyPlayDao())

        _historyPlayList.value = historyPlayRepository.fetchHistoryPlay as ArrayList<HistoryPlay>
    }
}