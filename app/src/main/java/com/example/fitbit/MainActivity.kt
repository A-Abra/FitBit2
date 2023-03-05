package com.example.fitbit

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomnavigation.BottomNavigationView

const val CHANNEL_ID = "Notifications"
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu)

        createNotificationChannel()
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.fastfood_24)
            .setContentTitle("Hello There")
            .setContentText("Put in your food you diabetic")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setCategory(NotificationCompat.CATEGORY_REMINDER)
        val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notifyThread = Thread {
            while (true) {
                notificationManager.notify(420, builder.build())
                Thread.sleep(86400 * 24)
            }
        }
        notifyThread.start()

        val fragmentManager: FragmentManager = supportFragmentManager

        findViewById<BottomNavigationView>(R.id.bottom_navigation).setOnItemSelectedListener {
                item ->
            var fragmentToShow: Fragment? = null

            when(item.itemId){
                R.id.nav_log -> {
                    // Navigate to list of foods entered
                    fragmentToShow = FoodLogFragment()
                }
                R.id.nav_summary -> {
                    // Navigate to Food data summary
                    fragmentToShow = FoodDataFragment()
                }
            }
            if (fragmentToShow != null){
                fragmentManager.beginTransaction().replace(R.id.flContainer,fragmentToShow).commit()
            }
            true
        }
        findViewById<BottomNavigationView>(R.id.bottom_navigation).selectedItemId = R.id.nav_log
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.app_name)
            val descriptionText = getString(R.string.app_name)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}