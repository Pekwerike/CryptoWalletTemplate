package com.pekwerike.cryptowallet.ui.screens.newcard

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp

@ExperimentalAnimationApi
@Composable
fun CryptoCardTextField(
    modifier: Modifier = Modifier,
    label: String,
    value: String
) {
    val labelFontSize by animateFloatAsState(targetValue = if (value.isBlank()) 14f else 12f)
    Column(modifier = modifier) {
        CompositionLocalProvider(LocalContentAlpha provides 0.4f) {
            Text(
                text = label,
                style = MaterialTheme.typography.caption,
                fontSize = labelFontSize.sp
            )
        }
        AnimatedVisibility(
            visible = value.isNotBlank(),
            enter = slideInVertically(initialOffsetY = { +it / 2 })
        ) {
            Text(
                text = value, maxLines = 1,
                style = MaterialTheme.typography.body2
            )
        }
    }
}