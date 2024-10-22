package com.mindera.rocketscience.spacex_presentation.components

import android.graphics.Bitmap
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun WebViewComponent(url: String) {
    var isLoading by remember { mutableStateOf(true) }

    val webViewClient: WebViewClient = object : WebViewClient() {
        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            isLoading = true
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            isLoading = false
        }
    }

    Box {
        AndroidView(factory = { context ->
            WebView(context).apply {
                this.webViewClient = webViewClient
                settings.javaScriptEnabled = false
                loadUrl(url)
            }
        })

        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}