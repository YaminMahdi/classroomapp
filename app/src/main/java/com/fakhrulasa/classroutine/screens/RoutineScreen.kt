package com.fakhrulasa.classroutine.screens

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.fakhrulasa.classroutine.ClassItem
import com.fakhrulasa.classroutine.bounceClick
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoutineScreen(navController: NavHostController) {
    var classList by rememberSaveable { mutableStateOf(emptyList<ClassItem>()) }
    val list= mutableListOf<ClassItem>()

    val context: Context = LocalContext.current
    val auth: FirebaseAuth = Firebase.auth
    val db = Firebase.firestore

    db.collection("routine")
        .get()
        .addOnSuccessListener { result ->
            for (document in result) {
                Log.d("TAG", "${document.id} => ${document.data}")
                val xx: ClassItem = document.toObject(ClassItem::class.java)
                xx.id=document.id
                list.add(xx)

            }
        }
        .addOnCompleteListener {
            classList=list
            Log.d("TAG", classList.size.toString())

        }
        .addOnFailureListener { exception ->
            Log.d("TAG", "Error getting documents: ", exception)
        }

    Scaffold(
        topBar = { TopBar("All Routines"){navController.navigate("login")} },
        floatingActionButton = { FloatingButton(context) }
    ) {pad->
        LazyColumn(
            modifier = Modifier.padding(pad)
        ){
            items(classList.size){
                RoutineItem(classList[it])
            }
        }
    }
}

@Composable
fun FloatingButton(context: Context) {
    ExtendedFloatingActionButton(
        text = {
            Text(text="Add", color = Color.White)
        },
        icon = {
            Icon(
                imageVector = Icons.Rounded.Add,
                contentDescription = "Navigate FAB",
                tint = Color.White,
            )
        },
        modifier = Modifier
            .bounceClick(),
        onClick = { Toast.makeText(context, "Not Implemented yet.", Toast.LENGTH_SHORT).show() },
        expanded = true,
        containerColor = Color(126, 126, 255),
    )
}

@Composable
fun RoutineItem(classItem: ClassItem) {
    Column(
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .padding(top = 10.dp)
            .fillMaxWidth()
            .bounceClick()
            .clip(RoundedCornerShape(10.dp))
            .background(Color.Blue.copy(alpha = .4f))
            .clickable {  }
            .padding(10.dp)
    ) {
        Text(
            text = "Course: ${classItem.nm}",
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Text(
            text = "Time: ${classItem.time}",
            style = MaterialTheme.typography.bodySmall,
            color = Color.White

        )
        Spacer(modifier = Modifier.size(16.dp))
        Text(
            text = "Teacher Initial: ${classItem.initial}",
            style = MaterialTheme.typography.bodySmall,
            color = Color.White

        )
    }
}