package com.revature.cpreceiver

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.revature.cpreceiver.ui.theme.CPReceiverTheme

//Database setup
//entity setup
//DAO setup

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {
            CPReceiverTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    UI()
                }
            }
        }
    }
}

@SuppressLint("Range")
@Composable
fun UI() {
    val PROVIDER_NAME = "com.revature.contentprovider.RevatureContentProvider.provider"
    val URL = "content://$PROVIDER_NAME/users"

    val CONTENT_URI: Uri = Uri.parse(URL)
    val context= LocalContext.current

    Column {

        var result  by remember{ mutableStateOf("")}

        Button(onClick = {
            val cursor = context.contentResolver.query(CONTENT_URI, null, null, null, null)
            if(cursor!!.moveToFirst()) {
                val stringBuilder = StringBuilder()
                while(!cursor.isAfterLast) {
                    result = "${cursor.getString(cursor.getColumnIndex("id"))}-${cursor.getString(cursor.getColumnIndex("name"))}"
                    cursor.moveToNext()
                }
                Log.d("Data", result)
            } else {
                Log.d("Data", "No Records Found")
            }

        }) {
            Text(text = "Show Records")
        }
    }
}
