package com.example.bmi_calculator.viewModels

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.bmi_calculator.R

class BMIDescriptionViewModel: ViewModel() {

    fun descriptionOfCategory(category: String, context: Context): Triple<String, String, String> {

        val definition: String
        val characteristics: String
        val implications: String

        when (category) {
            context.getString(R.string.underweight) -> {
                definition = context.getString(R.string.underweight_definition)
                characteristics = context.getString(R.string.underweight_characteristics)
                implications = context.getString(R.string.underweight_implications)
            }
            context.getString(R.string.healthy_weight) -> {
                definition = context.getString(R.string.healthy_definition)
                characteristics = context.getString(R.string.healthy_characteristics)
                implications = context.getString(R.string.healthy_implications)
            }
            context.getString(R.string.overweight) -> {
                definition = context.getString(R.string.overweight_definition)
                characteristics = context.getString(R.string.overweight_characteristics)
                implications = context.getString(R.string.overweight_implications)
            }
            else -> {
                definition = context.getString(R.string.obesity_definition)
                characteristics = context.getString(R.string.obesity_characteristics)
                implications = context.getString(R.string.obesity_implications)
            }
        }

        return Triple(definition, characteristics, implications)
    }

}