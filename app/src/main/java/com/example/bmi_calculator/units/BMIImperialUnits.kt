package com.example.bmi_calculator.units

class BMIImperialUnits : Units {
    override fun convertHeight(height: Double): Double {
        return height * 39.37
    }

    override fun convertWeight(weight: Double): Double {
        return weight * 2.2046
    }
}