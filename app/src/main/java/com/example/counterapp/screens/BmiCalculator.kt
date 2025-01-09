package com.example.counterapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BmiCalculator(modifier: Modifier = Modifier) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "BMI Calculator")
                }
            )
        }
    ) {
        var weight by remember { mutableStateOf("") }
        var height by remember { mutableStateOf("") }
        var bmi by remember { mutableStateOf("") }
        var status by remember { mutableStateOf("") }

        Column(
            modifier = modifier.padding(it)
        ) {
            EditNumberField(
                value = weight,
                label = "Weight (kg)",
                onValueChange = { weight = it },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                modifier = modifier
            )
            EditNumberField(
                value = height,
                label = "Height (cm)",
                onValueChange = { height = it },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                modifier = modifier
            )

            Button(onClick = { 
                bmi = calculateBmi(
                    weight = weight.toDoubleOrNull() ?: 0.0,
                    height = height.toDoubleOrNull() ?: 0.0
                )
                status = getBMIStatus(bmi.toDouble())
            }) {
                Text(text = "Calculate BMI")
            }
            BmiResult(
                bmi = bmi,
                status = status,
                modifier = modifier
            )
        }
    }
}

fun calculateBmi(weight: Double, height: Double): String {
    val bmi = weight / ((height / 100) * (height / 100))
    return String.format("%.1f", bmi)
}

fun getBMIStatus(bmi: Double): String {
    return when (bmi) {
        in 0.0..16.0 -> BMIConstants.SEVERELY_UNDERWEIGHT
        in 16.0..16.9 -> BMIConstants.UNDERWEIGHT_MODERATE
        in 17.0..18.4 -> BMIConstants.UNDERWEIGHT
        in 18.5..24.9 -> BMIConstants.NORMAL_WEIGHT
        in 25.0..29.9 -> BMIConstants.OVERWEIGHT
        in 30.0..34.9 -> BMIConstants.OBESE_CLASS_I
        in 35.0..39.9 -> BMIConstants.OBESE_CLASS_II
        else -> BMIConstants.OBESE_CLASS_III
    }
}

@Composable
fun EditNumberField(
    value: String,
    label: String,
    onValueChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions,
    modifier: Modifier = Modifier) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        keyboardOptions = keyboardOptions,
        label = { Text(text = label) },
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    )
}

@Composable
fun BmiResult(
    bmi: String,
    status: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "BMI: $bmi",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (status.isNotBlank()) {
            for (key in statusMap.keys) {
                val color = if (status == key) Color.DarkGray else Color.Transparent
                val fontWeight = if (status == key) FontWeight.Bold else FontWeight.Normal
                Row(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .fillMaxWidth()
                        .background(color = color)
                ) {
                    Text(
                        text = key,
                        fontWeight = fontWeight,
                        modifier = Modifier.weight(1f)
                    )
                    Text(text = statusMap[key] ?: "", fontWeight = fontWeight)
                }
            }
        }
    }
}

@Preview
@Composable
private fun BmiCalculatorPreview() {
    BmiCalculator()
}

object BMIConstants {
    const val SEVERELY_UNDERWEIGHT = "Severely Underweight"
    const val UNDERWEIGHT_MODERATE = "Moderate Underweight"
    const val UNDERWEIGHT = "Underweight"
    const val NORMAL_WEIGHT = "Normal weight"
    const val OVERWEIGHT = "Overweight"
    const val OBESE_CLASS_I = "Obesity Class I"
    const val OBESE_CLASS_II = "Obesity Class II"
    const val OBESE_CLASS_III = "Obesity Class III"
}

val statusMap = mapOf(
    BMIConstants.SEVERELY_UNDERWEIGHT to "Less than 16.0",
    BMIConstants.UNDERWEIGHT_MODERATE to "16.0 - 16.9",
    BMIConstants.UNDERWEIGHT to "17.0 - 18.4",
    BMIConstants.NORMAL_WEIGHT to "18.5 - 24.9",
    BMIConstants.OVERWEIGHT to "25.0 -29.9",
    BMIConstants.OBESE_CLASS_I to "30.0 - 34.9",
    BMIConstants.OBESE_CLASS_II to "35.0 - 39.9",
    BMIConstants.OBESE_CLASS_III to "40 and above"
)