package com.example.bmi_calculator

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.widget.EditText
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import kotlin.math.sqrt
import androidx.activity.viewModels
import androidx.lifecycle.MutableLiveData
import com.example.bmi_calculator.units.BMIMetricUnits
import com.example.bmi_calculator.units.Units
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class BMIViewModelUiState(
    val bmi: Double = 0.0,
    val category: String = "",
    val color: String = "",
)
class BMIViewModel(context: Context) : ViewModel() {

    private val _uiState = MutableStateFlow(BMIViewModelUiState())
    val uiState: StateFlow<BMIViewModelUiState> = _uiState.asStateFlow()

    var resultColor = MutableLiveData<String>()
    var resultBmi = MutableLiveData<Double>()
    var resultCategory = MutableLiveData<String>()

    var unitSystem: Units = BMIMetricUnits()
    @SuppressLint("StaticFieldLeak")
    lateinit var weightEditText: EditText
    @SuppressLint("StaticFieldLeak")
    lateinit var heightEditText: EditText
    @SuppressLint("StaticFieldLeak")
    lateinit var bmiTextView: TextView
    lateinit var sharedPreferences: SharedPreferences
    private val SHARED_PREF_NAME = "BMI_HISTORY"
    private val WEIGHT_KEY = "weight"
    private val HEIGHT_KEY = "height"

//    init {
//        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
//    }

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
            val weight = unitSystem.convertWeight(weightStr.toDouble())
            val height = unitSystem.convertHeight(heightStr.toDouble())
            val bmi = calculateBMIValue(weight, height)
            val category = determineBMICategory(bmi)

            bmiTextView.text = String.format("BMI: %.2f ", bmi)

            _uiState.update { currentState ->
                currentState.copy(
                    bmi = bmi,
                    category = category,
                )
            }
//            val editor = sharedPreferences.edit()
//            editor.putString(WEIGHT_KEY, weightStr)
//            editor.putString(HEIGHT_KEY, heightStr)
//            editor.apply()

        }
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
