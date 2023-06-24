package com.example.newsfeed.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.newsfeed.Model.NewsModal
import com.example.newsfeed.R

class UserNewsAdapter (private var nwsList : ArrayList<NewsModal>) : RecyclerView.Adapter<UserNewsAdapter.ViewHolder>() {

    private lateinit var mListner: onItemClickListner

    interface onItemClickListner{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(clickListner: onItemClickListner){
        mListner = clickListner
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.news_list_items_user,parent,false)
        return ViewHolder(itemView,mListner)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentNws = nwsList[position]
        holder.tvNwsName.text=currentNws.newstitle
    }


    override fun getItemCount(): Int{
        return nwsList.size
    }
    class ViewHolder ( itemView: View,clickListner: onItemClickListner) :RecyclerView.ViewHolder(itemView){

        val tvNwsName : TextView = itemView.findViewById(R.id.tvNwsName)

        init {
            itemView.setOnClickListener(){
                clickListner.onItemClick(adapterPosition)
            }
        }
    }
}