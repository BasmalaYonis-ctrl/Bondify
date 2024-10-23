package com.example.newbondify

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class MainMenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)

        val playWithFriendBtn = findViewById<Button>(R.id.playWithFriendBtn)
        val playWithAiBtn = findViewById<Button>(R.id.playWithAiBtn)
        val backArrowButton: ImageButton = findViewById(R.id.backArrowButton)

        backArrowButton.setOnClickListener {
            val intent = Intent(this, SelectActivity::class.java)
            startActivity(intent)
        }

        playWithFriendBtn.setOnClickListener {
            val intent = Intent(this, SecondMainActivity::class.java)
            intent.putExtra("isAi", false)
            startActivity(intent)
        }

        playWithAiBtn.setOnClickListener {
            val intent = Intent(this, AiDifficultyActivity::class.java)
            startActivity(intent)
        }
    }
}
