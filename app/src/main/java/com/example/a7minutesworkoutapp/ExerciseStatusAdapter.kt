package com.example.a7minutesworkoutapp

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.a7minutesworkoutapp.databinding.ItemExerciseStatusBinding

class ExerciseStatusAdapter(private val items: MutableList<Exercise>):
    RecyclerView.Adapter<ExerciseStatusAdapter.ViewHolder>() {

    class ViewHolder(binding: ItemExerciseStatusBinding):
        RecyclerView.ViewHolder(binding.root) {
            val tvItem = binding.tvItem
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(ItemExerciseStatusBinding.inflate(
            LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val exercise = items[position]
        holder.tvItem.text = exercise.id.toString()

        when {
            exercise.isSelected -> {
                holder.tvItem.background =
                    ContextCompat.getDrawable(holder.tvItem.context,
                        R.drawable.item_circular_thin_color_accent_border)
            }

            exercise.isCompleted -> {
                holder.tvItem.background =
                    ContextCompat.getDrawable(holder.tvItem.context,
                        R.drawable.item_circular_color_accent_background)
                holder.tvItem.setTextColor(Color.WHITE)
            }

            else -> {
                holder.tvItem.background =
                    ContextCompat.getDrawable(holder.tvItem.context,
                        R.drawable.item_circular_color_gray_background)
            }
        }
    }

    override fun getItemCount() = items.size
 }