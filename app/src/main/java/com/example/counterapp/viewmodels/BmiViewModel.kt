package com.example.counterapp.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class BmiViewModel : ViewModel() {

    val bmiUiState = mutableStateOf(BmiUiState())

    fun updateWeight(weight: String) {
        // Creates a new BmiUiState object with the updated weight value, the other properties are unchanged
        bmiUiState.value = bmiUiState.value.copy(weight = weight)
    }

    fun updateHeight(height: String) {
        bmiUiState.value = bmiUiState.value.copy(height = height)
    }

    fun calculateBmi() {
        val weight = bmiUiState.value.weight.toDoubleOrNull() ?: 0.0
        val height = bmiUiState.value.height.toDoubleOrNull() ?: 0.0

        val bmiDouble = weight / ((height / 100) * (height / 100))
        bmiUiState.value = bmiUiState.value.copy(bmi = String.format("%.2f", bmiDouble)) // copying the object and updating the bmi value
        bmiUiState.value = bmiUiState.value.copy(status = getBMIStatus(bmiDouble)) // copying the object and updating the status value
    }

    private fun getBMIStatus(bmi: Double): String {
        return when (bmi) {
            in 0.0..16.0 -> SEVERELY_UNDERWEIGHT
            in 16.0..16.9 -> UNDERWEIGHT_MODERATE
            in 17.0..18.4 -> UNDERWEIGHT
            in 18.5..24.9 -> NORMAL_WEIGHT
            in 25.0..29.9 -> OVERWEIGHT
            in 30.0..34.9 -> OBESE_CLASS_I
            in 35.0..39.9 -> OBESE_CLASS_II
            else -> OBESE_CLASS_III
        }
    }

    // Will now be treated as a static member/variables of this class
    companion object {
        const val SEVERELY_UNDERWEIGHT = "Severely Underweight"
        const val UNDERWEIGHT_MODERATE = "Moderate Underweight"
        const val UNDERWEIGHT = "Underweight"
        const val NORMAL_WEIGHT = "Normal weight"
        const val OVERWEIGHT = "Overweight"
        const val OBESE_CLASS_I = "Obesity Class I"
        const val OBESE_CLASS_II = "Obesity Class II"
        const val OBESE_CLASS_III = "Obesity Class III"

        val statusMap = mapOf(
            SEVERELY_UNDERWEIGHT to "Less than 16.0",
            UNDERWEIGHT_MODERATE to "16.0 - 16.9",
            UNDERWEIGHT to "17.0 - 18.4",
            NORMAL_WEIGHT to "18.5 - 24.9",
            OVERWEIGHT to "25.0 -29.9",
            OBESE_CLASS_I to "30.0 - 34.9",
            OBESE_CLASS_II to "35.0 - 39.9",
            OBESE_CLASS_III to "40 and above"
        )
    }
}

data class BmiUiState(
    val weight: String = "",
    val height: String = "",
    val bmi: String = "",
    val status: String = ""
)