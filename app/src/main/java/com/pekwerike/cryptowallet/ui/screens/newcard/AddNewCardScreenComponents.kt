package com.pekwerike.cryptowallet.ui.screens.newcard

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalTextInputService
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pekwerike.cryptowallet.R
import com.pekwerike.cryptowallet.ui.theme.CryptoWalletTheme
import com.pekwerike.cryptowallet.ui.theme.WalletCyan
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

@Composable
fun AddNewCardScreenTopAppBar(
    onArrowBackClicked : () -> Unit
) {
    Row(
        modifier = Modifier.height(56.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onArrowBackClicked, modifier = Modifier.weight(0.12f)) {
            CompositionLocalProvider(LocalContentAlpha provides 0.4f) {
                Icon(
                    imageVector = Icons.Rounded.ArrowBack,
                    contentDescription = "Navigate to homepage"
                )
            }
        }
        Text(
            modifier = Modifier.weight(0.82f),
            text = "Add new card",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.SemiBold)
        )
        Spacer(modifier = Modifier.weight(0.12f))
    }
}


@ExperimentalAnimationApi
@Composable
fun CardInputCollector(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    keyboardType: KeyboardType,
    keyboardActions: KeyboardActions,
    imeAction: ImeAction
) {
    val focusRequester = remember { FocusRequester() }
    val inputMethodService = LocalTextInputService.current
    LaunchedEffect(key1 = true, block = {
        delay(timeMillis = 700)
        focusRequester.requestFocus()
        inputMethodService?.showSoftwareKeyboard()
    })

    Card(
        modifier = modifier.fillMaxWidth(), elevation = 2.dp,
        shape = MaterialTheme.shapes.medium.copy(all = CornerSize(4.dp))
    ) {
        Column(
            modifier = Modifier
                .animateContentSize()
                .padding(8.dp)
        ) {
            TextField(
                modifier = Modifier.focusRequester(focusRequester),
                value = value, onValueChange = { onValueChange(it) }, label = {
                    Text(text = label)
                },
                colors = TextFieldDefaults.textFieldColors(
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    backgroundColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    cursorColor = MaterialTheme.colors.onBackground,
                    focusedLabelColor = MaterialTheme.colors.onBackground.copy(alpha = 0.4f)
                ),
                singleLine = true,
                keyboardActions = keyboardActions,
                keyboardOptions = KeyboardOptions(
                    keyboardType = keyboardType,
                    imeAction = imeAction,
                    capitalization = KeyboardCapitalization.Characters
                )
            )
        }
    }
}


@ExperimentalAnimationApi
@Composable
fun AddOrCancelCardButtonGroup(
    modifier: Modifier = Modifier,
    onAddCardClicked: () -> Unit,
    onCancelClicked: () -> Unit
) {
    val showButtonsText = remember {
        MutableTransitionState(initialState = false).apply {
            targetState = true
        }
    }
    AnimatedVisibility(
        visibleState = showButtonsText,
        enter = EnterTransition.None,
        exit = ExitTransition.None
    ) {
        Column(modifier = modifier.fillMaxWidth()) {
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = onAddCardClicked,
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
                            initialOffsetY = { +it },
                            animationSpec = tween(durationMillis = 500)
                        )
                    ),
                    text = stringResource(R.string.add_card),
                    fontWeight = FontWeight.SemiBold
                )

            }
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedButton(
                onClick = onCancelClicked,
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(
                    horizontal = 16.dp,
                    vertical = 14.dp
                )
            ) {
                Text(
                    modifier = Modifier.animateEnterExit(
                        enter = slideInVertically(
                            initialOffsetY = { +it },
                            animationSpec = tween(durationMillis = 500)
                        )
                    ),
                    text = stringResource(R.string.cancel),
                    color = MaterialTheme.colors.onBackground
                )
            }
        }
    }
}

@Composable
fun CryptoWalletDisabledTextField(
    modifier: Modifier = Modifier,
    value: String, onValueChange: (String) -> Unit,
    label: @Composable (() -> Unit)? = null,
    textStyle: TextStyle
) {
    TextField(
        modifier = modifier, value = value, onValueChange = onValueChange, label = label,
        singleLine = true,
        textStyle = textStyle,
        colors = TextFieldDefaults.textFieldColors(
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            backgroundColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            cursorColor = MaterialTheme.colors.onBackground,
            focusedLabelColor = MaterialTheme.colors.onBackground.copy(alpha = 0.4f)
        ),
        readOnly = true
    )
}

@ExperimentalAnimationApi
@Preview
@Composable
fun AddOrCancelCardButtonGroupPreview() {
    CryptoWalletTheme {
        Surface {
            AddOrCancelCardButtonGroup(
                modifier = Modifier.padding(16.dp),
                onAddCardClicked = {},
                onCancelClicked = {}
            )
        }
    }
}

@ExperimentalAnimationApi
@Preview
@Composable
fun CardInputCollectorPreview() {
    CryptoWalletTheme {
        Surface {
            CardInputCollector(
                modifier = Modifier.padding(4.dp),
                keyboardType = KeyboardType.Number,
                keyboardActions = KeyboardActions {

                },
                label = stringResource(id = R.string.cardholder_name),
                value = "",
                onValueChange = {},
                imeAction = ImeAction.None
            )
        }
    }
}

@Preview
@Composable
fun AddNewCardScreenTopAppBarPreview() {
    CryptoWalletTheme {
        Surface {
            AddNewCardScreenTopAppBar(
                onArrowBackClicked = {}
            )
        }
    }
}