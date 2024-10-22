package com.mindera.rocketscience.spacex_presentation.components

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import coil.compose.AsyncImage
import com.mindera.rocketscience.core.R

@Composable
fun LoadImage(
    modifier: Modifier = Modifier,
    imageLink: String?,
    size: Dp
) {
    AsyncImage(
        model = imageLink,
        contentDescription = stringResource(R.string.mission_image),
        modifier = modifier
            .size(size)
            .fillMaxHeight(),
        placeholder = painterResource(R.drawable.broken_image_icon),
        error = painterResource(R.drawable.loading_image_icon),
        contentScale = ContentScale.Crop
    )
}