package com.pekwerike.cryptowallet.ui.screens.newcard

import androidx.annotation.StringRes
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.pekwerike.cryptowallet.R

sealed class CardInput(
    val position: Int, @StringRes val label: Int,
    val keyBoardType: KeyboardType,
    val imeAction: ImeAction
) {
    abstract var text: MutableState<String>
    abstract fun onValueChanged(newValue: String)

    object CardNumber : CardInput(
        position = 3, label = R.string.card_number,
        keyBoardType = KeyboardType.Number,
        imeAction = ImeAction.Next
    ) {

        override var text: MutableState<String> = mutableStateOf("")

        override fun onValueChanged(newValue: String) {
            text.value = newValue
        }
    }

    object CardHolderName : CardInput(
        position = 2, label = R.string.cardholder_name,
        keyBoardType = KeyboardType.Text,
        imeAction = ImeAction.Next
    ) {
        override var text: MutableState<String> = mutableStateOf("")

        override fun onValueChanged(newValue: String) {
            text.value = newValue
        }
    }

    object ValidUntil : CardInput(
        position = 3, label = R.string.valid_until,
        keyBoardType = KeyboardType.Number,
        imeAction = ImeAction.Next
    ) {
        override var text: MutableState<String> = mutableStateOf("")

        override fun onValueChanged(newValue: String) {
            text.value = newValue
        }
    }

    object CVV : CardInput(
        position = 4, label = R.string.cvv_number,
        keyBoardType = KeyboardType.Number,
        imeAction = ImeAction.Done
    ) {
        override var text: MutableState<String> = mutableStateOf("")

        override fun onValueChanged(newValue: String) {
            text.value = newValue
        }
    }
}