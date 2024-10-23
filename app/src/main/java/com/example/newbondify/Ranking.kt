package com.example.newbondify

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class Ranking : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var playerAdapter: PlayerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_ranking)

        val players = listOf(
            Player("John Smith", 3250, R.drawable.baseline_person_24),
            Player("Emily Johnson", 3015, R.drawable.baseline_person_24),
            Player("David Brown", 2970, R.drawable.baseline_person_24),
            Player("Sarah Wilson", 2870, R.drawable.baseline_person_24),
            Player("Michael Davis", 2850, R.drawable.baseline_person_24),
            Player("Jessica Taylor", 2800, R.drawable.baseline_person_24),
            Player("Christopher Anderson", 2750, R.drawable.baseline_person_24),
            Player("Olivia Martinez", 2700, R.drawable.baseline_person_24),
            Player("Daniel Thomas", 2650, R.drawable.baseline_person_24),
            Player("Sophia Clark", 2600, R.drawable.baseline_person_24)
        )

        // Set up RecyclerView
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        playerAdapter = PlayerAdapter(players)
        recyclerView.adapter = playerAdapter

        // Bottom Navigation View
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        // Set the selected item to Ranking
        bottomNavigationView.selectedItemId = R.id.nav_save

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
                    // Stay on Ranking Activity (No action needed)
                    true
                }
                R.id.nav_account -> {
                    // Navigate to Account Activity
                    val intent = Intent(this, Account::class.java)
                    startActivity(intent)
                    finish() // Optional: finish this activity if you don't want to go back
                    true
                }
                else -> false
            }
        }
    }
}
