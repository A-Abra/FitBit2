package com.example.fitbit

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_food)

        val button = findViewById<Button>(R.id.btnAddFood)
        button.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(findViewById<EditText>(R.id.foodInput).text)
            /* && TextUtils.isEmpty(findViewById<EditText>(R.id.etAddCal).text) */) {

                setResult(Activity.RESULT_CANCELED, replyIntent)

            } else {
                val food = findViewById<EditText>(R.id.foodInput).text.toString()
                val calories = findViewById<EditText>(R.id.calInput).text.toString()

                replyIntent.putExtra(EXTRA_FOOD, food)
                replyIntent.putExtra(EXTRA_CALORIES,calories)
                setResult(Activity.RESULT_OK, replyIntent)

                /*    val extras = Bundle()
                    extras.putString("EXTRA_FOOD", food)
                    extras.putString("EXTRA_CALORIES", calories)
                    replyIntent.putExtra(EXTRA_REPLY, extras)
                    setResult(Activity.RESULT_OK, replyIntent)
                 */
            }
            finish()
        }
    }

    companion object {
        const val EXTRA_FOOD = "FOOD_EXTRA"
        const val EXTRA_CALORIES = "CALORIES_EXTRA"
    }
}