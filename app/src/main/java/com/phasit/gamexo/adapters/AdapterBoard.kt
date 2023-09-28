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
import androidx.recyclerview.widget.RecyclerView
import com.phasit.gamexo.R
import com.phasit.gamexo.models.ModelBox
import java.util.*
import kotlin.collections.ArrayList


class AdapterBoard(private val activity: Activity
) : RecyclerView.Adapter<AdapterBoard.ViewHolder>() {

    private val TAG = "AdapterBoard"


    private var l: ((index: Int)-> Unit)? = null
    fun setOnItemClick(l: (index: Int)-> Unit){
        this.l = l
    }

    private var dataList = ArrayList<ModelBox>()
    fun setData(data: ArrayList<ModelBox>){
        dataList = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.listview_box, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {   //สร้างเงื่อนไข//
        val model = dataList[position]

        holder.boxTV.text = model.player

        holder.boxCV.setOnClickListener {
            l?.let { it(position) }
        }

        if(model.isHighlight){
            holder.boxCV.setCardBackgroundColor(ContextCompat.getColor(activity, R.color.purple_200))
            holder.boxTV.setTextColor(ContextCompat.getColor(activity, R.color.white))
        }else{
            holder.boxCV.setCardBackgroundColor(Color.WHITE)
            holder.boxTV.setTextColor(ContextCompat.getColor(activity, R.color.black))
        }

    }

    override fun getItemCount(): Int {
        Log.i(TAG,"getItemCount "+dataList.size.toString())
        return dataList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var boxCV: CardView
        var boxTV: TextView


        init {
            boxCV = itemView.findViewById(R.id.boxCV)
            boxTV = itemView.findViewById(R.id.boxTV)
        }
    }


}
