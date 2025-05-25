package com.weatherapp.ui.weather_screen

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.weatherapp.R
import com.weatherapp.ui.theme.Black

@Composable
fun AlertDialog(
    errorMessage: String,
    onDismissRequest: () -> Unit
) {
    AlertDialog(
        onDismissRequest = {
        },
        text = { Text(text = errorMessage, color = Black) },
        confirmButton = {
            Button(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text(
                    text = stringResource(R.string.dismiss)
                )
            }
        }
    )
}