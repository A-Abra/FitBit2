package com.example.fitbit

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface HealthDAO {
    @Query("SELECT * FROM HealthData")
    fun getAll(): LiveData<List<FoodItem>>

    @Insert
    fun insert(food: FoodItem)

    @Query("DELETE FROM HealthData")
    fun deleteAll()

    @Delete
    fun deleteItem(food: FoodItem)
}