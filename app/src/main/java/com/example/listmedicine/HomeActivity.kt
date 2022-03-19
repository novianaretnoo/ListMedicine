package com.example.listmedicine

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val intentButton : Button = findViewById(R.id.buttonIntent)
        intentButton.setOnClickListener {viewDetail()}
    }

    private fun viewDetail() {
        val intent = Intent(this, TambahActivity::class.java)
        startActivity(intent)
    }
}