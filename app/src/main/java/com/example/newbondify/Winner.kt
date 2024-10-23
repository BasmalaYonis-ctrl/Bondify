package com.example.newbondify

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageView
import android.widget.TextView
import android.widget.Button
import android.widget.ImageButton

class Winner : AppCompatActivity() {
    companion object {
        var winnerPlayerTxt: String = ""

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_winner)

        val winnerTextView: TextView = findViewById(R.id.resultTxt)
        val winnerImageView: ImageView = findViewById(R.id.imageView)
        val replayButton: Button = findViewById(R.id.replayBtn)
        val backArrowButton: ImageButton = findViewById(R.id.backArrowButton)


        winnerTextView.text = winnerPlayerTxt

        if (winnerPlayerTxt == "You lost to the AI!") {
            winnerImageView.setImageResource(R.drawable.loss)
        } else {
            winnerImageView.setImageResource(R.drawable.winner)
        }


        replayButton.setOnClickListener {
            val intent = Intent(this,MainMenuActivity ::class.java)
            startActivity(intent)
            finish()
        }
        backArrowButton.setOnClickListener {
            val intent = Intent(this, SelectActivity::class.java)
            startActivity(intent)
        }

    }
}
