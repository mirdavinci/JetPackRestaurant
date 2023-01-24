package com.example.restaurantapp

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
//        Modifier.horizontalScroll(rememberScrollState())
        /*rememberScrollState() => to save scroll state */
    ) {
        items(viewModel.state.value) { restaurant ->
            RestaurantItem(restaurant) { id ->
                viewModel.toggleFavourite(id)

            }
        }
    }
}

@Composable
fun RestaurantItem(item: Restaurant, onClick: (id: Int) -> Unit) {
    val favouriteState = remember { mutableStateOf(false) }

    val icon = if (item.isFavourite)
        Icons.Filled.Favorite
    else
        Icons.Filled.FavoriteBorder

    Card(elevation = 4.dp, modifier = Modifier.padding(8.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(8.dp)) {
            RestaurantIcon(
                Icons.Filled.Place, Modifier.weight(0.15f)
            ) {
            }
            RestaurantDetails(
                item.title, item.description, Modifier.weight(0.85f),
            )
            RestaurantIcon(icon = icon, modifier = Modifier.weight(0.15f)) {
//                favouriteState.value = !favouriteState.value
                onClick(item.id)

            }

        }
    }
}


@Composable
private fun RestaurantIcon(icon: ImageVector, modifier: Modifier, onClick: () -> Unit = {}) {
    Image(
        imageVector = icon,
        contentDescription = "Restaurant icon",
        modifier = modifier
            .padding(8.dp)
            .clickable { onClick() }
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
