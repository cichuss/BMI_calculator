package com.example.bmi_calculator.activities

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.bmi_calculator.BMIViewModelUiState
import com.example.bmi_calculator.R
import com.example.bmi_calculator.viewModels.BMIDescriptionViewModel

class BMIDescriptionActivity : AppCompatActivity() {

    private lateinit var viewModel: BMIDescriptionViewModel
    private lateinit var categoryText: TextView
    private lateinit var definitionText: TextView
    private lateinit var characteristicsText: TextView
    private lateinit var implicationsText: TextView
    private var color: String = BMIViewModelUiState().color
    private var category: String = BMIViewModelUiState().category

    private var description: Triple<String, String, String> = viewModel.assignDescription()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmidescription)

        categoryText = findViewById(R.id.categoryName)
        categoryText.text = category

        categoryText.setTextColor(Color.parseColor(color))

        definitionText = findViewById(R.id.definitionText)
        characteristicsText = findViewById(R.id.characteristicsText)
        implicationsText = findViewById(R.id.implicationsText)

        definitionText.text = description.first
        characteristicsText.text = description.second
        characteristicsText.text = description.third


    }
}