package com.mindera.rocketscience.spacex_presentation.components

import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.mindera.rocketscience.core.R

@Composable
fun ScrollToTopButton(
    modifier: Modifier,
    onClick: () -> Unit
) {
    FloatingActionButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Icon(
            painter = painterResource(R.drawable.arrow_upward_alt),
            contentDescription = stringResource(R.string.scroll_top_button)
        )
    }
}