package com.example.restaurantapp

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class RestaurantViewModel(private val stateHandle: SavedStateHandle) : ViewModel() {


    val state = mutableStateOf(dummyRestaurants)
    fun toggleFavourite(id: Int) {
        val restaurants = state.value.toMutableList()
        val itemIndex = restaurants.indexOfFirst { it.id == id }

        val item = restaurants[itemIndex]

        storeSelection(item)
        restaurants[itemIndex] = item.copy(isFavourite = !item.isFavourite)
        state.value = restaurants
    }

    private fun storeSelection(item: Restaurant) {
        val savedToggleId = stateHandle.get<List<Int>>(FAVOURITES).orEmpty().toMutableList()
        if (item.isFavourite)
            savedToggleId.add(item.id)
        else
            savedToggleId.remove(item.id)
        stateHandle[FAVOURITES] = savedToggleId
    }

    /* companion objects are singleton objects whose properties and functions are tied to a class
     but not to the instance of that class —
     basically like the “static” keyword in Java but with a twist.  */
    companion object {
        const val FAVOURITES = "favourites"
    }
}