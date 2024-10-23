package com.example.newbondify

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class AiDifficultyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ai_difficulty)

        val easyBtn = findViewById<Button>(R.id.easyBtn)
        val hardBtn = findViewById<Button>(R.id.hardBtn)
        val backArrowButton: ImageButton = findViewById(R.id.backArrowButton)

        backArrowButton.setOnClickListener {
            val intent = Intent(this,MainMenuActivity::class.java)
            startActivity(intent)
        }

        easyBtn.setOnClickListener {
            val intent = Intent(this, SecondMainActivity::class.java)
            intent.putExtra("isAi", true)
            intent.putExtra("difficulty", "easy")
            startActivity(intent)
        }

        hardBtn.setOnClickListener {
            val intent = Intent(this, SecondMainActivity::class.java)
            intent.putExtra("isAi", true)
            intent.putExtra("difficulty", "hard")
            startActivity(intent)
        }
    }
}
