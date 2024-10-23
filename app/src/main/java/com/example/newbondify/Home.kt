package com.example.newbondify

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.cardview.widget.CardView

class Home : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        val cardSinglePlayer = findViewById<CardView>(R.id.card_single_player)
        val cardMultiPlayers = findViewById<CardView>(R.id.card_multi_players)

        // Set up Bottom Navigation item selected listener
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    // Stay on Home
                    true
                }
                R.id.nav_save -> {
                    // Navigate to Ranking Activity
                    val intent = Intent(this, Ranking::class.java)
                    startActivity(intent)
                    true
                }
                R.id.nav_account -> {
                    // Navigate to Account Activity
                    val intent = Intent(this, Account::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }

        // Set up onClickListeners for card views
        cardSinglePlayer.setOnClickListener {
            // Navigate to Single Player Activity
            val intent = Intent(this, SinglePlayerActivity::class.java)
            startActivity(intent)
        }

        cardMultiPlayers.setOnClickListener {
            // Navigate to Multi Players Activity
            val intent = Intent(this, MultiPlayersActivity::class.java)
            startActivity(intent)
        }
    }
}
