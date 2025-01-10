package com.example.tictactoe

import android.os.Bundle
import androidx.activity.ComponentActivity
import android.view.View
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView

class MainActivity : ComponentActivity() {

    private lateinit var boardButtons: Array<Button>
    private var currentPlayer = "X"
    private var board = Array(3) { Array(3) { "" } }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        val gridLayout = findViewById<GridLayout>(R.id.board)
        val resultText = findViewById<TextView>(R.id.resultText)
        val playAgainButton = findViewById<Button>(R.id.playAgainButton)

        boardButtons = Array(9) { i ->
            val button = gridLayout.getChildAt(i) as Button
            button.setOnClickListener {
                onButtonClicked(button, i / 3, i % 3, resultText, playAgainButton)
            }
            button
        }

        playAgainButton.setOnClickListener {
            resetGame()
            playAgainButton.visibility = View.GONE
        }
    }

    private fun onButtonClicked(button: Button, row: Int, col: Int, resultText: TextView, playAgainButton: Button) {
        if (board[row][col].isNotEmpty()) return

        board[row][col] = currentPlayer
        button.text = currentPlayer

        when {
            checkWin() -> {
                resultText.text = "Player $currentPlayer Wins!"
                playAgainButton.visibility = View.VISIBLE
                disableBoard()
            }
            isBoardFull() -> {
                resultText.text = "It's a Draw!"
                playAgainButton.visibility = View.VISIBLE
                disableBoard()
            }
            else -> {
                currentPlayer = if (currentPlayer == "X") "O" else "X"
                resultText.text = if (currentPlayer == "X") "X's turn" else "O's turn"
            }
        }
    }

    private fun checkWin(): Boolean {
        // Check rows, columns, and diagonals
        for (i in 0..2) {
            if (board[i][0] == currentPlayer && board[i][1] == currentPlayer && board[i][2] == currentPlayer) return true
            if (board[0][i] == currentPlayer && board[1][i] == currentPlayer && board[2][i] == currentPlayer) return true
        }
        if (board[0][0] == currentPlayer && board[1][1] == currentPlayer && board[2][2] == currentPlayer) return true
        if (board[0][2] == currentPlayer && board[1][1] == currentPlayer && board[2][0] == currentPlayer) return true

        return false
    }

    private fun isBoardFull(): Boolean {
        return board.all { row -> row.all { it.isNotEmpty() } }
    }

    private fun disableBoard() {
        boardButtons.forEach { it.isEnabled = false }
    }

    private fun resetGame() {
        board = Array(3) { Array(3) { "" } }
        currentPlayer = "X"
        findViewById<TextView>(R.id.resultText).text = "X's turn"
        boardButtons.forEach {
            it.text = ""
            it.isEnabled = true
        }
    }
}