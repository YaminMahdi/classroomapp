package com.fakhrulasa.classroutine

import android.content.Context
import android.widget.Toast

fun showToastMessage(context: Context, messsage: String ){
    Toast.makeText(context, messsage, Toast.LENGTH_LONG).show()

}