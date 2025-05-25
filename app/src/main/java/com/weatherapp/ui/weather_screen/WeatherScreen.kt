package com.weatherapp.ui.weather_screen

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import coil3.compose.AsyncImage
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.location.LocationServices
import com.weatherapp.R
import com.weatherapp.domain.model.CityDetails
import com.weatherapp.domain.model.WeatherForecast
import com.weatherapp.ui.theme.Spacing
import com.weatherapp.ui.theme.TransparentBlack
import com.weatherapp.ui.theme.WeatherAppTheme
import com.weatherapp.ui.theme.White

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun WeatherScreen(
    state: WeatherState,
    onEvent: (EventType) -> Unit
) {
    val context = LocalContext.current
    val locationPermissionState = rememberMultiplePermissionsState(
        listOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    )
    val locationClient = remember {
        LocationServices.getFusedLocationProviderClient(context)
    }
    LaunchedEffect(Unit) {
        locationPermissionState.launchMultiplePermissionRequest()
    }
    LaunchedEffect(locationPermissionState.allPermissionsGranted) {
        if (
            ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
        ) {
            locationClient.lastLocation.addOnSuccessListener { location ->
                location?.let {
                    onEvent(EventType.GetWeatherEvent(Pair(location.latitude, location.longitude)))
                }
            }
        }
    }
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(state.backgroundImage),
                contentScale = ContentScale.FillBounds,
                contentDescription = null
            )

            if (locationPermissionState.permissions.any { it.status.isGranted }) {
                Column(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                        .padding(Spacing.medium),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    LazyVerticalGrid(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1F),
                        columns = GridCells.Fixed(2),
                        verticalArrangement = Arrangement.spacedBy(Spacing.medium),
                        horizontalArrangement = Arrangement.spacedBy(Spacing.medium)
                    ) {
                        if (state.city != null)
                            item(span = {
                                GridItemSpan(2)
                            }) {
                                HeaderSection(
                                    modifier = Modifier.fillMaxWidth(),
                                    state.city
                                )
                            }
                        items(
                            span = { GridItemSpan(1) },
                            count = state.weatherConditions.size
                        ) {
                            AttributesItem(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .aspectRatio(1F),
                                state.weatherConditions[it]
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(Spacing.medium))
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(MaterialTheme.shapes.medium)
                            .background(color = TransparentBlack)
                            .padding(Spacing.medium),
                        horizontalArrangement = Arrangement.spacedBy(Spacing.large)
                    ) {
                        items(state.weatherForecast.size) {
                            WeatherForecastItem(
                                item = state.weatherForecast[it]
                            )
                        }
                    }
                }
            } else {
                AllowLocationAccess(
                    modifier = Modifier.padding(Spacing.medium)
                        .fillMaxWidth(),
                    context
                )
            }
            if (state.isLoading == true)
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
    if (!state.error.isNullOrEmpty())
        AlertDialog(state.error) {
            onEvent(EventType.DismissDialogEvent)
        }
}

@Composable
fun HeaderSection(
    modifier: Modifier = Modifier,
    details: CityDetails
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            details.name,
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            details.temp,
            style = MaterialTheme.typography.headlineLarge
        )
        if (details.description != null)
            Text(
                details.description,
                style = MaterialTheme.typography.bodyMedium
            )
        Text(
            "H:${details.tempMax}, L:${details.tempMin}",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun AttributesItem(
    modifier: Modifier = Modifier,
    item: WeatherConditionsUi
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = TransparentBlack
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(Spacing.medium)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(item.iconId),
                    tint = White,
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(Spacing.small))
                Text(
                    text = stringResource(item.titleId),
                    modifier = Modifier.weight(1F),
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
            Spacer(modifier = Modifier.height(Spacing.small))
            Text(
                item.description,
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.weight(1F))
            Text(
                item.additionalDatas,
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}

@Composable
fun WeatherForecastItem(
    modifier: Modifier = Modifier,
    item: WeatherForecast
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = item.date,
            style = MaterialTheme.typography.bodyMedium
        )
        AsyncImage(
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.size(40.dp),
            model = item.icon,
            contentDescription = null
        )
        Text(
            text = item.temp,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun AllowLocationAccess(
    modifier: Modifier = Modifier,
    context: Context
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = stringResource(R.string.allow_location_access),
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            text = stringResource(R.string.allow_location_access_description),
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(Spacing.large))
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    .setData(Uri.fromParts("package", context.packageName, null))
                context.startActivity(intent)
            }
        ) {
            Text(
                text = stringResource(R.string.settings),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Preview
@Composable
fun WeatherScreenPreview() {
    WeatherAppTheme {
        WeatherScreen(
            WeatherState(
                backgroundImage = R.drawable.bg_night,
                city = CityDetails(
                    "New York",
                    "18.2°",
                    "Raining",
                    "22°",
                    "13.7°"
                ),
                weatherConditions = listOf(
                    WeatherConditionsUi(
                        R.string.wind,
                        R.drawable.ic_wind,
                        "2342",
                        ""
                    ),
                    WeatherConditionsUi(
                        R.string.rainfall,
                        R.drawable.ic_rain,
                        "13ml",
                        "Heavy rain"
                    )
                )
            )
        ) { }
    }
}