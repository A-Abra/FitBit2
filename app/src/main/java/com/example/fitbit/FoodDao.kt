package com.example.fitbit

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
@Dao
interface FoodDAO {
    @Query("SELECT * FROM HealthData")
    fun getAll(): Flow<List<FoodItem>>
    @Insert
    fun insert(food: FoodItem)
    @Insert
    fun insertAll(vararg food:FoodItem)
    @Query("SELECT AVG(calories) FROM HealthData")
    fun getAverage(): Int
    @Query("SELECT MIN(Calories) FROM HealthData")
    fun getLowest() : Int
    @Query("SELECT MAX(Calories) FROM HealthData")
    fun getHighest() : Int

    @Query("DELETE FROM HealthData")
    fun deleteAll()

    @Delete
    fun deleteItem(food: FoodItem)
}