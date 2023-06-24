package com.example.newsfeed.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsfeed.Adapters.UserNewsAdapter
import com.example.newsfeed.Model.NewsModal
import com.example.newsfeed.R
import com.google.firebase.database.*
import java.util.*
import kotlin.collections.ArrayList

class FetchingNewsUser : AppCompatActivity() {

    private lateinit var newsRecyclerView: RecyclerView
    private lateinit var tvLoadingData: TextView
    private lateinit var searchView: SearchView
    private lateinit var newsList: ArrayList<NewsModal>
    private lateinit var filteredList: ArrayList<NewsModal>
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fetching_news_user)

        newsRecyclerView = findViewById(R.id.rvEmp)
        newsRecyclerView.layoutManager = LinearLayoutManager(this)
        newsRecyclerView.setHasFixedSize(true)

        tvLoadingData = findViewById(R.id.tvLoadingData)

        searchView = findViewById(R.id.searchbtn)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    filter(newText.toLowerCase(Locale.getDefault()))
                }
                return true
            }
        })

        newsList = arrayListOf()
        filteredList = arrayListOf()
        getNewsData()
    }

    //get news data from database
    private fun getNewsData() {
        newsRecyclerView.visibility = View.GONE
        tvLoadingData.visibility = View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("News")

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                newsList.clear()
                if (snapshot.exists()) {
                    for (nwsSnap in snapshot.children) {
                        val nwsData = nwsSnap.getValue(NewsModal::class.java)
                        nwsData?.let { newsList.add(it) }
                    }
                    filteredList.clear()
                    filteredList.addAll(newsList)

                    val mAdapter = UserNewsAdapter(filteredList)
                    newsRecyclerView.adapter = mAdapter

                    mAdapter.setOnItemClickListener(object : UserNewsAdapter.onItemClickListner {
                        override fun onItemClick(position: Int) {
                            val intent = Intent(this@FetchingNewsUser, NewsDetailActivityUser::class.java)
                            intent.putExtra("nwsID", filteredList[position].newsId)
                            intent.putExtra("nwsTitle", filteredList[position].newstitle)
                            intent.putExtra("nwsDescription", filteredList[position].newsdescript)
                            startActivity(intent)
                        }
                    })




                    newsRecyclerView.visibility = View.VISIBLE
                    tvLoadingData.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle database error
            }
        })
    }

    private fun filter(query: String) {
        filteredList.clear()
        for (news in newsList) {
            if (news.newstitle?.toLowerCase(Locale.getDefault())?.contains(query) == true) {
                filteredList.add(news)
            }
        }
        newsRecyclerView.adapter?.notifyDataSetChanged()
    }

}
