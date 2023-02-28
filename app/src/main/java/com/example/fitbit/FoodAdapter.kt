package com.example.fitbit

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FoodAdapter internal constructor(context: Context) : RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var food = mutableListOf<FoodItem>()
    inner class FoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val foodItemView: TextView = itemView.findViewById(R.id.foodItemtv)
        val numCalView: TextView = itemView.findViewById(R.id.numCaltv)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val itemView = inflater.inflate(R.layout.food_item, parent, false)
        return FoodViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val current = food[position]
        holder.foodItemView.text = current.foodname
        holder.numCalView.text = current.calories.toString()
    }
    @SuppressLint("NotifyDataSetChanged")
    internal fun setFood(food: List<FoodItem>) {
        this.food = food as MutableList<FoodItem>
        notifyDataSetChanged()
    }
    override fun getItemCount() = food.size
}