package com.example.intents

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.example.intents.ui.theme.IntentsTheme

class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<ImageViewMOdel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            IntentsTheme {
                viewModel.uri?.let {
                    AsyncImage(
                        model = viewModel.uri,
                        contentDescription = null
                    )
                }
                Button(onClick = {
                    // explicit intents

                    // Activity to activity
                    /*Intent(applicationContext, SecondActivity::class.java).also {
                        startActivity(it)
                    }*/

                    // To Activity of other app
                    Intent(Intent.ACTION_MAIN).also {
                        it.`package` = "com.google.android.youtube"

                        try {
                            startActivity(it)
                        } catch (e: ActivityNotFoundException) {
                            e.printStackTrace()
                        }
                    }

                    //implicit intents
                    val intent = Intent(Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra(Intent.EXTRA_EMAIL, arrayOf("test@test.com"))
                        putExtra(Intent.EXTRA_EMAIL, "What the hell you doing")
                        putExtra(Intent.EXTRA_EMAIL, "AAAAAAAAAAAA")
                    }
                    if (intent.resolveActivity(packageManager) != null) {
                        startActivity(intent)
                    }
                }) {
                    Text(text = "to second activity")
                }
            }
        }
    }

    @SuppressLint("MissingSuperCall")
    override fun onNewIntent(intent: Intent) {
        val uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(Intent.EXTRA_STREAM, Uri::class.java)
        } else {
            intent.getParcelableExtra(Intent.EXTRA_STREAM)
        }
        viewModel.updateUri(uri)
    }
}
