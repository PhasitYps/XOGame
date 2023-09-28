package com.phasit.gamexo.database

import androidx.room.*
import com.phasit.gamexo.models.HistoryPlay

@Dao
interface HistoryPlayDao {

    @Query("SELECT * FROM HistoryPlayTB ORDER BY createDate DESC")
    fun getAll(): List<HistoryPlay>

    @Query("SELECT * FROM HistoryPlayTB WHERE id IN (:ids)")
    suspend fun loadAllByIds(ids: IntArray): List<HistoryPlay>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(history: HistoryPlay)

    @Delete
    suspend fun delete(history: HistoryPlay)
}