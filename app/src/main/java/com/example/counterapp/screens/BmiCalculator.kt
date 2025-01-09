package com.example.counterapp.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
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
            }) {
                Text(text = "Calculate BMI")
            }
            Text(text = "BMI: $bmi")
        }
    }
}

fun calculateBmi(weight: Double, height: Double): String {
    val bmi = weight / ((height / 100) * (height / 100))
    return String.format("%.1f", bmi)
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

@Preview
@Composable
private fun BmiCalculatorPreview() {
    BmiCalculator()
}