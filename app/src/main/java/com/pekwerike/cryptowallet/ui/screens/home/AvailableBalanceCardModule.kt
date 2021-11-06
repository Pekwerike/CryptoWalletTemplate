package com.pekwerike.cryptowallet.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import com.pekwerike.cryptowallet.ui.theme.CryptoWalletTheme
import kotlinx.coroutines.delay
import java.text.DecimalFormat


private val decimalFormatter = DecimalFormat("#,###")

@ExperimentalAnimationApi
@Composable
fun AvailableBalanceCardList() {
    Column(modifier = Modifier.padding(top = 32.dp, bottom = 16.dp)) {
        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            Text(
                modifier = Modifier.padding(start = 16.dp),
                text = "Available balance",
                style = MaterialTheme.typography.body2,
                fontWeight = FontWeight.Bold
            )
        }
        AvailableBalanceText()
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.primary)
                .padding(start = 16.dp, top = 24.dp, bottom = 16.dp),
            text = "My cards",
            fontWeight = FontWeight.SemiBold
        )
        CardList()
    }
}


@ExperimentalAnimationApi
@Composable
private fun CardList() {
    Box {
        val isCardListVisible = remember {
            MutableTransitionState(false).apply {
                // Start the animation immediately.
                targetState = true
            }
        }

        AnimatedVisibility(
            visibleState = isCardListVisible,
            enter = slideInHorizontally(
                initialOffsetX = { +it * 2 },
                animationSpec = tween(
                    durationMillis = 1200,
                    easing = LinearOutSlowInEasing
                )
            )
        ) {
            val localConfiguration = LocalConfiguration.current
            val cardsHeight =
                remember { (localConfiguration.screenHeightDp * 0.24f) }
            val cardsWidth = remember { (localConfiguration.screenWidthDp * 0.80f) }
            LazyRow {
                items(5) {
                    when (it) {
                        1 -> {
                            VisaCardMock(
                                modifier = Modifier
                                    .height(cardsHeight.dp)
                                    .width(cardsWidth.dp)
                            )
                        }
                        3 -> {
                            VisaCardMock(
                                modifier = Modifier
                                    .height(cardsHeight.dp)
                                    .width(cardsWidth.dp)
                            )
                        }
                        else -> {
                            MasterCardMock(
                                modifier = Modifier
                                    .height(cardsHeight.dp)
                                    .width(cardsWidth.dp)
                            )
                            if (it == 4) {
                                Spacer(modifier = Modifier.width(12.dp))
                            }
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
fun AvailableBalanceText() {
    var animateBalanceCount: Boolean by remember { mutableStateOf(false) }
    val availableBalance: Float by animateFloatAsState(
        targetValue = if (animateBalanceCount) 52739.94f else 25000.33F,
        animationSpec = tween(
            durationMillis = 1250
        )
    )
    LaunchedEffect(key1 = true, block = {
        delay(200)
        animateBalanceCount = true
    })
    Text(
        modifier = Modifier.padding(start = 16.dp, top = 8.dp, bottom = 8.dp),
        text = "$${decimalFormatter.format(availableBalance)}",
        style = MaterialTheme.typography.h4,
        fontWeight = FontWeight.SemiBold
    )
}

@ExperimentalAnimationApi
@Preview
@Composable
fun AvailableBalanceCardListPreview() {
    CryptoWalletTheme {
        Surface {
            AvailableBalanceCardList()
        }
    }
}