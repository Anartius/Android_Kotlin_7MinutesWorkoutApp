package com.example.a7minutesworkoutapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.a7minutesworkoutapp.databinding.ItemHistoryBinding

class HistoryItemAdapter(private val items: List<HistoryEntity>):
    RecyclerView.Adapter<HistoryItemAdapter.ViewHolder>() {

    class ViewHolder(binding: ItemHistoryBinding):
        RecyclerView.ViewHolder(binding.root) {
        val llHistoryItem = binding.llHistoryItem
        val tvId = binding.tvHistoryId
        val tvDate = binding.tvHistoryDate
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemHistoryBinding.inflate(
                LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val context = holder.itemView.context
        val item = items[position]

        holder.tvId.text = item.id.toString()
        holder.tvDate.text = item.date

        holder.llHistoryItem.setBackgroundColor(
            ContextCompat.getColor(
                context,
                if (position % 2 == 0) R.color.light_grey else R.color.white
            )
        )
    }

    override fun getItemCount() = items.size
}