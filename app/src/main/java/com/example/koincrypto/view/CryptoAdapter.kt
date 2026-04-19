package com.example.koincrypto.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.koincrypto.databinding.RecyclerRowBinding
import com.example.koincrypto.model.CryptoModel
import androidx.core.graphics.toColorInt

class RecyclerViewAdapter(private val cryptoList : ArrayList<CryptoModel>
, private val listener : Listener
) : RecyclerView.Adapter<RecyclerViewAdapter.RowHolder>() {

    interface Listener {
        fun onItemClick(cryptoModel: CryptoModel)
    }
    private val colors: Array<String> = arrayOf(
        "#FF5252",
        "#7C4DFF",
        "#00E676",
        "#00B0FF",
        "#FFEA00",
        "#FF4081",
        "#18FFFF",
        "#FF9100"
    )
    class RowHolder(val binding : RecyclerRowBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowHolder {
        val itemBinding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RowHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return cryptoList.count()
    }

    override fun onBindViewHolder(holder: RowHolder, position: Int) {

        holder.itemView.setOnClickListener {
            listener.onItemClick(cryptoList.get(position))
        }
        holder.itemView.setBackgroundColor(colors[position % 8].toColorInt())
        holder.binding.cryptoNameText.text = cryptoList[position].currency
        holder.binding.cryptoPriceText.text = cryptoList[position].price
    }

}