package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.database.Card

class LibraryPageRVAdapter(
    items: List<Card> = emptyList(),
    private var listener: ((Card) -> Unit)? = null
) :
    RecyclerView.Adapter<LibraryPageRVAdapter.MyViewHolder>() {
    var itemsList: List<Card> = items

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.textView2)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.library_recyclerview_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.textView.text = itemsList[position].name
        holder.itemView.setOnClickListener{
            listener?.invoke(itemsList[position])
        }
    }

    override fun getItemCount(): Int = itemsList.size
}
