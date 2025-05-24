package com.weatherapp.ui.weather_screen

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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.weatherapp.R
import com.weatherapp.domain.model.CityDetails
import com.weatherapp.domain.model.WeatherForecast
import com.weatherapp.ui.theme.Spacing
import com.weatherapp.ui.theme.TransparentBlack
import com.weatherapp.ui.theme.White

@Preview
@Composable
fun WeatherScreen(
    state: WeatherState
) {
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(state.backgroundImage),
                contentScale = ContentScale.FillBounds,
                contentDescription = null
            )
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
                    horizontalArrangement = Arrangement.spacedBy(Spacing.medium)
                ) {
                    items(state.weatherForecast.size) {
                        WeatherForecastItem(
                            item = state.weatherForecast[it]
                        )
                    }
                }
            }
            if (state.isLoading == true)
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
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
                style = MaterialTheme.typography.bodySmall
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
            model = "https://letsenhance.io/static/73136da51c245e80edc6ccfe44888a99/1015f/MainBefore.jpg",
            contentDescription = null
        )
        Text(
            text = item.temp,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}