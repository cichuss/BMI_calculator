package com.example.bmi_calculator

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.sqrt
import androidx.activity.viewModels
import androidx.appcompat.widget.SwitchCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.bmi_calculator.activities.AboutMeActivity
import com.example.bmi_calculator.activities.HistoryActivity
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: BMIViewModel
    private lateinit var calculateButton: Button
    private lateinit var menuButton: NavigationView
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var unitSwitch: SwitchCompat

//    private lateinit var weightEditText: EditText
//    private lateinit var heightEditText: EditText
//    private lateinit var bmiTextView: TextView
//    private lateinit var sharedPreferences: SharedPreferences
//    private val SHARED_PREF_NAME = "BMI_HISTORY"
//    private val WEIGHT_KEY = "weight"
//    private val HEIGHT_KEY = "height"
//
//    private fun calculateBMIValue(weight: Double, height: Double): Double {
//        return weight / sqrt(height)
//    }
//
//    private fun determineBMICategory(bmi: Double): String {
//
//        var category = "ERROR"
//
//        category = if (bmi < 18.5) {
//            "Underweight"
//        } else if (bmi in 18.5..24.9) {
//            "Healthy weight"
//        } else if (bmi in 25.0..29.9) {
//            "Overweight"
//        } else {
//            "Obesity"
//        }
//
//        return category
//    }
//
//    fun calculateBMI() {
//        val weightStr = weightEditText.text.toString()
//        val heightStr = heightEditText.text.toString()
//
//        if (weightStr.isNotEmpty() && heightStr.isNotEmpty()) {
//            val weight = weightStr.toDouble()
//            val height = heightStr.toDouble()
//            val bmi = calculateBMIValue(weight, height)
//            val category = determineBMICategory(bmi)
//
//            bmiTextView.text = String.format("BMI: %.2f ", bmi)
//
////            val editor = sharedPreferences.edit()
////            editor.putString(WEIGHT_KEY, weightStr)
////            editor.putString(HEIGHT_KEY, heightStr)
////            editor.apply()
//        }
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewModel: BMIViewModel by viewModels()
//        viewModel = ViewModelProvider(this)[BMIViewModel::class.java]

//        lifecycleScope.launch {

        drawerLayout = findViewById(R.id.drawer_layout)
        val navView = findViewById<NavigationView>(R.id.nav_view)

        val burgerMenuButton = findViewById<Button>(R.id.burgerMenuButton)

        burgerMenuButton.setOnClickListener {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START)
            } else {
                drawerLayout.openDrawer(GravityCompat.START)
            }
        }

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
        unitSwitch = findViewById(R.id.unitSwitch)
        calculateButton = findViewById(R.id.calculate)

        unitSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {

//                viewModel.weightEditText.text.clear()
//                viewModel.heightEditText.text.clear()
//                viewModel.bmiTextView.text = ""
                unitSwitch.text = "Imperial Units"
            } else {

//                viewModel.weightEditText.text.clear()
//                viewModel.heightEditText.text.clear()
//                viewModel.bmiTextView.text = ""
                unitSwitch.text = "Metric Units"
            }
        }
        calculateButton.setOnClickListener {
            viewModel.weightEditText = findViewById(R.id.Weight)
            viewModel.heightEditText = findViewById(R.id.Height)
            viewModel.bmiTextView = findViewById(R.id.Bmi)
            viewModel.calculateBMI()
//           }
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
}
