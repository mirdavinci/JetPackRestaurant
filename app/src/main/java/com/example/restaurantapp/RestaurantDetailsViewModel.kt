package com.example.restaurantapp

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RestaurantDetailsViewModel : ViewModel() {

    private var restInterface: RestaurantsApiService

    /*
    Since RestaurantDetailsViewModel will hold the state of the
    restaurant details screen, add a MutableState object that will hold
    a Restaurant object and initialize it with a null value until we finish
    executing the network request that retrieves it
     */
    private val state = mutableStateOf<Restaurant?>(null)

    init {
        val retrofit: Retrofit =
            Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://restaurant-app-jetpack-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .build()

        restInterface = retrofit.create(RestaurantsApiService::class.java)

        viewModelScope.launch {
            /*
            We stored the obtained Restaurant inside the restaurant variable and passed
            it to the state variable of the RestaurantDetailsViewModel class so that
            the UI will be recomposed with the freshly received restaurant content.
             */
            val restaurant = getRemoteRestaurants(2)
            state.value = restaurant
        }
    }


    suspend fun getRemoteRestaurants(id: Int): Restaurant {
        return withContext(Dispatchers.IO) {
            val responseMap = restInterface.getRestaurant(id)
            return@withContext responseMap.values.first()
        }
    }

}