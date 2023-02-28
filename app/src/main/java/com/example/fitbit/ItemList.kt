package com.example.fitbit

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

class ItemRepository(private val FoodDao: FoodDAO) {
    val allItems: LiveData<List<FoodItem>> = FoodDao.getAll()
    @WorkerThread
    fun insert(food: FoodItem) {
        FoodDao.insert(food)
    }
}