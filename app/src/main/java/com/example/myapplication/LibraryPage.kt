package com.example.myapplication

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.myapplication.database.AppDatabase
import io.reactivex.android.schedulers.AndroidSchedulers

class LibraryPage : Fragment() {
    lateinit var recyclerView: RecyclerView
    private lateinit var adapter: LibraryPageRVAdapter
    private lateinit var preloader: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            Toast.makeText(
                activity?.applicationContext,
                data?.getStringExtra("ToastText"),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        preloader = view.findViewById(R.id.libraryPreloader)
        swipeRefresh = view.findViewById(R.id.librarySwipeRefresh)
        swipeRefresh.setOnRefreshListener {
            loadCards()
        }

        adapter = LibraryPageRVAdapter {
            val intent = Intent(activity, ShowCardActivity::class.java)
            intent.putExtra("Card", it)
            startActivityForResult(intent, Activity.RESULT_OK)
        }
        recyclerView = view.findViewById(R.id.libraryRecyclerView)
        recyclerView.adapter = adapter
        loadCards()
        super.onViewCreated(view, savedInstanceState)
    }

    @SuppressLint("CheckResult", "NotifyDataSetChanged")
    private fun loadCards() {
        AppDatabase
            .getDatabase(requireContext())
            .cardDao()
            .getAll()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                adapter.itemsList = it
                adapter.notifyDataSetChanged()
                preloader.visibility = View.GONE
                swipeRefresh.isRefreshing = false

            }, {
                Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_LONG).show()
                swipeRefresh.isRefreshing = false
            })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_library, container, false)
    }

}