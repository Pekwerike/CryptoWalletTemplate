package com.pekwerike.cryptowallet.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.pekwerike.cryptowallet.ui.theme.CryptoWalletTheme

@Composable
fun DeckableLayout(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit = {}
) {

    ConstraintLayout(modifier = modifier.fillMaxSize()) {
        val (upperBackground, columnContent) = createRefs()
        val upperBackgroundGuideline = createGuidelineFromTop(fraction = 0.62f)
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(
                    upperBackground
                ) {
                    linkTo(start = parent.start, end = parent.end)
                    linkTo(top = parent.top, bottom = upperBackgroundGuideline)
                    height = Dimension.fillToConstraints
                },
            color = MaterialTheme.colors.primary,
            content = {}
        )
        Column(
            modifier = Modifier.constrainAs(columnContent) {
                top.linkTo(parent.top)
                linkTo(start = parent.start, end = parent.end)
                width = Dimension.fillToConstraints
            },
            content = {
                CompositionLocalProvider(LocalContentColor provides MaterialTheme.colors.onPrimary) {
                    content()
                }
            })
    }
}

@Preview
@Composable
fun DeckableLayoutPreview() {
    CryptoWalletTheme {
        Surface {
            DeckableLayout(
                content = {
                    Text(text = "Available Balance")
                    Text(text = "53,3553.53", style = MaterialTheme.typography.h5)
                }
            )
        }
    }
}