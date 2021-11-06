package com.pekwerike.cryptowallet.ui.screens.newcard

import androidx.annotation.StringRes
import androidx.compose.animation.*
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pekwerike.cryptowallet.R
import com.pekwerike.cryptowallet.ui.screens.AddNewCardMockBack
import com.pekwerike.cryptowallet.ui.screens.AddNewCardMockFront
import com.pekwerike.cryptowallet.ui.theme.CryptoWalletTheme
import kotlinx.coroutines.delay


@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Composable
fun AddNewCardScreen(
    onAddCard: () -> Unit,
    onCancelAddCard: () -> Unit
) {
    Scaffold(
        topBar = {
            AddNewCardScreenTopAppBar()
        }
    ) {
        val newCardInputs =
            listOf(
                CardInput.CardNumber, CardInput.CardHolderName, CardInput.ValidUntil,
                CardInput.CVV
            )
        var cardNumberState by remember { mutableStateOf("**** **** **** ****") }
        var currentInputEntry by remember {
            mutableStateOf(0)
        }
        cardNumberState =
            if (newCardInputs[1].text.value.isNotBlank()) newCardInputs[0].text.value else cardNumberState
        val cardInputState by derivedStateOf {
            newCardInputs[currentInputEntry]
        }


        var cardInputDetailsCompleted by remember {
            mutableStateOf(false)
        }

        val cardFace by derivedStateOf {
            if (currentInputEntry < 3 || cardInputDetailsCompleted) {
                CardFace.Front
            } else {
                CardFace.Back
            }
        }


        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            var showFlipCard by remember {
                mutableStateOf(false)
            }
            LaunchedEffect(key1 = true, block = {
                showFlipCard = true
            })
            val flipCardRiseRotation by animateFloatAsState(
                targetValue = if (showFlipCard) 0f else 90f,
                animationSpec = tween(durationMillis = 1100)
            )


            FlipCard(
                cardFace = cardFace,
                modifier = Modifier
                    .wrapContentHeight()
                    .graphicsLayer {
                        rotationX = flipCardRiseRotation
                        cameraDistance = 12.5f * density
                    },
                front = {
                    AddNewCardMockFront(
                        modifier = Modifier
                            .padding(16.dp),
                        cardHolderName = newCardInputs[1].text.value,
                        cardNumberValue = cardNumberState,
                        validUntilDate = newCardInputs[2].text.value
                    )
                },
                back = {
                    AddNewCardMockBack(
                        modifier = Modifier.padding(16.dp),
                        cardHolderName = newCardInputs[1].text.value,
                        cvvNumber = newCardInputs[3].text.value
                    )
                }
            )
            AnimatedContent(targetState = currentInputEntry,
                transitionSpec = {
                    slideInVertically(
                        { height -> height }, animationSpec = tween(
                            durationMillis = 1000
                        )
                    ) + fadeIn() with
                            slideOutVertically(
                                { height -> -height }, animationSpec = tween(
                                    durationMillis = 1000
                                )
                            ) + fadeOut()
                }) {
                if (!cardInputDetailsCompleted) {
                    CardInputCollector(
                        modifier = Modifier.padding(16.dp),
                        label = stringResource(id = cardInputState.label),
                        keyboardType = cardInputState.keyBoardType,
                        keyboardActions = KeyboardActions {
                            if (currentInputEntry < newCardInputs.size - 1) {
                                if (cardInputState is CardInput.CardNumber ) {
                                    if(cardInputState.text.value.length == 16) {
                                        currentInputEntry += 1
                                    }
                                }
                                else {currentInputEntry += 1}
                            } else {
                                cardInputDetailsCompleted = true
                            }
                        },
                        value = cardInputState.text.value,
                        onValueChange = {
                            when (cardInputState) {
                                is CardInput.CardNumber -> if (it.length <= 16) {
                                    cardInputState.text.value = it
                                    cardNumberState =
                                        formatCardNumberState(
                                            it,
                                            cardNumberState,
                                            currentInputEntry
                                        )
                                    if (it.length == 16) currentInputEntry += 1
                                }
                                is CardInput.ValidUntil -> {
                                    if (it.length <= 4) {
                                        cardInputState.text.value = it
                                    }
                                }
                                is CardInput.CVV -> {
                                    if (it.length <= 3) {
                                        cardInputState.text.value = it
                                    }
                                }
                                else -> cardInputState.text.value = it
                            }
                        },
                        imeAction = cardInputState.imeAction
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                } else {
                    AddOrCancelCardButtonGroup(
                        modifier = Modifier.padding(16.dp),
                        onAddCardClicked = onAddCard,
                        onCancelClicked = onCancelAddCard
                    )
                }
            }
        }
    }
}


private fun formatCardNumberState(
    it: String,
    cardNumberState: String,
    currentInputEntry: Int
): String {
    var cardNumberState1 = cardNumberState
    var currentInputEntry1 = currentInputEntry
    when (it.length) {
        0 -> cardNumberState1 = "**** **** **** ****"
        1 -> cardNumberState1 = "$it*** **** **** ****"
        2 -> cardNumberState1 = "$it** **** **** ****"
        3 -> cardNumberState1 = "$it* **** **** ****"
        4 -> cardNumberState1 = "$it **** **** ****"
        5 -> cardNumberState1 = "$it *** **** ****"
        6 -> cardNumberState1 = "$it** **** ****"
        7 -> cardNumberState1 = "$it* **** ****"
        8 -> cardNumberState1 = "$it **** ****"
        9 -> cardNumberState1 = "$it*** ****"
        10 -> cardNumberState1 = "$it** ****"
        11 -> cardNumberState1 = "$it* ****"
        12 -> cardNumberState1 = "$it****"
        13 -> cardNumberState1 = "$it***"
        14 -> cardNumberState1 = "$it**"
        15 -> cardNumberState1 = "$it*"
        16 -> {
            cardNumberState1 = it
            currentInputEntry1 += 1
        }
    }
    return cardNumberState1
}

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Preview
@Composable
fun AddNewCardScreenPreview() {
    CryptoWalletTheme {
        Surface {
            AddNewCardScreen(
                onAddCard = {},
                onCancelAddCard = {}
            )
        }
    }
}
