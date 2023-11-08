package com.example.bmi_calculator

import android.content.Intent
import android.os.Bundle
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
import com.example.bmi_calculator.activities.AboutMeActivity
import com.example.bmi_calculator.activities.BMIDescriptionActivity
import com.example.bmi_calculator.activities.HistoryActivity
import com.example.bmi_calculator.units.BMIImperialUnits
import com.example.bmi_calculator.units.BMIMetricUnits
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.launch



class MainActivity : AppCompatActivity() {


    private lateinit var calculateButton: Button
    private lateinit var menuButton: NavigationView
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
                }
            }
        }

        calculateButton(viewModel)
        burgerMenu()
        navigation()
        unitSwitch(viewModel)
        clickOnCategory()

        }

    private fun updateTextViewsWithBMIResults(uiState: BMIViewModelUiState ) {

        val resultValueTextView = findViewById<TextView>(R.id.Bmi)
        val resultCategoryTextView = findViewById<TextView>(R.id.categoryText)

        resultValueTextView.text = String.format("BMI: %.2f ", uiState.bmi)
//        resultValueTextView.setTextColor(uiState.color)
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
            if (isChecked) {
                clearTextViews()
                findViewById<EditText>(R.id.Weight).hint = "Weight [lb]"
                findViewById<EditText>(R.id.Height).hint = "Height [in]"
                viewModel.unitSystem = BMIImperialUnits()
                unitSwitch.text = "Imperial Units"
            } else {
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

    private fun calculateButton(viewModel: BMIViewModel) {
        calculateButton = findViewById(R.id.calculate)
        calculateButton.setOnClickListener {
            val weightEditText = findViewById<EditText>(R.id.Weight)
            val heightEditText = findViewById<EditText>(R.id.Height)

            val bmiTextView = findViewById<TextView>(R.id.Bmi)
            val weightString = weightEditText.text.toString()
            val heightString = heightEditText.text.toString()

            if (weightString.isNotEmpty() && heightString.isNotEmpty())  {

                val weight = weightString.toDouble()
                val height = heightString.toDouble()
                viewModel.calculateBMI(weight, height)
            }

            updateTextViewsWithBMIResults(viewModel.uiState.value)

        }
    }

    private fun clickOnCategory() {
        val resultCategoryText = findViewById<TextView>(R.id.categoryText)

        resultCategoryText.setOnClickListener {

            openDescription()
        }
    }
}
