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
        return weight / (height/100 * height/100)
    }

    private fun determineBMICategory(bmi: Double, context: Context): String {

        val category: String = if(bmi < 18.5) {
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

        val color: Int = when (category) {
            context.getString(R.string.underweight)
            -> {
                Color.parseColor(context.getString(R.string.BLUE))
            }
            context.getString(R.string.healthy_weight) -> {
                Color.parseColor(context.getString(R.string.GREEN))
            }
            context.getString(R.string.overweight) -> {
                Color.parseColor(context.getString(R.string.ORANGE))
            }
            else -> {
                Color.parseColor(context.getString(R.string.RED))
            }
        }

        return color
    }

    fun calculateBMI(weight: Double, height: Double, context: Context) {

        val convertedWeight = unitSystem.convertWeight(weight)
        val convertedHeight = unitSystem.convertHeight(height)
        val bmi = calculateBMIValue(convertedWeight, convertedHeight)
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



