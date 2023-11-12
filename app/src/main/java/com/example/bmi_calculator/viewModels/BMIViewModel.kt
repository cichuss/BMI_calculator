package com.example.bmi_calculator.viewModels

import android.content.Context
import android.graphics.Color
import androidx.lifecycle.ViewModel
import com.example.bmi_calculator.R
import com.example.bmi_calculator.units.BMIMetricUnits
import com.example.bmi_calculator.units.Units
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.math.sqrt

data class BMIViewModelUiState(
    val bmi: Double? = null,
    val category: String? = null,
    val color: Int = Color.BLACK,
    val unitSystem: String = "metric",
)
class BMIViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(BMIViewModelUiState())
    val uiState: StateFlow<BMIViewModelUiState> = _uiState.asStateFlow()

    var unitSystem: Units = BMIMetricUnits()


    private fun calculateBMIValue(weight: Double, height: Double): Double {
        return weight / sqrt(height)
    }

    private fun determineBMICategory(bmi: Double, context: Context): String {

        var category = "ERROR"

        category = if(bmi < 18.5) {
            context.getString(R.string.underweight)
        } else if(bmi in 18.5..24.9) {
            context.getString(R.string.healthy_weight)
        } else if(bmi in 25.0..29.9) {
            context.getString(R.string.overweight)
        } else {
            context.getString(R.string.obesity)
        }

        return category
    }

    private fun determineCategoryColor(category: String, context: Context): Int {

        var color = Color.BLACK

        color = when (category) {
            context.getString(R.string.underweight)
            -> {
                Color.parseColor("#0168e9")
            }
            context.getString(R.string.healthy_weight) -> {
                Color.parseColor("#30ff3d")
            }
            context.getString(R.string.overweight) -> {
                Color.parseColor("#ff9b65")
            }
            else -> {
                Color.parseColor("#f40232")
            }
        }

        return color
    }

    fun calculateBMI(weight: Double, height: Double, context: Context) {


//        val weightStr = weightEditText.text.toString()
//        val heightStr = heightEditText.text.toString()

//        if (weightStr.isNotEmpty() && heightStr.isNotEmpty()) {
        val weight = unitSystem.convertWeight(weight)
        val height = unitSystem.convertHeight(height)
        val bmi = calculateBMIValue(weight, height)
        val category = determineBMICategory(bmi, context)
        val color = determineCategoryColor(category, context)

        val unitString: String = (if (unitSystem is BMIMetricUnits) context.getString(R.string.metric) else context.getString(R.string.imperial))

        _uiState.update { currentState: BMIViewModelUiState ->
            currentState.copy(
                bmi = bmi,
                category = category,
                color = color,
                unitSystem = unitString,
                )
        }

    }
}



