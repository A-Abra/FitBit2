package com.example.fitbit

import android.app.*
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import java.util.*


class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_food)

        val imageView: ImageView = findViewById(R.id.imageView)
        val calendar = Calendar.getInstance()
        when (calendar.get(Calendar.DAY_OF_WEEK)) {
            Calendar.SUNDAY -> imageView.setImageResource(R.drawable.nov1)
            Calendar.MONDAY -> imageView.setImageResource(R.drawable.nov2)
            Calendar.TUESDAY -> imageView.setImageResource(R.drawable.nov3)
            Calendar.WEDNESDAY -> imageView.setImageResource(R.drawable.nov4)
            Calendar.THURSDAY -> imageView.setImageResource(R.drawable.nov5)
            Calendar.FRIDAY -> imageView.setImageResource(R.drawable.nov6)
            Calendar.SATURDAY -> imageView.setImageResource(R.drawable.nov7)
            else -> imageView.setImageResource(R.drawable.beet)
        }

        val button = findViewById<Button>(R.id.btnAddFood)
        button.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(findViewById<EditText>(R.id.foodInput).text)
                && TextUtils.isEmpty(findViewById<EditText>(R.id.calInput).text) ) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val food = findViewById<EditText>(R.id.foodInput).text.toString()
                val calories = findViewById<EditText>(R.id.calInput).text.toString()

                replyIntent.putExtra(EXTRA_FOOD, food)
                replyIntent.putExtra(EXTRA_CALORIES,calories)
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }
    }
    companion object {
        const val EXTRA_FOOD = "FOOD_EXTRA"
        const val EXTRA_CALORIES = "CALORIES_EXTRA"
    }
}