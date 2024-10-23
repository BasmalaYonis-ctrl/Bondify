package com.example.newbondify

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MultiPlayersActivity : AppCompatActivity() {
    private lateinit var numberOfPlayersInput: EditText
    private lateinit var playersContainer: LinearLayout
    private lateinit var numberOfQuestionsInput: EditText
    private val playerNameFields: ArrayList<EditText> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_multi_players)

        numberOfPlayersInput = findViewById(R.id.numberOfPlayersInput)
        playersContainer = findViewById(R.id.playersContainer)
        numberOfQuestionsInput = findViewById(R.id.numberOfQuestionsInput)
        val updatePlayersButton: Button = findViewById(R.id.updatePlayersButton)
        val startGameButton: Button = findViewById(R.id.startGameButton)

        updatePlayersButton.setOnClickListener {
            updatePlayerFields()
        }

        startGameButton.setOnClickListener {
            startGame()
        }
    }

    private fun updatePlayerFields() {
        playersContainer.removeAllViews()
        playerNameFields.clear()

        val numPlayersString = numberOfPlayersInput.text.toString()
        if (TextUtils.isEmpty(numPlayersString)) {
            Toast.makeText(this, "Please enter a valid number of players", Toast.LENGTH_SHORT).show()
            return
        }

        val numPlayers = try {
            numPlayersString.toInt()
        } catch (e: NumberFormatException) {
            Toast.makeText(this, "Please enter a valid number", Toast.LENGTH_SHORT).show()
            return
        }

        if (numPlayers <= 0) {
            Toast.makeText(this, "Number of players must be greater than 0", Toast.LENGTH_SHORT).show()
            return
        }

        for (i in 1..numPlayers) {
            val playerEditText = EditText(this).apply {
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                hint = "Enter Player $i's name"
            }
            playersContainer.addView(playerEditText)
            playerNameFields.add(playerEditText)
        }
    }

    private fun startGame() {
        val playerNames: ArrayList<String> = ArrayList()
        for (editText in playerNameFields) {
            val playerName = editText.text.toString().trim()
            if (playerName.isEmpty()) {
                Toast.makeText(this, "Please enter all player names", Toast.LENGTH_SHORT).show()
                return
            }
            playerNames.add(playerName)
        }

        val numberOfQuestionsString = numberOfQuestionsInput.text.toString()
        if (TextUtils.isEmpty(numberOfQuestionsString)) {
            Toast.makeText(this, "Please enter the number of questions", Toast.LENGTH_SHORT).show()
            return
        }

        val numberOfQuestions = try {
            numberOfQuestionsString.toInt()
        } catch (e: NumberFormatException) {
            Toast.makeText(this, "Please enter a valid number of questions", Toast.LENGTH_SHORT).show()
            return
        }

        if (numberOfQuestions <= 0) {
            Toast.makeText(this, "Number of questions must be greater than 0", Toast.LENGTH_SHORT).show()
            return
        }

        val intent = Intent(this@MultiPlayersActivity, GameScreenActivity::class.java)
        intent.putStringArrayListExtra("PLAYER_NAMES", playerNames)
        intent.putExtra("NUMBER_OF_QUESTIONS", numberOfQuestions)
        startActivity(intent)
        }
}