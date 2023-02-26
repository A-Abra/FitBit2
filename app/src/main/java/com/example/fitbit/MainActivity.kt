package com.example.fitbit

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.graphics.*
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.*


class MainActivity : AppCompatActivity() {
    private val newFoodActivityRequestCode = 1
    private lateinit var itemViewModel: ItemViewModel
    private var actionEvent: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        actionEvent = findViewById(R.id.actionEvent);

        val recyclerView = findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.rvFood)
        val adapter = FoodAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        itemViewModel = ViewModelProviders.of(this).get(ItemViewModel::class.java)

        itemViewModel.allItems.observe(this, Observer { food ->
            food?.let { adapter.setFood(it) }
        })

        findViewById<Button>(R.id.btnAdd).setOnClickListener{
            val intent = Intent(this, DetailActivity::class.java)
            startActivityForResult(intent, newFoodActivityRequestCode)
        }

    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)

        if (requestCode == newFoodActivityRequestCode && resultCode == Activity.RESULT_OK) {
            intentData?.let { data ->
                // Add new food name into database
                val food = FoodItem(0,data.getStringExtra(DetailActivity.EXTRA_FOOD), data.getStringExtra(DetailActivity.EXTRA_CALORIES)?.toInt())
                itemViewModel.insert(food)
                // Add new calories into database
            }
        } else {
            Toast.makeText(
                applicationContext,
                "Not saved",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}