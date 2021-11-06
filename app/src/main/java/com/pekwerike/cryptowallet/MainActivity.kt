package com.pekwerike.cryptowallet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.pekwerike.cryptowallet.ui.screens.cardcompleted.CardAddedSuccessfully
import com.pekwerike.cryptowallet.ui.screens.home.HomeScreen
import com.pekwerike.cryptowallet.ui.screens.navigation.CryptoWalletNavGraph
import com.pekwerike.cryptowallet.ui.screens.newcard.AddNewCardScreen
import com.pekwerike.cryptowallet.ui.theme.CryptoWalletTheme

class MainActivity : ComponentActivity() {
    @ExperimentalMaterialApi
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CryptoWalletTheme {
                CryptoWalletNavGraph(navController = rememberNavController())
            }
        }
    }
}

