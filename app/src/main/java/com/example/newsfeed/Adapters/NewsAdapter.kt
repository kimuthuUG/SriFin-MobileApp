package com.example.newsfeed.Adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.newsfeed.Activities.NewsDetailActivity
import com.example.newsfeed.Model.NewsModal
import com.example.newsfeed.R

interface OnItemClickListener {
    fun onItemClick(position: Int)
}

class NewsAdapter(private var newsList: ArrayList<NewsModal>) : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    private lateinit var listener: OnItemClickListener

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.nws_list_items, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentNews = newsList[position]
        holder.bind(currentNews)
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val tvNewsName: TextView = itemView.findViewById(R.id.tvNwsName)

        fun bind(currentNews: NewsModal) {
            tvNewsName.text = currentNews.newstitle

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, NewsDetailActivity::class.java)
                intent.putExtra("newsid", currentNews.newsId)
                intent.putExtra("newsdescription", currentNews.newsdescript)
                intent.putExtra("newstitle", currentNews.newstitle)
                itemView.context.startActivity(intent)
            }

            itemView.setOnLongClickListener {
                listener.onItemClick(adapterPosition)
                true
            }
        }
    }
}
