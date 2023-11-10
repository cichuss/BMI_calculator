package com.example.bmi_calculator.activities

import HistoryViewModel
import Measurement
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.bmi_calculator.viewModels.BMIViewModel
import com.example.bmi_calculator.viewModels.BMIViewModelUiState
import com.example.bmi_calculator.R
import com.example.bmi_calculator.units.BMIImperialUnits
import com.example.bmi_calculator.units.BMIMetricUnits
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter
import org.w3c.dom.Text


class MainActivity : AppCompatActivity() {


    private lateinit var calculateButton: Button
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var unitSwitch: SwitchCompat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewModel: BMIViewModel by viewModels()
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {uiState ->
                    var bmi = uiState.bmi
                    var category = uiState.category
                    var color = uiState.color
                    var unitSystem = uiState.unitSystem
                    if(viewModel.uiState.value.bmi != null) {
                        updateTextViewsWithBMIResults(viewModel.uiState.value)
                    }
                }
            }
        }

        val historyViewModel: HistoryViewModel by viewModels()

        if(viewModel.uiState.value.bmi != null) {
            updateTextViewsWithBMIResults(viewModel.uiState.value)
        }
        calculateButton(viewModel, historyViewModel)
        burgerMenu()
        navigation()
        unitSwitch(viewModel)
        clickOnCategory()

        Log.d("check1", "${findViewById<TextView>(R.id.Bmi).text}")

        }

    private fun updateTextViewsWithBMIResults(uiState: BMIViewModelUiState) {

        val resultValueTextView = findViewById<TextView>(R.id.Bmi)
        val resultCategoryTextView = findViewById<TextView>(R.id.categoryText)

        resultValueTextView.text = String.format("BMI: %.2f ", uiState.bmi)
        resultCategoryTextView.setTextColor(uiState.color)
        resultCategoryTextView.text = uiState.category
    }

    private fun clearTextViews() {
        val weightEditText = findViewById<EditText>(R.id.Weight)
        val heightEditText = findViewById<EditText>(R.id.Height)

        val resultBMIText = findViewById<TextView>(R.id.Bmi)
        val resultCategoryText = findViewById<TextView>(R.id.categoryText)

        weightEditText.text.clear()
        heightEditText.text.clear()
        resultBMIText.text = "BMI:"
        resultCategoryText.text = ""
    }

    private fun unitSwitch(viewModel: BMIViewModel) {
        unitSwitch = findViewById(R.id.unitSwitch)

        unitSwitch.setOnCheckedChangeListener { _, isChecked ->
            val unitSystem = viewModel.uiState.value.unitSystem
            if (isChecked && unitSystem != "imperial") {
                clearTextViews()
                findViewById<EditText>(R.id.Weight).hint = "Weight [lb]"
                findViewById<EditText>(R.id.Height).hint = "Height [in]"
                viewModel.unitSystem = BMIImperialUnits()
                unitSwitch.text = "Imperial Units"
            } else if (!isChecked && unitSystem != "metric"){
                clearTextViews()
                findViewById<EditText>(R.id.Weight).hint = "Weight [kg]"
                findViewById<EditText>(R.id.Height).hint = "Height [cm]"
                viewModel.unitSystem = BMIMetricUnits()
                unitSwitch.text = "Metric Units"
            }
        }
    }

    private fun burgerMenu() {

        drawerLayout = findViewById(R.id.drawer_layout)

        val burgerMenuButton = findViewById<Button>(R.id.burgerMenuButton)

        burgerMenuButton.setOnClickListener {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START)
            } else {
                drawerLayout.openDrawer(GravityCompat.START)
            }
        }
    }

    private fun openHistory(){
        val history = Intent(this,
            HistoryActivity::class.java)

        startActivity(history)
    }

    private fun openAboutMe(){
        val aboutMe = Intent(this,
            AboutMeActivity::class.java)

        startActivity(aboutMe)
    }

    private fun openDescription(){
        val description = Intent(this,
            BMIDescriptionActivity::class.java)

        val resultCategoryText = findViewById<TextView>(R.id.categoryText)
        val color = resultCategoryText.currentTextColor

        description.putExtra("category", resultCategoryText.text)
        description.putExtra("color", color)
        startActivity(description)

    }
    private fun navigation() {
        val navView = findViewById<NavigationView>(R.id.nav_view)

        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.history_option -> {
                    openHistory()
                }
                R.id.about_me_option -> {
                    openAboutMe()
                }
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
    }

    private fun calculateButton(viewModel: BMIViewModel, historyViewModel: HistoryViewModel) {
        calculateButton = findViewById(R.id.calculate)
        calculateButton.setOnClickListener {
            val weightEditText = findViewById<EditText>(R.id.Weight)
            val heightEditText = findViewById<EditText>(R.id.Height)
            val noValuesError: TextView = findViewById(R.id.error)

            val weightString = weightEditText.text.toString()
            val heightString = heightEditText.text.toString()

            if (weightString.isNotEmpty() && heightString.isNotEmpty())  {

                noValuesError.text = ""
                val weight = weightString.toDouble()
                val height = heightString.toDouble()
                viewModel.calculateBMI(weight, height)
                val bmi = viewModel.uiState.value.bmi
                if (bmi !=null) {

                    saveToHistory(
                        weight,
                        height,
                        bmi,
                        viewModel.uiState.value.unitSystem,
                        historyViewModel
                    )
                }
                updateTextViewsWithBMIResults(viewModel.uiState.value)

            } else {
                clearTextViews()
                noValuesError.text = "Provide correct values"
                noValuesError.setTextColor(Color.RED)
            }

        }
    }

    private fun saveToHistory(weight: Double, height: Double, bmi: Double, unitSystem: String, historyViewModel: HistoryViewModel) {
        val bmiFormatted = String.format("%.2f ", bmi)
        val currentDateTime = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val formattedDateTime = currentDateTime.format(formatter)

        val measurement = Measurement(formattedDateTime, weight, height, bmiFormatted, unitSystem)

        historyViewModel.addMeasurement(measurement, this)
    }

    private fun clickOnCategory() {
        val resultCategoryText = findViewById<TextView>(R.id.categoryText)

        resultCategoryText.setOnClickListener {
            if (resultCategoryText.text.toString() != "") {
                openDescription()
            }
        }
    }
}
