package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView

/**
 * A simple [Fragment] subclass.
 * Use the [HomePage.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomePage : Fragment() {
    lateinit var recyclerView: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.homeRecyclerView)
        recyclerView.adapter = HomePageRVAdapter(fillList())

        // TODO("Add on item click listener for change activity to NewCardActivity and send current item")
    }

    private fun fillList(): List<Preset> {
        // TODO("Send request to API => JSON => List of Presets")
        return listOf(
            Preset("9 мая"),
            Preset("23 февраля"),
            Preset("8 марта"),
            Preset("Новый год"),
            Preset("День рождения"),
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() = HomePage()
    }
}