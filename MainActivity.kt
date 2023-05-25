package com.example.myapplication_mark

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.myapplication_mark.CoinListActivity

class MainActivity : AppCompatActivity() {
    private lateinit var showCoinListButton: Button
    private lateinit var showFavoritesButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showCoinListButton = findViewById(R.id.showCoinListButton)
        showFavoritesButton = findViewById(R.id.showFavoritesButton)

        showCoinListButton.setOnClickListener {
            val intent = Intent(this, CoinListActivity::class.java)
            startActivity(intent)
        }

        showFavoritesButton.setOnClickListener {
            val intent = Intent(this, FavoritesActivity::class.java)
            startActivity(intent)
        }
    }
}