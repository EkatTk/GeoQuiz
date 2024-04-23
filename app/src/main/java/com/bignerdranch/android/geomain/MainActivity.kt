package com.bignerdranch.android.geomain

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: Button
    private lateinit var questionTextView: TextView

    private val quizViewModel: QuizViewModel by
    lazy {
        ViewModelProviders.of(this)[QuizViewModel::class.java]
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(TAG, "onCreate(Bundle?) called")

        setContentView(R.layout.activity_main)

        trueButton = findViewById(R.id.true_button)

        trueButton.setOnClickListener { view: View ->
            checkAnswer(true)
        }

        falseButton = findViewById(R.id.false_button)

        falseButton.setOnClickListener { view: View ->
            checkAnswer(false)
        }

        nextButton = findViewById(R.id.next_button)
        nextButton.isEnabled = false
        questionTextView = findViewById(R.id.question_text_view)

        nextButton.setOnClickListener {
            quizViewModel.moveToNext()
            updateQuestion()
        }
        updateQuestion()

    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")
    }
    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
    }
    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
    }
    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }
    private fun updateQuestion() {
        val questionTextResId = quizViewModel.currentQuestionText
        questionTextView.setText(questionTextResId)
        nextButton.isEnabled = false
        trueButton.isEnabled = true
        falseButton.isEnabled = true
    }

    private fun checkAnswer(userAnswer: Boolean) {
        nextButton.isEnabled = true
        val correctAnswer = quizViewModel.currentQuestionAnswer
        val messageResId = if (userAnswer == correctAnswer) {
            quizViewModel.correctUpdate()
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT)
            .apply {
                setGravity(Gravity.TOP, 0, 100)
                show()
            }
        trueButton.isEnabled = false
        falseButton.isEnabled = false

        if (quizViewModel.currentIndex == quizViewModel.questionBank.size-1){
            nextButton.isEnabled = false
            showCustomDialog(quizViewModel.correctChek.toString())
        }
    }
    private fun showCustomDialog(data: String) {
        val dialogBinding = layoutInflater.inflate(R.layout.activity_modal, null)
        val myDialog = Dialog(this)
        myDialog.setContentView(dialogBinding)
        myDialog.setCancelable(true)
        myDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        myDialog.show()
        val resultText = myDialog.findViewById<TextView>(R.id.result_text)
        val str = "You answered " + data + " out of " + quizViewModel.questionBank.size + " questions correctly"
        resultText.text = str
        val okButton = dialogBinding.findViewById<Button>(R.id.ok_button)
        okButton.setOnClickListener {
            myDialog.dismiss()
        }
    }
}
