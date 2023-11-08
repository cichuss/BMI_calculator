package com.example.bmi_calculator

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.bmi_calculator.activities.AboutMeActivity
import com.example.bmi_calculator.activities.HistoryActivity
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: BMIViewModel
    private lateinit var calculateButton: Button
    private lateinit var menuButton: NavigationView
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var unitSwitch: SwitchCompat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val model: BMIViewModel by viewModels()
        viewModel = model
//        viewModel = ViewModelProvider(this).get(BMIViewModel::class.java)

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
                findViewById<EditText>(R.id.Weight).hint = "Weight [lb]"
                findViewById<EditText>(R.id.Height).hint = "Height [in]"
//                viewModel.unitSystem = BMIImperialUnits()
                unitSwitch.text = "Imperial Units"
            } else {

//                viewModel.weightEditText.text.clear()
//                viewModel.heightEditText.text.clear()
//                viewModel.bmiTextView.text = ""
                findViewById<EditText>(R.id.Weight).hint = "Weight [kg]"
                findViewById<EditText>(R.id.Height).hint = "Height [cm]"
//                viewModel.unitSystem = BMIMetricUnits()
                unitSwitch.text = "Metric Units"
            }
        }
        calculateButton.setOnClickListener {
            viewModel.weightEditText = findViewById(R.id.Weight)
            viewModel.heightEditText = findViewById(R.id.Height)
            viewModel.bmiTextView = findViewById(R.id.Bmi)

            viewModel.calculateBMI()
//            lifecycleScope.launch {
//                repeatOnLifecycle(Lifecycle.State.STARTED) {
//                    viewModel.uiState.collect {
////                        viewModel.calculateBMI()
//                    }
//                }
//            }
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
