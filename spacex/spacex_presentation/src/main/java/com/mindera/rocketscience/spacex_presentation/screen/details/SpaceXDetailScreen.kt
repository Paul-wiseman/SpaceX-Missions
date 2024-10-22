package com.mindera.rocketscience.spacex_presentation.screen.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import com.mindera.rocketscience.spacex_presentation.components.SpaceXTopAppBar
import com.mindera.rocketscience.spacex_presentation.components.WebViewComponent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    url: String?,
    onNavigateUP: () -> Unit
) {
    val scrollBarBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBarBehavior.nestedScrollConnection),
        topBar = {
            SpaceXTopAppBar(
                onNavigateUp = onNavigateUP,
                scrollBehavior = scrollBarBehavior
            ) {
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            url?.let {
                WebViewComponent(url)
            }
        }
    }
}