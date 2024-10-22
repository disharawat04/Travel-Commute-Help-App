package com.example.wordle

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.wordle.R.id.keyboardLayout

class MainActivity : AppCompatActivity() {

    private lateinit var gridLayout: GridLayout
    private lateinit var keyboardLayout: GridLayout
    private val wordToGuess = "MORAL" // Example word, replace this
    private var currentRow = 0
    private val guesses = mutableListOf<String>()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gridLayout = findViewById(R.id.gridLayout)
        keyboardLayout = findViewById(R.id.keyboardLayout)

        // Initialize the grid with empty TextViews
        initializeGrid()

        // Initialize the keyboard with alphabet buttons
        initializeKeyboard()

        val submitButton: Button = findViewById(R.id.btnSubmit)
        submitButton.setOnClickListener {
            checkGuess()
        }
    }

    private fun initializeGrid() {
        for (i in 0 until 30) { // 6 rows * 5 columns = 30 blocks
            val textView = TextView(this)
            textView.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            textView.text = "" // Empty initially
            textView.setBackgroundResource(R.drawable.block_border) // A drawable for borders
            textView.textSize = 24f
            textView.gravity = android.view.Gravity.CENTER
            gridLayout.addView(textView)
        }
    }

    private fun initializeKeyboard() {
        val letters = ('A'..'Z').toList() // Alphabet letters
        for (letter in letters) {
            val button = Button(this)
            button.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            button.text = letter.toString()
            button.setOnClickListener {
                addLetterToGuess(letter)
            }
            keyboardLayout.addView(button)
        }
    }

    private fun addLetterToGuess(letter: Char) {
        if (guesses.size < 5) { // Only allow 5 letters per guess
            guesses.add(letter.toString())

            // Update UI by filling the current row
            val currentTextViewIndex = currentRow * 5 + guesses.size - 1
            val textView = gridLayout.getChildAt(currentTextViewIndex) as TextView
            textView.text = letter.toString()
        }
    }

    private fun checkGuess() {
        if (guesses.size == 5) {
            // Validate the guess
            val guess = guesses.joinToString("")
            for (i in guess.indices) {
                val textView = gridLayout.getChildAt(currentRow * 5 + i) as TextView
                when {
                    guess[i] == wordToGuess[i] -> textView.setBackgroundColor(ContextCompat.getColor(this, R.color.green))
                    wordToGuess.contains(guess[i]) -> textView.setBackgroundColor(ContextCompat.getColor(this, R.color.yellow))
                    else -> textView.setBackgroundColor(ContextCompat.getColor(this, R.color.gray))
                }
            }

            currentRow++
            guesses.clear() // Clear the guess for the next row
        }
    }
}
