package com.mindera.rocketscience.spacex_presentation.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.mindera.rocketscience.core.R
import com.mindera.rocketscience.core_ui.ui.navigation.Route

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpaceXTopAppBar(
    currentRoute: Route? = null,
    onNavigateUp: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
    filterButtonOnClick: () -> Unit
) {
    val isHomeScreen = currentRoute == Route.Home

    CenterAlignedTopAppBar(
        title = { Text(stringResource(R.string.Space_x), textAlign = TextAlign.Center) },
        navigationIcon = {
            if (!isHomeScreen) {
                IconButton(onClick = onNavigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back)
                    )
                }
            }
        },
        actions = {
            var clicked by remember { mutableStateOf(false) }

            if (isHomeScreen) {
                IconButton(
                    modifier = Modifier
                        .clickable { clicked = !clicked },
                    onClick = filterButtonOnClick) {
                    Icon(
                        painter = painterResource(R.drawable.filter_icon),
                        contentDescription = stringResource(R.string.filter_list)
                    )
                }
            }
        },
        scrollBehavior = scrollBehavior
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun SpaceXTopAppBarPreview() {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    SpaceXTopAppBar(onNavigateUp = {}, scrollBehavior = scrollBehavior) {}
}