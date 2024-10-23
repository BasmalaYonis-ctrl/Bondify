package com.example.newbondify

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class SecondMainActivity : AppCompatActivity() {

    private var player1 = ArrayList<Int>()
    private var player2 = ArrayList<Int>()
    var activePlayer = 1
    var isAi = false
    var difficulty = "easy"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second_main)
        isAi = intent.getBooleanExtra("isAi", false)
        difficulty = intent.getStringExtra("difficulty") ?: "easy"
    }

    fun butClicked(view: View) {
        val butSelected = view as Button
        var cellId = 0
        when (butSelected.id) {
            R.id.button1 -> cellId = 1
            R.id.button2 -> cellId = 2
            R.id.button3 -> cellId = 3
            R.id.button4 -> cellId = 4
            R.id.button5 -> cellId = 5
            R.id.button6 -> cellId = 6
            R.id.button7 -> cellId = 7
            R.id.button8 -> cellId = 8
            R.id.button9 -> cellId = 9
        }
        playGame(cellId, butSelected)
    }

    private fun playGame(cellId: Int, butSelected: Button) {
        if (activePlayer == 1) {
            butSelected.setBackgroundColor(Color.parseColor("#009193"))
            butSelected.background = ContextCompat.getDrawable(this, R.drawable.ic_x)
            player1.add(cellId)
            activePlayer = 2
            checkWinner()

            if (isAi && player1.size + player2.size < 9 && activePlayer == 2) {
                aiMove()
            }

        } else {
            butSelected.background = ContextCompat.getDrawable(this, R.drawable.ic_o)
            player2.add(cellId)
            activePlayer = 1
            checkWinner()
        }
        butSelected.isEnabled = false
    }

    private fun aiMove() {
        if (difficulty == "easy") {
            randomAiMove()
        } else {
            hardAiMove()
        }
    }

    private fun randomAiMove() {
        val emptyCells = ArrayList<Int>()
        for (cellId in 1..9) {
            if (!(player1.contains(cellId) || player2.contains(cellId))) {
                emptyCells.add(cellId)
            }
        }

        if (emptyCells.isNotEmpty()) {
            val randIndex = (0 until emptyCells.size).random()
            val cellId = emptyCells[randIndex]

            val selectedButton: Button? = when (cellId) {
                1 -> findViewById(R.id.button1)
                2 -> findViewById(R.id.button2)
                3 -> findViewById(R.id.button3)
                4 -> findViewById(R.id.button4)
                5 -> findViewById(R.id.button5)
                6 -> findViewById(R.id.button6)
                7 -> findViewById(R.id.button7)
                8 -> findViewById(R.id.button8)
                9 -> findViewById(R.id.button9)
                else -> null
            }

            selectedButton?.let {
                playGame(cellId, it)
            }
        }
    }


    private fun hardAiMove() {
        val winningMove = findWinningMove(player2)
        if (winningMove != -1) {
            makeAiMove(winningMove)
            return
        }

        val blockingMove = findWinningMove(player1)
        if (blockingMove != -1) {
            makeAiMove(blockingMove)
            return
        }

        randomAiMove()
    }


    private fun findWinningMove(player: ArrayList<Int>): Int {
        val winningCombinations = arrayOf(
            arrayOf(1, 2, 3),
            arrayOf(4, 5, 6),
            arrayOf(7, 8, 9),
            arrayOf(1, 4, 7),
            arrayOf(2, 5, 8),
            arrayOf(3, 6, 9),
            arrayOf(1, 5, 9),
            arrayOf(3, 5, 7)
        )

        for (combination in winningCombinations) {
            val (a, b, c) = combination
            if (player.contains(a) && player.contains(b) && !player1.contains(c) && !player2.contains(c)) {
                return c
            }
            if (player.contains(a) && player.contains(c) && !player1.contains(b) && !player2.contains(b)) {
                return b
            }
            if (player.contains(b) && player.contains(c) && !player1.contains(a) && !player2.contains(a)) {
                return a
            }
        }
        return -1
    }

    private fun makeAiMove(cellId: Int) {
        val selectedButton: Button? = when (cellId) {
            1 -> findViewById(R.id.button1)
            2 -> findViewById(R.id.button2)
            3 -> findViewById(R.id.button3)
            4 -> findViewById(R.id.button4)
            5 -> findViewById(R.id.button5)
            6 -> findViewById(R.id.button6)
            7 -> findViewById(R.id.button7)
            8 -> findViewById(R.id.button8)
            9 -> findViewById(R.id.button9)
            else -> null
        }

        selectedButton?.let {
            playGame(cellId, it)
        }
    }

    private fun checkWinner() {
        val winningCombinations = arrayOf(
            arrayOf(1, 2, 3),
            arrayOf(4, 5, 6),
            arrayOf(7, 8, 9),
            arrayOf(1, 4, 7),
            arrayOf(2, 5, 8),
            arrayOf(3, 6, 9),
            arrayOf(1, 5, 9),
            arrayOf(3, 5, 7)
        )

        var winner = -1
        for (combination in winningCombinations) {
            val (a, b, c) = combination
            if (player1.contains(a) && player1.contains(b) && player1.contains(c)) {
                winner = 1
            } else if (player2.contains(a) && player2.contains(b) && player2.contains(c)) {
                winner = 2
            }
        }

        if (winner != -1) {
            val intent = Intent(this, Winner::class.java)
            Winner.winnerPlayerTxt = when {
                winner == 1 -> "Player 1 Wins!"
                winner == 2 && isAi -> "You lost to the AI!"
                winner == 2 -> "Player 2 Wins!"
                else -> ""
            }
            startActivity(intent)
            resetGame()
        }
        else if (player1.size + player2.size == 9) {
            val intent = Intent(this, Winner::class.java)
            Winner.winnerPlayerTxt = "It's a Draw!"
            startActivity(intent)
            resetGame()
        }
    }

    private fun resetGame() {
        player1.clear()
        player2.clear()
        activePlayer = 1

        for (cellId in 1..9) {
            val button: Button? = when (cellId) {
                1 -> findViewById(R.id.button1)
                2 -> findViewById(R.id.button2)
                3 -> findViewById(R.id.button3)
                4 -> findViewById(R.id.button4)
                5 -> findViewById(R.id.button5)
                6 -> findViewById(R.id.button6)
                7 -> findViewById(R.id.button7)
                8 -> findViewById(R.id.button8)
                9 -> findViewById(R.id.button9)
                else -> null
            }
            button?.text = ""
            button?.isEnabled = true
            button?.background = ContextCompat.getDrawable(this, R.color.default_button_color)
        }
    }
}
