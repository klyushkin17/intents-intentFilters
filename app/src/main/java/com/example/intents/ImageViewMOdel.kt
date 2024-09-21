package com.example.intents

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import java.net.URI

class ImageViewMOdel: ViewModel() {
    var uri: Uri? by mutableStateOf(null)
        private set

    fun updateUri(uri: Uri?) {
        this.uri = uri
    }
}