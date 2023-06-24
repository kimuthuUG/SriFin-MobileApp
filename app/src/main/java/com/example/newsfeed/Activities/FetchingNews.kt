package com.example.newsfeed.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsfeed.Adapters.NewsAdapter
import com.example.newsfeed.Model.NewsModal
import com.example.newsfeed.R
import com.google.firebase.database.*

class FetchingNews : AppCompatActivity() {

    private lateinit var newsRecyclerView: RecyclerView
    private lateinit var tvLoadingData: TextView
    private lateinit var nwsList: ArrayList<NewsModal>
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fetching_news)

        newsRecyclerView = findViewById(R.id.rvEmp)
        newsRecyclerView.layoutManager = LinearLayoutManager(this)
        newsRecyclerView.setHasFixedSize(true)

        tvLoadingData = findViewById(R.id.tvLoadingData)

        nwsList = arrayListOf<NewsModal>()
        getNewsData()
    }

    //get data in database
    private fun getNewsData() {
        newsRecyclerView.visibility = View.GONE
        tvLoadingData.visibility = View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("News")

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                nwsList.clear()
                if (snapshot.exists()) {
                    for (nwsSnap in snapshot.children) {
                        val nwsData = nwsSnap.getValue(NewsModal::class.java)
                        nwsList.add(nwsData!!)
                    }
                    val mAdapter = NewsAdapter(nwsList)
                    newsRecyclerView.adapter = mAdapter



                    newsRecyclerView.visibility = View.VISIBLE
                    tvLoadingData.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle onCancelled event
            }
        })
    }
}
