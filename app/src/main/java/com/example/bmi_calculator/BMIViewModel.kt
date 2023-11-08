package com.example.bmi_calculator

import android.graphics.Color
import androidx.lifecycle.ViewModel
import com.example.bmi_calculator.units.BMIMetricUnits
import com.example.bmi_calculator.units.Units
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.math.sqrt

data class BMIViewModelUiState(
    val bmi: Double = 0.0,
    val category: String = "",
    val color: Int = Color.BLACK,
)
class BMIViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(BMIViewModelUiState())
    val uiState: StateFlow<BMIViewModelUiState> = _uiState.asStateFlow()

    var unitSystem: Units = BMIMetricUnits()


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

    private fun determineCategoryColor(category: String): Int {

        var color = Color.BLACK

        color = when (category) {
            "Underweight" -> {
                Color.parseColor("#0168e9")
            }
            "Healthy weight" -> {
                Color.parseColor("#30ff3d")
            }
            "Overweight" -> {
                Color.parseColor("#ff9b65")
            }
            else -> {
                Color.parseColor("#f40232")
            }
        }

        return color
    }

    fun calculateBMI(weight: Double, height: Double) {


//        val weightStr = weightEditText.text.toString()
//        val heightStr = heightEditText.text.toString()

//        if (weightStr.isNotEmpty() && heightStr.isNotEmpty()) {
            val weight = unitSystem.convertWeight(weight)
            val height = unitSystem.convertHeight(height)
            val bmi = calculateBMIValue(weight, height)
            val category = determineBMICategory(bmi)
            val color = determineCategoryColor(category)

            _uiState.update { currentState:BMIViewModelUiState ->
                currentState.copy(
                    bmi = bmi,
                    category = category,
                    color = color,
                )
            }

        }
    }



