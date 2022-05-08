package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * A simple [Fragment] subclass.
 * Use the [LibraryPage.newInstance] factory method to
 * create an instance of this fragment.
 */
class LibraryPage : Fragment() {
    lateinit var recyclerView: RecyclerView
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = LibraryPageRVAdapter(fillList())
        recyclerView = view.findViewById(R.id.libraryRecyclerView)
        recyclerView.adapter = adapter


        super.onViewCreated(view, savedInstanceState)
    }

    private fun fillList(): List<String> {
        // TODO("Return list of saved cards")
        return listOf(
            "9 мая", "1 сентября",
            "9 мая", "1 сентября",
            "9 мая", "1 сентября",
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_library, container, false)
    }

}