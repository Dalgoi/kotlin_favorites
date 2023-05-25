package com.example.myapplication_mark

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class FavoritesActivity : AppCompatActivity() {

    private lateinit var favoritesRecyclerView: RecyclerView
    private lateinit var favoritesAdapter: CoinListAdapter
    private lateinit var favoritesList: MutableList<Coin>

    private lateinit var database: FirebaseDatabase
    private lateinit var favoritesRef: DatabaseReference
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        firebaseAuth = FirebaseAuth.getInstance()
        val currentUser = firebaseAuth.currentUser
        val userId = currentUser?.uid
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)

        favoritesRecyclerView = findViewById(R.id.favoritesRecyclerView)

        // Firebase 초기화
        database = FirebaseDatabase.getInstance()
        favoritesRef = database.reference.child("users")
            .child(FirebaseAuth.getInstance().currentUser?.uid ?: "")
            .child("favorites")

        favoritesList = mutableListOf()
        favoritesAdapter = CoinListAdapter(favoritesList)

        favoritesRecyclerView.layoutManager = LinearLayoutManager(this)
        favoritesRecyclerView.adapter = favoritesAdapter

        // 데이터베이스에서 즐겨찾기 목록을 가져와서 RecyclerView에 표시
        favoritesRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                favoritesList.clear()
                for (coinSnapshot in snapshot.children) {
                    val coin = coinSnapshot.getValue(Coin::class.java)
                    coin?.let {
                        favoritesList.add(coin)
                    }
                }
                favoritesAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                // 처리 중단
            }
        })

        favoritesAdapter.setOnFavoritesClickListener { coin ->
            toggleFavorite(coin)
        }
    }

    private fun toggleFavorite(coin: Coin) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return // userId가 null인 경우 함수 중단
        val coinRef = database.reference.child("users").child(userId).child("favorites")

        if (favoritesList.contains(coin)) {
            favoritesList.remove(coin)
            coinRef.child(coin.id).removeValue() // 즐겨찾기에서 제거
            Toast.makeText(this, "${coin.name} removed from favorites", Toast.LENGTH_SHORT)
                .show()
        } else {
            favoritesList.add(coin)
            coinRef.child(coin.id).setValue(coin) // 즐겨찾기에 추가
            Toast.makeText(this, "${coin.name} added to favorites", Toast.LENGTH_SHORT)
                .show()
        }
        favoritesAdapter.notifyDataSetChanged()
    }
}