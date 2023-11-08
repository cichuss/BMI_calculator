package com.example.bmi_calculator.viewModels

import androidx.lifecycle.ViewModel

class BMIDescriptionViewModel: ViewModel() {

    fun descriptionOfCategory(category: String): Triple<String, String, String> {

        var definition = "ERROR"
        var characteristics = "ERROR"
        var implications = "ERROR"

        when (category) {
            "Underweight" -> {
                definition = "Underweight is a category of BMI that indicates an individual has a lower body mass relative to their height. This can result from insufficient caloric intake, underlying medical conditions, or poor nutrition.\n"
                characteristics = "People in the underweight category may have a BMI below 18.5. They often have less body fat and muscle mass than is considered healthy, which can lead to a weakened immune system, nutritional deficiencies, and other health issues.\n"
                implications = "Being underweight can increase the risk of health problems such as weakened bones, compromised immune function, and difficulty in maintaining proper body functions. Medical attention and dietary adjustments may be necessary to achieve a healthier weight."
            }
            "Healthy weight" -> {
                definition = "Healthy weight refers to a BMI range that is considered optimal for overall well-being and reduced risk of obesity-related health issues. It represents a balanced ratio of weight to height.\n"
                characteristics = "A healthy weight typically falls within the BMI range of 18.5 to 24.9. People in this category are often better equipped to maintain good physical health, as well as overall energy and vitality.\n"
                implications = "Maintaining a healthy weight is associated with a lower risk of chronic diseases, improved cardiovascular health, and better physical fitness. It is generally seen as the ideal BMI range for most individuals."
            }
            "Overweight" -> {
                definition = "Overweight indicates a higher BMI than the healthy weight range, suggesting an excess of body fat. This can result from factors like overeating, lack of physical activity, or genetic predisposition.\n"
                characteristics = "Individuals in the overweight category often have a BMI between 25 and 29.9. This excess weight can contribute to an increased risk of conditions such as heart disease, type 2 diabetes, and joint problems.\n"
                implications = "Being overweight can lead to various health challenges and complications, making it essential to adopt lifestyle changes such as a balanced diet and regular exercise to reduce health risks and reach a healthier weight."
            }
            else -> {
                definition = "Obesity is a severe condition characterized by a very high BMI, indicating a substantial accumulation of excess body fat. It can have serious health implications and is often the result of prolonged unhealthy lifestyle choices.\n"
                characteristics = "Obesity is typically defined by a BMI of 30 or higher. It significantly raises the risk of life-threatening health conditions, including heart disease, stroke, certain cancers, sleep apnea, and more.\n"
                implications = "Obesity is associated with a multitude of health problems and is considered a major public health concern. Treatment often involves comprehensive lifestyle changes, including diet, exercise, and, in some cases, medical interventions to reduce the risks associated with excess body weight."
            }
        }

        return Triple(definition, characteristics, implications)
    }

}