package com.andreyzim.appcompose.ui

import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.compose.OpenChatAITheme
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalMaterial3Api
@ExperimentalFoundationApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val clipboard = baseContext.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        setContent {
            OpenChatAITheme {
                // A surface container using the 'background' color from the theme
                OpenChatApp(modifier = Modifier.fillMaxSize(), clipboardManager = clipboard)
            }
        }

    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showSystemUi = true)
@Composable
fun DefaultPreview() {
    OpenChatAITheme {
//        ReceivedMessage(body = fishText, showAvatar = false)
    }
}