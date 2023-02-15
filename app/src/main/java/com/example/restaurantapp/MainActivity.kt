package com.example.restaurantapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable

import com.example.restaurantapp.ui.theme.RestaurantAppTheme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RestaurantAppTheme {
                RestaurantApp()
            }
        }
    }
}


//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun DefaultPreview() {
//    RestaurantAppTheme {
//        RestaurantApp()
//    }
//}

@Composable
private fun RestaurantApp(){
    /*
    This composable function will act as the parent composable function of our
Restaurants application. Here, all the screens of the app will be defined.
     */

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "restaurants"){
        composable(route = "restaurants"){
            RestaurantsScreen()
        }
        composable(route = "restaurant/{restaurant_id}"){
        }

    }
}