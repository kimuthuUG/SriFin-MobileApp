package com.example.newsfeed.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.newsfeed.R

class MainActivity : AppCompatActivity() {

    private lateinit var btnInsertData : Button
    private lateinit var btnFetchData : Button
    private lateinit var btnUserView :Button

    //add values
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnInsertData = findViewById(R.id.button)
        btnFetchData = findViewById(R.id.button2)
        btnUserView = findViewById(R.id.userview)

        //buttons for add fetch and user view

        btnInsertData.setOnClickListener {

            var intent = Intent(this, NewsForm::class.java)
            startActivity(intent)
        }
        btnFetchData.setOnClickListener {

            var intent = Intent(this, FetchingNews::class.java)
            startActivity(intent)
        }
        btnUserView.setOnClickListener {
            var intent = Intent(this, FetchingNewsUser::class.java)
            startActivity(intent)

        }

    }
}