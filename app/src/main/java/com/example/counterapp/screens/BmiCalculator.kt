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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.counterapp.viewmodels.BmiViewModel

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
        val bmiViewModel: BmiViewModel = viewModel()

        Column(
            modifier = modifier.padding(it)
        ) {
            EditNumberField(
                value = bmiViewModel.weight,
                label = "Weight (kg)",
                onValueChange = { bmiViewModel.weight = it },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                modifier = modifier
            )
            EditNumberField(
                value = bmiViewModel.height,
                label = "Height (cm)",
                onValueChange = { bmiViewModel.height = it },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                modifier = modifier
            )

            Button(onClick = {
                bmiViewModel.calculateBmi()
            }) {
                Text(text = "Calculate BMI")
            }
            BmiResult(
                bmi = bmiViewModel.bmi,
                status = bmiViewModel.status,
                statusMap = BmiViewModel.statusMap,
                modifier = modifier
            )
        }
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
    statusMap: Map<String, String>,
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

