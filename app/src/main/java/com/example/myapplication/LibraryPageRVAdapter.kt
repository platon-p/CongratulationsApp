package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class LibraryPageRVAdapter(private val items: List<String>) :
    RecyclerView.Adapter<LibraryPageRVAdapter.MyViewHolder>() {


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.homeRVLabel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.home_recyclerview_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.textView.text = items[position]
    }

    override fun getItemCount(): Int = items.size
}
