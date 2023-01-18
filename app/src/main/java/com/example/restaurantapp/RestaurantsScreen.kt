package com.example.restaurantapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Place
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun RestaurantScreen() {
    val viewModel: RestaurantViewModel = viewModel()
    LazyColumn(
        Modifier.verticalScroll(rememberScrollState())
        /*rememberScrollState() => to save scroll state */
    ) {
        items(viewModel.getRestaurants()) { restaurant ->
            RestaurantItem(item = restaurant)
        }
    }
}

@Composable
fun RestaurantItem(item: Restaurant) {
    Card(elevation = 4.dp, modifier = Modifier.padding(8.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(8.dp)) {
            RestaurantIcon(
                Icons.Filled.Place, Modifier.weight(0.15f)
            )
            RestaurantDetails(
                item.title, item.description, Modifier.weight(0.85f),
            )
            FavoriteIcon(modifier = Modifier.padding(8.dp))
        }
    }
}


@Composable
private fun RestaurantIcon(icon: ImageVector, modifier: Modifier) {
    Image(
        imageVector = icon,
        contentDescription = "Restaurant icon",
        modifier = modifier.padding(8.dp)
    )
}

@Composable
private fun RestaurantDetails(title: String, item: String, modifier: Modifier) {

    Column(modifier = modifier) {
        Text(
            text = title, style = MaterialTheme.typography.h6
        )
        CompositionLocalProvider(
            LocalContentAlpha provides ContentAlpha.medium
        ) {
            Text(
                text = item, style = MaterialTheme.typography.body2
            )
        }
    }
}

@Composable
fun NameInput() {
    val textState = remember { mutableStateOf("") }
    TextField(value = textState.value,
        onValueChange = { newValue -> textState.value = newValue },
        label = { Text("Your name") })
}

@Composable
private fun FavoriteIcon(modifier: Modifier) {
    val favoriteState = remember {
        mutableStateOf(false)
    }
    val icon = if (favoriteState.value)
        Icons.Filled.Favorite
    else
        Icons.Filled.FavoriteBorder
    Image(
        imageVector = icon,
        contentDescription = "Favorite restaurant icon",
        modifier = modifier
            .padding(8.dp)
            .clickable {
                favoriteState.value =
                    !favoriteState.value
            }
    )
}