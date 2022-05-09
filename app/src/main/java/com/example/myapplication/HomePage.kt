package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.volley.ClientError
import com.android.volley.NoConnectionError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson

/**
 * A simple [Fragment] subclass.
 * Use the [HomePage.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomePage : Fragment() {
    private val adapter = HomePageRVAdapter {
        val intent = Intent(activity, NewCardActivity::class.java)
        intent.putExtra("Preset", it)
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
        intent.addFlags(Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP)
        activity?.startActivity(intent)
    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    private val volleyErrorHandler: Response.ErrorListener = Response.ErrorListener {
        Log.println(
            Log.INFO,
            "aaa!",
            resources.getString(R.string.base_api_url) + "/" + resources.getString(R.string.api_presets)
        )

        if (it is ClientError) {
            Toast.makeText(
                activity,
                "Ошибка подключения к серверу",
                Toast.LENGTH_LONG
            ).show()
        } else if (it is NoConnectionError) {
            Toast.makeText(
                activity,
                "Не удалось подключиться к интернету",
                Toast.LENGTH_LONG
            ).show()
        }
        Log.e("Home Page Volley", it.toString())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.libraryRecyclerView)
        recyclerView.adapter = adapter

        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout)

        swipeRefreshLayout.setOnRefreshListener {
            fillList()
            swipeRefreshLayout.isRefreshing = false
        }
        fillList()
    }

    private fun fillList() {
        val queue = Volley.newRequestQueue(activity)
        val stringRequest =
            StringRequest(
                Request.Method.GET,
                resources.getString(R.string.base_api_url) + "/" + resources.getString(R.string.api_presets),
                {
                    val presetsList =
                        Gson().fromJson(it.toString(), Array<Preset>::class.java).asList()
                    adapter.itemsList = presetsList
                    adapter.notifyDataSetChanged()
                    val pr = view?.findViewById<ProgressBar>(R.id.homeProgressBar)
                    pr?.visibility = View.GONE
                },
                volleyErrorHandler
            )
        queue.add(stringRequest)
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