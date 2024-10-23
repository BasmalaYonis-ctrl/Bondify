package com.example.newbondify

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.newbondify.network.RetrofitInstance
import com.example.newbondify.network.TruthOrDareQuestion
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GameScreenActivity : AppCompatActivity() {

    private lateinit var scoreContainer: LinearLayout
    private lateinit var currentPlayerName: TextView
    private lateinit var questionTypeValue: TextView
    private lateinit var questionText: TextView
    private lateinit var answerTruthButton: Button
    private lateinit var skipToDareButton: Button
    private lateinit var nextPlayerButton: Button
    private lateinit var gameOverLabel: TextView
 private lateinit var boxQuestion: LinearLayout
    private val playerScores: MutableMap<String, Int> = mutableMapOf()
    private val playerQuestionCount: MutableMap<String, Int> = mutableMapOf()
    private var playerNames: ArrayList<String> = ArrayList()
    private var currentPlayerIndex = 0
    private var numberOfQuestionsPerPlayer = 0
    private var totalQuestionsAsked = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_screen)
        boxQuestion =findViewById(R.id.boxQuestion)
        scoreContainer = findViewById(R.id.scoreContainer)
        currentPlayerName = findViewById(R.id.currentPlayerName)
        questionTypeValue = findViewById(R.id.questionTypeValue)
        questionText = findViewById(R.id.questionText)
        answerTruthButton = findViewById(R.id.answerTruthButton)
        skipToDareButton = findViewById(R.id.skipToDareButton)
        nextPlayerButton = findViewById(R.id.nextPlayerButton)
        gameOverLabel = findViewById(R.id.gameOverLabel)

        // Retrieve player names and number of questions per player from the intent
        playerNames = intent.getStringArrayListExtra("PLAYER_NAMES") ?: ArrayList()
        numberOfQuestionsPerPlayer = intent.getIntExtra("NUMBER_OF_QUESTIONS", 0)

        // Initialize player scores and question counts
        for (name in playerNames) {
            playerScores[name] = 0
            playerQuestionCount[name] = 0
            addPlayerScoreView(name)
        }

        // Fetch the initial question for the first player
        updateCurrentPlayer()
        fetchTruthQuestion()

        answerTruthButton.setOnClickListener {
            handleAnswerTruth()
        }

        skipToDareButton.setOnClickListener {
            handleDare()
        }

        nextPlayerButton.setOnClickListener {
            moveToNextPlayer()
            fetchTruthQuestion()
        }
    }

    private fun updateCurrentPlayer() {
        val currentPlayer = playerNames[currentPlayerIndex]
        currentPlayerName.text = currentPlayer
    }

    private fun moveToNextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % playerNames.size
        updateCurrentPlayer()
        checkEndGameCondition()
    }

    private fun addPlayerScoreView(playerName: String) {
        // Adding a TextView for each player's score in scoreContainer
        val playerScoreView = TextView(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            text = "$playerName: 0"
            setPadding(0, 8, 0, 8)
        }
        scoreContainer.addView(playerScoreView)
    }

    private fun updatePlayerScore(index: Int, scoreIncrement: Int) {
        val playerName = playerNames[index]
        playerScores[playerName] = playerScores[playerName]?.plus(scoreIncrement) ?: 0

        // Update the score TextView for the specific player
        val scoreViewIndex = index + 1
        if (scoreViewIndex < scoreContainer.childCount) {
            (scoreContainer.getChildAt(scoreViewIndex) as TextView).text = "$playerName: ${playerScores[playerName]}"
        }
    }

    private fun fetchTruthQuestion() {
        if (totalQuestionsAsked >= numberOfQuestionsPerPlayer * playerNames.size) {
            endGame()
            return
        }

        val call = RetrofitInstance.api.getTruthQuestion()
        call.enqueue(object : Callback<TruthOrDareQuestion> {
            override fun onResponse(call: Call<TruthOrDareQuestion>, response: Response<TruthOrDareQuestion>) {
                if (response.isSuccessful) {
                    val questionResponse = response.body()
                    if (questionResponse != null) {
                        questionTypeValue.text = questionResponse.type
                        questionText.text = questionResponse.question
                    } else {
                        Toast.makeText(this@GameScreenActivity, "Failed to load question", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@GameScreenActivity, "Failed to load question", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<TruthOrDareQuestion>, t: Throwable) {
                Toast.makeText(this@GameScreenActivity, "Failed to load question", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun handleAnswerTruth() {
        // Update the score for the current player only when answering "Truth"
        val currentPlayer = playerNames[currentPlayerIndex]
        updatePlayerScore(currentPlayerIndex, 1)

        // Update the question count for the current player
        playerQuestionCount[currentPlayer] = playerQuestionCount[currentPlayer]?.plus(1) ?: 1
        totalQuestionsAsked++

        // Move to the next player after answering truth
        moveToNextPlayer()
        fetchTruthQuestion()
    }

    private fun handleDare() {
        // Just show the dare question without incrementing the score
        val call = RetrofitInstance.api.getDareQuestion()
        call.enqueue(object : Callback<TruthOrDareQuestion> {
            override fun onResponse(call: Call<TruthOrDareQuestion>, response: Response<TruthOrDareQuestion>) {
                if (response.isSuccessful) {
                    val questionResponse = response.body()
                    if (questionResponse != null) {
                        questionTypeValue.text = questionResponse.type
                        questionText.text = questionResponse.question
                    } else {
                        Toast.makeText(this@GameScreenActivity, "Failed to load dare", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@GameScreenActivity, "Failed to load dare", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<TruthOrDareQuestion>, t: Throwable) {
                Toast.makeText(this@GameScreenActivity, "Failed to load dare", Toast.LENGTH_SHORT).show()
            }
        })

        // Enable the next player button for proceeding
        nextPlayerButton.visibility = View.VISIBLE
        answerTruthButton.visibility = View.GONE
        skipToDareButton.visibility = View.GONE

        // Update the question count without scoring
        val currentPlayer = playerNames[currentPlayerIndex]
        playerQuestionCount[currentPlayer] = playerQuestionCount[currentPlayer]?.plus(1) ?: 1
        totalQuestionsAsked++
    }

    private fun checkEndGameCondition() {
        if (totalQuestionsAsked >= numberOfQuestionsPerPlayer * playerNames.size) {
            endGame()
        } else {
            answerTruthButton.visibility = View.VISIBLE
            skipToDareButton.visibility = View.VISIBLE
            nextPlayerButton.visibility = View.GONE
        }
    }

    private fun endGame() {
        // Hide all question-related views
        boxQuestion.visibility=View.GONE
        currentPlayerName.visibility = View.GONE
        questionTypeValue.visibility = View.GONE
        questionText.visibility = View.GONE
        answerTruthButton.visibility = View.GONE
        skipToDareButton.visibility = View.GONE
        nextPlayerButton.visibility = View.GONE

        // Show the "Game Over" label and the scores
        gameOverLabel.visibility = View.VISIBLE
        scoreContainer.visibility = View.VISIBLE

        // Display the "Game Over" message
        gameOverLabel.text = "Game Over! Here are the final scores:"
    }
}