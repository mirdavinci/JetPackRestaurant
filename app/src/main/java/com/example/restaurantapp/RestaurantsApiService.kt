package com.example.restaurantapp

import retrofit2.Call
import retrofit2.http.GET

interface RestaurantsApiService {

    @GET("restaurants.json")
    fun getRestaurants(): Call<Any>
}