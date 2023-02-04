package com.fakhrulasa.classroutine.screens

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.fakhrulasa.classroutine.bounceClick
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationScreen(navController: NavHostController) {
    val context: Context = LocalContext.current
    val auth: FirebaseAuth = Firebase.auth
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var rePassword by rememberSaveable { mutableStateOf("") }

    Scaffold(topBar = { TopBar("Registration"){navController.popBackStack()} }) {pad->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(pad)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                placeholder = { Text("Input Email") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.padding(10.dp))
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                placeholder = { Text("Input New Password") },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.padding(10.dp))
            OutlinedTextField(
                value = rePassword,
                onValueChange = { rePassword = it },
                label = { Text("Re-type Password") },
                placeholder = { Text("Re-type New Password") },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.padding(10.dp))
            Text(
                text = "Already Registered?",
                fontWeight = FontWeight.Bold,
                color = Color.Blue.copy(alpha = .5f),
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .bounceClick()
                    .clickable {
                        navController.popBackStack()
                    }
            )
        }
        Column(
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .bounceClick()
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.Blue.copy(alpha = .5f))
                    .clickable{
                        if(email.isNotBlank() && password.isNotBlank() && rePassword.isNotBlank()) {
                            if(password != rePassword) {
                                Toast.makeText(context, "Password didn't matched.", Toast.LENGTH_SHORT).show()
                            }
                            else{
                                auth.createUserWithEmailAndPassword(email,password)
                                    .addOnCompleteListener(context as Activity) { task->
                                        if(task.isSuccessful){
                                            Toast.makeText(context,"Registered Successfully", Toast.LENGTH_SHORT).show()
                                            navController.navigate("home")
                                        } else{
                                            Toast.makeText(context,task.exception!!.message.toString(), Toast.LENGTH_SHORT).show()
                                        }
                                    }
                            }
                        }
                        else {
                            Toast.makeText(context,"Can't be empty.", Toast.LENGTH_SHORT).show()
                        }
                    }
            ) {
                Text(
                    text = "Register Now",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(vertical = 10.dp)
                )
            }
        }

    }
}