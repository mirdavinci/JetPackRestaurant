package com.example.restaurantapp

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RestaurantViewModel(private val stateHandle: SavedStateHandle) : ViewModel() {

    private var restInterface: RestaurantsApiService

    init {
        val retrofit: Retrofit =
            Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://restaurants-db-default-rtdb.firebaseio.com/").build()

        restInterface = retrofit.create(RestaurantsApiService::class.java)
    }


    fun getRestaurants() {
        restInterface.getRestaurants().enqueue(
            object : Callback<List<Restaurant>>{
                override fun onResponse(
                    call: Call<List<Restaurant>>,
                    response: Response<List<Restaurant>>
                ) {
                    response.body()?.let {
                        restaurants ->
                        state.value = restaurants.restoreSelections()
                    }
                }

                override fun onFailure(call: Call<List<Restaurant>>, t: Throwable) {
                    t.printStackTrace()

                }
            }
        )
    }

    val  state = mutableStateOf(emptyList<Restaurant>())
    fun toggleFavourite(id: Int) {
        val restaurants = state.value.toMutableList()
        val itemIndex = restaurants.indexOfFirst { it.id == id }

        val item = restaurants[itemIndex]

        storeSelection(item)
//        dummyRestaurants.restoreSelections()
        restaurants[itemIndex] = item.copy(isFavourite = !item.isFavourite)
        state.value = restaurants
    }

    private fun storeSelection(item: Restaurant) {
        val savedToggleId = stateHandle.get<List<Int>>(FAVOURITES).orEmpty().toMutableList()
        if (item.isFavourite) savedToggleId.add(item.id)
        else savedToggleId.remove(item.id)
        stateHandle[FAVOURITES] = savedToggleId
    }

    private fun List<Restaurant>.restoreSelections():
            List<Restaurant> {
        /*
        let is often used for executing a code block
        only with non-null values.
        To perform actions on a non-null object,
        use the safe call operator ?. on it and call
        "let" with the actions in its lambda.
        Another case for using let is introducing local variables
        with a limited scope for improving code readability.
        */
        stateHandle.get<List<Int>?>(FAVOURITES)?.let { selectedIds ->
            val restaurantsMap = this.associateBy { it.id }
            selectedIds.forEach { id ->
                restaurantsMap[id]?.isFavourite = true
            }
            return restaurantsMap.values.toList()
        }
        return this
    }


    /* companion objects are singleton objects whose properties and functions are tied to a class
     but not to the instance of that class —
     basically like the “static” keyword in Java but with a twist.  */
    companion object {
        const val FAVOURITES = "favourites"
    }
}