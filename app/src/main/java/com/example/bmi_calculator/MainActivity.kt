package com.example.bmi_calculator

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.sqrt


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: Utils
    private lateinit var calculateButton: Button
    private lateinit var weightEditText: EditText
    private lateinit var heightEditText: EditText
    private lateinit var bmiTextView: TextView
    private lateinit var sharedPreferences: SharedPreferences
    private val SHARED_PREF_NAME = "BMI_HISTORY"
    private val WEIGHT_KEY = "weight"
    private val HEIGHT_KEY = "height"

    private fun calculateBMIValue(weight: Double, height: Double): Double {
        return weight / sqrt(height)
    }

    private fun determineBMICategory(bmi: Double): String {

        var category = "ERROR"

        category = if(bmi < 18.5) {
            "Underweight"
        } else if(bmi in 18.5..24.9) {
            "Healthy weight"
        } else if(bmi in 25.0..29.9) {
            "Overweight"
        } else {
            "Obesity"
        }

        return category
    }
    fun calculateBMI() {
        val weightStr = weightEditText.text.toString()
        val heightStr = heightEditText.text.toString()

        if (weightStr.isNotEmpty() && heightStr.isNotEmpty()) {
            val weight = weightStr.toDouble()
            val height = heightStr.toDouble()
            val bmi = calculateBMIValue(weight, height)
            val category = determineBMICategory(bmi)

            bmiTextView.text = String.format("BMI: %.2f ", bmi)

//            val editor = sharedPreferences.edit()
//            editor.putString(WEIGHT_KEY, weightStr)
//            editor.putString(HEIGHT_KEY, heightStr)
//            editor.apply()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        weightEditText = findViewById(R.id.Weight)
        heightEditText = findViewById(R.id.Height)
        bmiTextView = findViewById(R.id.Bmi)

//        viewModel = ViewModelProvider(this)[Utils::class.java]

        calculateButton = findViewById(R.id.calculate)

        calculateButton.setOnClickListener {
            calculateBMI()
        }
    }
}
