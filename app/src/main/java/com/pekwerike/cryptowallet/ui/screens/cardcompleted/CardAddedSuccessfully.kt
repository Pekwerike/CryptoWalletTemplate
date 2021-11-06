package com.pekwerike.cryptowallet.ui.screens.cardcompleted

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pekwerike.cryptowallet.R
import com.pekwerike.cryptowallet.ui.theme.CongratulationsGreen
import com.pekwerike.cryptowallet.ui.theme.CryptoWalletTheme
import com.pekwerike.cryptowallet.ui.theme.WalletCyan
import kotlinx.coroutines.delay


@ExperimentalAnimationApi
@Composable
fun CardAddedSuccessfully(
    onBackToWalletClicked: () -> Unit
) {
    val showCardAddedSuccessfully = remember {
        MutableTransitionState(initialState = false).apply {
            targetState = true
        }
    }

    AnimatedVisibility(
        visibleState = showCardAddedSuccessfully,
        enter = EnterTransition.None,
        exit = ExitTransition.None
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            CongratulationsCard(
                modifier = Modifier.animateEnterExit(
                    enter = expandVertically(
                        expandFrom = Alignment.CenterVertically,
                        initialHeight = { it / 4 },
                        animationSpec = tween(
                            durationMillis = 1000,
                            easing = LinearOutSlowInEasing
                        )
                    )
                )
            )
            Spacer(modifier = Modifier.height(40.dp))
            Button(
                onClick = onBackToWalletClicked,
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(
                    horizontal = 16.dp,
                    vertical = 14.dp
                ),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = WalletCyan,
                    contentColor = contentColorFor(backgroundColor = MaterialTheme.colors.primary)
                )
            ) {
                Text(
                    modifier = Modifier.animateEnterExit(
                        enter = slideInVertically(
                            initialOffsetY = { +it * 2 },
                            animationSpec = tween(delayMillis = 800, durationMillis = 800)
                        )
                    ),
                    text = stringResource(R.string.back_to_wallet),
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}


@ExperimentalAnimationApi
@Composable
fun CongratulationsCard(modifier: Modifier = Modifier) {
    val showCongratulationsCard = remember {
        MutableTransitionState(initialState = false).apply {
            targetState = true
        }
    }
    val transition = updateTransition(targetState = showCongratulationsCard, label = "")
    val arcSweepAngle = transition.animateFloat(label = "arcSweepAngle",
        transitionSpec = {
            tween(
                durationMillis = 800
            )
        }) { state ->
        if (state.currentState == showCongratulationsCard.targetState) {
            -340f
        } else {
            0f
        }
    }

    val screenHeight = LocalConfiguration.current.screenHeightDp
    val cardHeight = remember(screenHeight) { screenHeight * 0.3f }
    AnimatedVisibility(
        visibleState = showCongratulationsCard,
        enter = EnterTransition.None,
        exit = ExitTransition.None
    ) {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .height(cardHeight.dp),
            elevation = 8.dp
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Canvas(modifier = Modifier
                    .size(100.dp), onDraw = {
                    drawArc(
                        color = CongratulationsGreen,
                        startAngle = 315f,
                        sweepAngle = arcSweepAngle.value,
                        useCenter = false,
                        style = Stroke(
                            width = 8f,
                            cap = StrokeCap.Round,
                            join = StrokeJoin.Round,
                            pathEffect = PathEffect.cornerPathEffect(radius = 5f)
                        )
                    )

                    drawPath(
                        path = Path().apply {
                            moveTo(
                                x = 0.24f * size.width,
                                y = 0.5f * size.height
                            )
                            lineTo(x = 0.45f * size.width, y = 0.73f * size.height)
                            lineTo(x = size.width, y = 0.12f * size.height)
                        },
                        color = CongratulationsGreen,
                        style = Stroke(
                            width = 10f,
                            cap = StrokeCap.Round,
                            join = StrokeJoin.Round,
                            pathEffect = PathEffect.cornerPathEffect(radius = 8f)
                        )
                    )
                })

                Text(
                    text = stringResource(R.string.congratulations),
                    modifier = Modifier
                        .animateEnterExit(
                            enter = fadeIn(animationSpec = tween(durationMillis = 600))
                        )
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    style = MaterialTheme.typography.body1.copy(
                        color = CongratulationsGreen,
                        fontWeight = FontWeight.Bold
                    )
                )

                Text(
                    text = "Your card has been added successfully",
                    style = MaterialTheme.typography.body2,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .animateEnterExit(
                            enter = fadeIn(animationSpec = tween(durationMillis = 600)) + slideInVertically(
                                initialOffsetY = { +it },
                                animationSpec = tween(durationMillis = 800, delayMillis = 800)
                            )
                        )
                        .padding(horizontal = 16.dp)
                )
            }
        }
    }
}


@ExperimentalAnimationApi
@Preview
@Composable
fun CardAddedSuccessfullyPreview() {
    CryptoWalletTheme {
        Surface {
            CardAddedSuccessfully(
                onBackToWalletClicked = {}
            )
        }
    }
}

@ExperimentalAnimationApi
@Preview
@Composable
fun CongratulationsCardPreview() {
    CryptoWalletTheme {
        Surface {
            CongratulationsCard(modifier = Modifier.padding(16.dp))
        }
    }
}