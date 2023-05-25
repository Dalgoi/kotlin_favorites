package com.example.myapplication_mark

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CoinListActivity : AppCompatActivity() {

    private lateinit var coinRecyclerView: RecyclerView
    private lateinit var coinListAdapter: CoinListAdapter
    private lateinit var favoritesButton: Button

    private val coinList = arrayListOf<Coin>()
    private val favoritesList = arrayListOf<Coin>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coin_list)

        coinRecyclerView = findViewById(R.id.coinRecyclerView)
        favoritesButton = findViewById(R.id.favoritesButton)

        // 코인 목록 생성 (임의의 데이터)
        coinList.add(Coin("Bitcoin", "50,000"))
        coinList.add(Coin("Ethereum", "3,000"))
        coinList.add(Coin("Cardano", "2"))
        coinList.add(Coin("Doge_coin", "0.5"))

        coinListAdapter = CoinListAdapter(coinList)

        coinRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@CoinListActivity)
            adapter = coinListAdapter
        }

        coinListAdapter.setOnFavoritesClickListener { coin ->
            addToFavorites(coin)
        }

        favoritesButton.setOnClickListener {
            navigateToFavorites()
        }
    }

    private fun addToFavorites(coin: Coin) {
        if (!favoritesList.contains(coin)) {
            favoritesList.add(coin)
            Toast.makeText(this, "${coin.name} added to favorites", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "${coin.name} is already in favorites", Toast.LENGTH_SHORT).show()
        }
    }

    private fun navigateToFavorites() {
        val intent = Intent(this, FavoritesActivity::class.java)
        intent.putParcelableArrayListExtra("favoritesList", favoritesList)
        startActivity(intent)
    }
}