package com.example.newsfeed.Activities

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

class NewsDetailActivityUser : AppCompatActivity() {

    private lateinit var tvNwsId: TextView
    private lateinit var tvNwsTit: TextView
    private lateinit var tvNwsDes: TextView
    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_details_user)
        initView()
        setValuesToViews()

        btnUpdate.setOnClickListener {
            openUpdateDialog(
                intent.getStringExtra("nwsId").toString(),
                intent.getStringExtra("nwsTitle").toString()
            )
        }
        btnDelete.setOnClickListener {
            deleteRecord(
                intent.getStringExtra("nwsId").toString()
            )
        }
    }

    private fun deleteRecord(id: String) {
        val dbRef = FirebaseDatabase.getInstance().getReference("News").child(id)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this, "News data deleted", Toast.LENGTH_LONG).show()
            val intent = Intent(this, FetchingNews::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener { error ->
            Toast.makeText(this, "Deleting Error ${error.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun initView() {
        tvNwsId = findViewById(R.id.tvNwsId)
        tvNwsTit = findViewById(R.id.tvNwsTit)
        tvNwsDes = findViewById(R.id.tvNwsDes)
        btnUpdate = findViewById(R.id.btnUpdate)
        btnDelete = findViewById(R.id.btnDelete)
    }

    private fun setValuesToViews() {
        tvNwsId.text = intent.getStringExtra("nwsId")
        tvNwsTit.text = intent.getStringExtra("nwsTitle")
        tvNwsDes.text = intent.getStringExtra("nwsDescription")
    }

    private fun openUpdateDialog(newsId: String, nwsName: String) {
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.update_dialog, null)

        mDialog.setView(mDialogView)

        val etNwsTitle = mDialogView.findViewById<EditText>(R.id.etNwsTitle)
        val etNwsDes = mDialogView.findViewById<EditText>(R.id.etNwsDes)
        val btnUpdateData = mDialogView.findViewById<Button>(R.id.btnUpdateData)

        etNwsTitle.setText(intent.getStringExtra("nwsTitle").toString())
        etNwsDes.setText(intent.getStringExtra("nwsDescription").toString())

        mDialog.setTitle("Updating $nwsName Record")

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnUpdateData.setOnClickListener {
            val updatedId = intent.getStringExtra("nwsId").toString()
            updateNwsData(
                updatedId,
                etNwsTitle.text.toString(),
                etNwsDes.text.toString()
            )
            alertDialog.dismiss()
        }
    }

    private fun updateNwsData(id: String, title: String, description: String) {
        val dbRef = FirebaseDatabase.getInstance().getReference("News").child(id)
        val nwsInfo = NewsModal(id, title, description)

        dbRef.setValue(nwsInfo).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(applicationContext, "News data updated", Toast.LENGTH_LONG).show()

                // Update the TextViews with the new data
                tvNwsTit.text = title
                tvNwsDes.text = description
            } else {
                Toast.makeText(applicationContext, "Failed to update news data", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }
}