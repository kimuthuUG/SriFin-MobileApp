package com.example.newsfeed.Activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.newsfeed.Model.NewsModal
import com.example.newsfeed.R
import com.google.firebase.database.FirebaseDatabase

class NewsDetailActivity : AppCompatActivity() {

    private lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_detail)

        //database refferance
        database = FirebaseDatabase.getInstance()

        val newsId = intent.getStringExtra("newsid")
        val newstitle = intent.getStringExtra("newstitle")
        val newsdescription = intent.getStringExtra("newsdescription")

        val titleEditText = findViewById<EditText>(R.id.tvNwsTit)
        val descriptionEditText = findViewById<EditText>(R.id.tvNwsDes)

        titleEditText.setText(newstitle)
        descriptionEditText.setText(newsdescription)

        val docRef = newsId?.let { database.reference.child("News").child(it) }

        val btnUpdate = findViewById<Button>(R.id.btnUpdate)
        val btnDelete = findViewById<Button>(R.id.btnDelete)

        //delete values

        btnDelete.setOnClickListener {
            if (docRef != null) {
                docRef.removeValue()
                    .addOnSuccessListener {
                        Toast.makeText(this, "News deleted successfully", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(this, "Error deleting news: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
            }
        }

        //update the news form
        btnUpdate.setOnClickListener {
            val updatedTitle = titleEditText.text.toString()
            val updatedDescription = descriptionEditText.text.toString()

            val updatedNews = NewsModal(newsId, updatedTitle, updatedDescription)

            if (docRef != null) {
                docRef.setValue(updatedNews)
                    .addOnSuccessListener {
                        Toast.makeText(this, "News updated successfully", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(this, "Error updating news: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
            }
        }
    }
}
