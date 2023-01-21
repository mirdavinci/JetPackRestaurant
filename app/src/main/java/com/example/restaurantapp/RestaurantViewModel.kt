package com.example.restaurantapp

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class RestaurantViewModel() : ViewModel() {

    private val state = mutableStateOf(dummyRestaurants)


    fun getRestaurants() = dummyRestaurants

    fun toggleFavourite(id: Int) {
        val restaurants = state.value.toMutableList()
        val itemIndex = restaurants.indexOfFirst { it.id == id }

        val item = restaurants[itemIndex]

        restaurants[itemIndex] = item.copy(isFavourite = !item.isFavourite)
        state.value = restaurants
    }

}