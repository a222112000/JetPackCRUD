package com.example.crudapp

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.crudapp.ui.theme.CRUDAppTheme
import com.example.crudapp.ui.theme.UserScreen
import com.example.crudapp.ui.theme.edit.EditUserScreen
import com.example.crudapp.util.Route
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
            CRUDAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = Route.Users_All){
                        composable(Route.Users_All){
                            UserScreen(onNav = {
                                navController.navigate(it.route)
                            })
                        }
                        composable(route = Route.Edit_User + "?userId={userId}",
                        arguments = listOf(
                            navArgument(name = "userId"){
                                type = NavType.IntType
                                defaultValue = -1
                            }
                        )
                        ){
                            EditUserScreen(onPopBack = { navController.navigate(Route.Edit_User + "?userId={userId}") })
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CRUDAppTheme {
        Greeting("Android")
    }
}