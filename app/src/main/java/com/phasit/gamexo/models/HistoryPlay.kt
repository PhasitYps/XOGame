package com.phasit.gamexo.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "HistoryPlayTB")
data class HistoryPlay(
    @PrimaryKey(autoGenerate = true) var id: Int?,
    var dataBoardJson: String?,
    var size: Int?,
    var winner: String?,
    var createDate: Long?,
    var updateDate: Long?
)