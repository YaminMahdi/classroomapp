package com.fakhrulasa.classroutine

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fakhrulasa.classroutine.screens.LoginScreen
import com.fakhrulasa.classroutine.screens.RegistrationScreen
import com.fakhrulasa.classroutine.screens.RoutineScreen
import com.fakhrulasa.classroutine.ui.theme.ClassRoutineTheme
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainComposeActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ClassRoutineTheme {
                val navController = rememberNavController()
                // A surface container using the 'background' color from the theme
                //val context = LocalContext.current
                NavHost(
                    navController = navController,
                    startDestination = if(Firebase.auth.currentUser!=null) "home" else "login"
                ) {
                    composable("login") {
                        LoginScreen(navController)
                    }
                    composable("registration") {
                        RegistrationScreen(navController)
                    }
                    composable("home") {
                        RoutineScreen(navController)
                    }
                }

            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ClassRoutineTheme {
        LoginScreen(rememberNavController())
    }
}