package com.example.myapplication

import android.annotation.SuppressLint
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
import com.example.myapplication.api.ApiClient
import com.example.myapplication.api.Preset
import com.example.myapplication.api.PresetApi
import retrofit2.Call
import retrofit2.Callback

/**
 * A simple [Fragment] subclass.
 * Use the [HomePage.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomePage : Fragment() {
    private lateinit var pr: ProgressBar

    private val adapter = HomePageRVAdapter {
        val intent = Intent(activity, NewCardActivity::class.java)
        intent.putExtra("Preset", it)
        activity?.startActivity(intent)
    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pr = view.findViewById(R.id.homeProgressBar)
        recyclerView = view.findViewById(R.id.homeRecyclerView)
        recyclerView.adapter = adapter
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout)
        swipeRefreshLayout.setOnRefreshListener {
            fillList()
        }
        fillList()
    }

    private fun fillList() {
        val api = ApiClient.getApiClient().create(PresetApi::class.java)
        api.getAllPresets()
            .enqueue(object : Callback<List<Preset>> {
                @SuppressLint("NotifyDataSetChanged")
                override fun onResponse(
                    call: Call<List<Preset>>,
                    response: retrofit2.Response<List<Preset>>
                ) {
                    adapter.itemsList =
                        response.body() as List<Preset> // as List<com.example.myapplication.Preset>
                    adapter.notifyDataSetChanged()
                    pr.visibility = View.GONE
                    swipeRefreshLayout.isRefreshing = false
                }

                override fun onFailure(call: Call<List<Preset>>, t: Throwable) {
                    Log.e("Retrofit", t.toString())
                    Toast.makeText(activity?.applicationContext, "Ooops...", Toast.LENGTH_SHORT)
                        .show()
                    swipeRefreshLayout.isRefreshing = false
                }
            })
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