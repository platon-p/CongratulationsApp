package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView

/**
 * A simple [Fragment] subclass.
 * Use the [LibraryPage.newInstance] factory method to
 * create an instance of this fragment.
 */
class LibraryPage : Fragment() {
    lateinit var recyclerView: RecyclerView
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = LibraryPageRVAdapter(fillList()) {
            val intent = Intent(activity, ShowCardActivity::class.java)
            intent.putExtra("Card", it)
            startActivity(intent)
        }
        recyclerView = view.findViewById(R.id.libraryRecyclerView)
        recyclerView.adapter = adapter
        super.onViewCreated(view, savedInstanceState)
    }

    private fun fillList(): List<Card> {
        // TODO("Return list of SAVED cards")
        return listOf(
            Card(
                Preset(
                    1u,
                    "Благодарственное письмо",
                    "A4",
                    "Ea tempor in pariatur ea enim nulla eiusmod. Nulla fugiat consequat occaecat est id consectetur Lorem voluptate ut amet sunt tempor. Nulla fugiat consequat occaecat est id consectetur Lorem voluptate ut amet sunt tempor",
                    "[Уважаемый][Уважаемая] {}!",
                    30f, 60f, 30f, ""
                ),
                "Моя открытка", "Мужыской", "Иванов Иван Иванович"
            )
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