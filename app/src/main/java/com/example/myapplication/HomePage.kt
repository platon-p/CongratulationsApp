package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import java.io.Serializable

/**
 * A simple [Fragment] subclass.
 * Use the [HomePage.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomePage : Fragment() {
    lateinit var recyclerView: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = HomePageRVAdapter(fillList()) {
            val intent = Intent(activity, NewCardActivity::class.java)
            intent.putExtra("Preset", it)
            activity?.startActivity(intent)
        }
        recyclerView = view.findViewById(R.id.libraryRecyclerView)
        recyclerView.adapter = adapter
    }

    private fun fillList(): List<Preset> {
        // TODO("Send request to API => JSON => List of Presets")
        return listOf(
            Preset("9 мая"),
            Preset("23 февраля"),
            Preset("8 марта"),
            Preset("Новый год"),
            Preset("Новый год"),
            Preset("Новый год"),
            Preset("Новый год"),
            Preset("Новый год"),
            Preset("Новый год"),
            Preset("Новый год"),
            Preset("Новый год"),
            Preset("Новый год"),
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