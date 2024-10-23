package com.example.newbondify

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class Account : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_account)

        // Bottom Navigation View
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.selectedItemId = R.id.nav_account

        // Set up navigation item selected listener
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    // Navigate to Home Activity
                    val intent = Intent(this, Home::class.java)
                    startActivity(intent)
                    finish() // Optional: finish this activity if you don't want to go back
                    true
                }
                R.id.nav_save -> {
                    // Navigate to Ranking Activity
                    val intent = Intent(this, Ranking::class.java)
                    startActivity(intent)
                    finish() // Optional: finish this activity if you don't want to go back
                    true
                }
                R.id.nav_account -> {
                    // Stay on Account Activity (No action needed)
                    true
                }
                else -> false
            }
        }
    }
}
