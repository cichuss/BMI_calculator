package com.example.bmi_calculator.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import com.example.bmi_calculator.R
import com.example.bmi_calculator.viewModels.BMIDescriptionViewModel

class BMIDescriptionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmidescription)


        val category = findViewById<TextView>(R.id.categoryName)
        val color = intent.getIntExtra("color", 0)
        category.text = intent.getStringExtra("category")

        category.setTextColor(color)

        val viewModel: BMIDescriptionViewModel by viewModels()

        val description: Triple<String, String, String> = viewModel.descriptionOfCategory(category.text.toString())
        val definitionText = findViewById<TextView>(R.id.definitionText)
        val characteristicsText = findViewById<TextView>(R.id.characteristicsText)
        val implicationsText = findViewById<TextView>(R.id.implicationsText)

        definitionText.text = description.first
        characteristicsText.text = description.second
        implicationsText.text = description.third

    }
}