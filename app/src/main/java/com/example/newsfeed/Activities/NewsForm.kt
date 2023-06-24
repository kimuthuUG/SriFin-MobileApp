package com.example.newsfeed.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.newsfeed.Model.NewsModal
import com.example.newsfeed.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class NewsForm : AppCompatActivity() {

    private lateinit var newsTitle : EditText
    private lateinit var newsDes : EditText

    private lateinit var addNews : Button

    private lateinit var dbRef: DatabaseReference

    //add data
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_form)

        newsTitle = findViewById(R.id.editTextTextPersonName)
        newsDes = findViewById(R.id.editTextTextPersonName2)

        addNews = findViewById(R.id.button3)

        dbRef = FirebaseDatabase.getInstance().getReference("News")

        addNews.setOnClickListener {
            saveNewsData()
        }


    }

    //data save in to database
    private fun saveNewsData(){
        //getting values
        val titleName = newsTitle.text.toString()
        val desName= newsDes.text.toString()

        // validate title and description
        if (titleName.isEmpty()){
            newsTitle.error = "please enter title"
        }
        if (desName.isEmpty()){
            newsDes.error="please enter description"
        }

        val newsId = dbRef.push().key!!

        val news = NewsModal(newsId, titleName , desName  )


        dbRef.child(newsId).setValue(news)
            .addOnCompleteListener{
                Toast.makeText(this,"data insert successfully",Toast.LENGTH_LONG).show()

                if (newsTitle.text.isNotEmpty() && newsDes.text.isNotEmpty()) {
                        newsTitle.text.clear()
                        newsDes.text.clear()
                    }
            }.addOnFailureListener{
                Toast.makeText(this,"Error ",Toast.LENGTH_LONG).show()

            }




    }

}