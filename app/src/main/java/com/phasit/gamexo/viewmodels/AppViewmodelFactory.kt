package com.phasit.gamexo.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class AppViewmodelFactory(private val context: Context): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        when{
            modelClass.isAssignableFrom(PlayGameViewModel::class.java)->{
                return PlayGameViewModel(context) as T
            }
            modelClass.isAssignableFrom(HistoryPlayViewModel::class.java)->{
                return HistoryPlayViewModel(context) as T
            }
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}