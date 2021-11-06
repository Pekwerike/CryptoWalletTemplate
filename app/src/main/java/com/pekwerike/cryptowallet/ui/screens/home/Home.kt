package com.pekwerike.cryptowallet.ui.screens.home

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.drawablepainter.rememberDrawablePainter
import com.pekwerike.cryptowallet.R
import com.pekwerike.cryptowallet.ui.screens.AvailableBalanceCardList
import com.pekwerike.cryptowallet.ui.screens.CryptoWalletBottomBar
import com.pekwerike.cryptowallet.ui.screens.DeckableLayout
import com.pekwerike.cryptowallet.ui.screens.HomeTopAppBar
import com.pekwerike.cryptowallet.ui.theme.CryptoWalletTheme
import com.pekwerike.cryptowallet.ui.utils.dashedBorder
import kotlinx.coroutines.delay

data class NavigationItem(
    @StringRes val label: Int,
    val icon: ImageVector
)


@ExperimentalAnimationApi
@Composable
fun HomeScreen(onAddNewCardClicked: () -> Unit) {
    val showBottomBarComponents = remember {
        MutableTransitionState(false).apply {
            targetState = true
        }
    }
    Scaffold(
        topBar = {
            HomeTopAppBar()
        },
        floatingActionButton = {
            AnimatedVisibility(
                visibleState = showBottomBarComponents,
                enter = slideInVertically(
                    initialOffsetY = { +it * 2 },
                    animationSpec = tween(
                        durationMillis = 700,
                        easing = LinearOutSlowInEasing
                    )
                )
            ) {
                FloatingActionButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Rounded.SyncAlt,
                        contentDescription = "Send and receive"
                    )
                }
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true,
        bottomBar = {
            Box {
                AnimatedVisibility(
                    visibleState = showBottomBarComponents,
                    enter = slideInVertically(
                        initialOffsetY = { +it },
                        animationSpec = tween(
                            durationMillis = 500
                        )
                    )
                ) {
                    CryptoWalletBottomBar()
                }
                Spacer(modifier = Modifier.height(56.dp))
            }
        }
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .padding(
                    contentPadding
                )
                .fillMaxSize()
        ) {
            DeckableLayout(modifier = Modifier.weight(0.6f)) {
                AvailableBalanceCardList()
            }
            AddNewCard(
                modifier = Modifier
                    .weight(0.32f)
                    .padding(16.dp)
                    .fillMaxWidth(),
                onAddNewCardClicked = onAddNewCardClicked
            )
            Spacer(modifier = Modifier.weight(0.08f))
        }
    }
}


@Composable
fun AddNewCard(
    modifier: Modifier = Modifier,
    onAddNewCardClicked: () -> Unit
) {
    Card(
        modifier = modifier,
        elevation = 0.dp,
    ) {
        Box(modifier = Modifier
            .fillMaxSize()
            .clickable {
                onAddNewCardClicked()
            }) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                drawPath(
                    path = Path().apply {
                        addRoundRect(
                            roundRect = RoundRect(
                                left = 0f,
                                top = 0f,
                                right = size.width,
                                bottom = size.height,
                            )
                        )
                    }, style = Stroke(
                        width = 5f, join = StrokeJoin.Round,
                        miter = 0f,
                        pathEffect = PathEffect.dashPathEffect(
                            intervals = floatArrayOf(15f, 20f),
                            phase = 5f
                        )
                    ),
                    color = Color.Gray
                )
            }
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    modifier = Modifier.size(40.dp),
                    imageVector = Icons.Outlined.Add,
                    contentDescription = "Add new card",
                    tint = Color.Gray
                )

                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Add new cards",
                    style = MaterialTheme.typography.subtitle1
                )
            }
        }
    }
}

@Composable
@Preview
fun AddNewCardPreview() {
    CryptoWalletTheme {
        Surface {
            AddNewCard(
                modifier = Modifier
                    .padding(10.dp)
                    .size(340.dp),
                onAddNewCardClicked = {}
            )
        }
    }
}

@ExperimentalAnimationApi
@Composable
@Preview
fun HomeScreenPreview() {
    CryptoWalletTheme {
        Surface {
            HomeScreen(onAddNewCardClicked = {})
        }
    }
}