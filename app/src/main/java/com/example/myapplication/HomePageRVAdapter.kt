package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HomePageRVAdapter(
    items: List<Preset> = emptyList(),
    private var listener: ((Preset) -> Unit)? = null
) :
    RecyclerView.Adapter<HomePageRVAdapter.MyViewHolder>() {

    var itemsList: List<Preset> = items

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.homeRVLabel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.home_recyclerview_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.textView.text = itemsList[position].name
        holder.itemView.setOnClickListener {
            listener?.invoke(itemsList[position])
        }
    }

    override fun getItemCount(): Int = itemsList.size
}
