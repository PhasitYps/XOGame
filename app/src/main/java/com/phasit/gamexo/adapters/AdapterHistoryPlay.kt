package com.phasit.gamexo.adapters

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.phasit.gamexo.R
import com.phasit.gamexo.models.HistoryPlay
import com.phasit.gamexo.models.ModelBox
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class AdapterHistoryPlay(private val activity: Activity
) : RecyclerView.Adapter<AdapterHistoryPlay.ViewHolder>() {

    private val TAG = "AdapterBoard"


    private var dataList = ArrayList<HistoryPlay>()
    fun setData(data: ArrayList<HistoryPlay>){
        dataList = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.listview_history_play, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {   //สร้างเงื่อนไข//
        val model = dataList[position]


        holder.createDateTV.text = formatDate(Date(model.createDate!!))
        holder.winnerTV.text = if (model.winner != "") "${model.winner} ชนะ" else "!เสมอ"

        val adapter = AdapterBoard(activity)
        holder.boardRCV.adapter = adapter
        holder.boardRCV.layoutManager = GridLayoutManager(activity, model.size!!, GridLayoutManager.VERTICAL, false)
        adapter.setData(jsonStringToArrayList(model.dataBoardJson!!))

    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun jsonStringToArrayList(json: String): ArrayList<ModelBox> {
        val jsonParser = Json { ignoreUnknownKeys = true } // ตั้งค่าให้รับข้อมูลที่ไม่รู้จักใน JSON
        return jsonParser.decodeFromString(json)
    }

    private fun formatDate(date: Date): String{
        val dateFormated = SimpleDateFormat("เมื่อ dd/MM/yyyy HH:mm").format(date)
        return dateFormated
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var createDateTV: TextView
        var winnerTV: TextView
        var boardRCV: RecyclerView


        init {
            createDateTV = itemView.findViewById(R.id.createDateTV)
            winnerTV = itemView.findViewById(R.id.winnerTV)
            boardRCV = itemView.findViewById(R.id.boardRCV)
        }
    }


}
