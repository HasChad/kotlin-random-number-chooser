package com.haschad.randomnumberchooser

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import com.haschad.randomnumberchooser.ui.theme.RandomNumberChooserTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RandomNumberChooserTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NumberChooser()
                }
            }
        }
    }
}

@Composable
fun NumberChooser() {
    var min_value by remember { mutableStateOf("") }
    var max_value by remember { mutableStateOf("") }
    var wish_result_count by remember { mutableStateOf("") }
    var randomizer_result by remember { mutableStateOf(listOf<Int>()) }
    var enable_button by remember { mutableStateOf(false) }
    val maxChar = 9

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(50.dp))

        Text("Enter the lower value:")
        OutlinedTextField(
            value = min_value,
            onValueChange = {
                if (it.isDigitsOnly()) {
                    if (it.length <= maxChar) min_value = it
                }
            },
            label = { Text("Lower value") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            singleLine = true
        )
        if (min_value == "") {
            Text(
                "Enter a value",
                color = Color.Red
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text("Enter the higher value:")
        OutlinedTextField(
            value = max_value,
            onValueChange = {
                if (it.isDigitsOnly()) {
                    if (it.length <= maxChar) max_value = it
                }
            },
            label = { Text("Higher value") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            singleLine = true
        )
        if (max_value == "") {
            Text(
                "Enter a value",
                color = Color.Red
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text("Enter the amount of random number:")
        OutlinedTextField(
            value = wish_result_count,
            onValueChange = {
                if (it.isDigitsOnly()) {
                    if (it.length <= maxChar) wish_result_count = it
                }
            },
            label = { Text("Random number count") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            singleLine = true
        )
        if (wish_result_count == "") {
            Text(
                "Enter a value",
                color = Color.Red
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        if (min_value != "" && max_value != "" && wish_result_count != "") {
            if (min_value.toInt() < max_value.toInt()) {
                if (wish_result_count.toInt() > max_value.toInt() - min_value.toInt()) {
                    enable_button = false
                    Text(
                        "Random number count must be lower than number options",
                        color = Color.Red
                    )
                } else {
                    enable_button = true
                }
            } else {
                enable_button = false
                Text(
                    "Lower number needs to lower than higher number!",
                    color = Color.Red
                )
            }
        } else {
            enable_button = false
        }


        Button(
            onClick = {
                randomizer_result =
                    List(wish_result_count.toInt()) {
                        (min_value.toInt()..max_value.toInt()).random()

                    }.toMutableList()

                randomizer_result =
                    randomizer_result.distinct().toMutableList()

                while (randomizer_result.size < wish_result_count.toInt()) {
                    (randomizer_result as MutableList<Int>).add(
                        (min_value.toInt()..max_value.toInt()).random()
                    )

                    randomizer_result =
                        randomizer_result.distinct().toMutableList()
                }
            },
            enabled = enable_button

        ) { Text(text = "Generate") }

        Spacer(modifier = Modifier.height(20.dp))

        LazyColumn(
            modifier = Modifier
                .height(250.dp)
                .fillMaxWidth()
                .background(Color.DarkGray),
            horizontalAlignment = Alignment.CenterHorizontally,

            ) {
            items(randomizer_result) {
                Text(
                    "$it",
                    fontSize = 20.sp

                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NumberChooserPreview() {
    RandomNumberChooserTheme {
        NumberChooser()
    }
}