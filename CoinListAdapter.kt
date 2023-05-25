package com.example.myapplication_mark

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CoinListAdapter(private val coinList: List<Coin>) : RecyclerView.Adapter<CoinListAdapter.ViewHolder>() {
    private var onFavoritesClickListener: ((Coin) -> Unit)? = null

    fun setOnFavoritesClickListener(listener: (Coin) -> Unit) {
        onFavoritesClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_coin, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val coin = coinList[position]
        holder.bind(coin)
    }

    override fun getItemCount(): Int {
        return coinList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val coinNameTextView: TextView = itemView.findViewById(R.id.coinNameTextView)
        private val coinSymbolTextView: TextView = itemView.findViewById(R.id.coinSymbolTextView)
        private val favoritesButton: Button = itemView.findViewById(R.id.favoritesButton)

        fun bind(coin: Coin) {
            coinNameTextView.text = coin.name
            coinSymbolTextView.text = coin.symbol

            favoritesButton.setOnClickListener {
                onFavoritesClickListener?.invoke(coin)
            }
        }
    }
}