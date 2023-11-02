package com.example.bmi_calculator

import android.content.Context
import android.content.SharedPreferences
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.ViewModel
import kotlin.math.sqrt

class Utils(context: Context) : ViewModel() {

    private lateinit var weightEditText: EditText
    private lateinit var heightEditText: EditText
    private lateinit var bmiTextView: TextView
    private lateinit var sharedPreferences: SharedPreferences
    private val SHARED_PREF_NAME = "BMI_HISTORY"
    private val WEIGHT_KEY = "weight"
    private val HEIGHT_KEY = "height"

    init {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
    }

    fun calculateBMI() {
        val weightStr = weightEditText.text.toString()
        val heightStr = heightEditText.text.toString()

        if (weightStr.isNotEmpty() && heightStr.isNotEmpty()) {
            val weight = weightStr.toDouble()
            val height = heightStr.toDouble()
            val bmi = calculateBMIValue(weight, height)
            val category = determineBMICategory(bmi)

            bmiTextView.text = String.format("BMI: %.2f ($category)")

            val editor = sharedPreferences.edit()
            editor.putString(WEIGHT_KEY, weightStr)
            editor.putString(HEIGHT_KEY, heightStr)
            editor.apply()
        }
    }

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

    fun saveBMIHistory(weight: String, height: String) {
        val editor = sharedPreferences.edit()
        editor.putString(WEIGHT_KEY, weight)
        editor.putString(HEIGHT_KEY, height)
        editor.apply()
    }

    fun getWeightFromHistory(): String {
        return sharedPreferences.getString(WEIGHT_KEY, "") ?: ""
    }

    fun getHeightFromHistory(): String {
        return sharedPreferences.getString(HEIGHT_KEY, "") ?: ""
    }
}
