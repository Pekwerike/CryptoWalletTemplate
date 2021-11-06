package com.pekwerike.cryptowallet.ui.screens.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.pekwerike.cryptowallet.ui.screens.cardcompleted.CardAddedSuccessfully
import com.pekwerike.cryptowallet.ui.screens.home.AddNewCard
import com.pekwerike.cryptowallet.ui.screens.home.HomeScreen
import com.pekwerike.cryptowallet.ui.screens.newcard.AddNewCardScreen

sealed class Screen(val route: String) {
    object Home : Screen(route = "home")
    object AddNewCard : Screen(route = "addNewCard")
    object CardCreated : Screen(route = "cardCreatedSuccessfully")
}

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Composable
fun CryptoWalletNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "home") {
        composable(route = Screen.Home.route) {
            HomeScreen(onAddNewCardClicked = {
                navController.navigate(Screen.AddNewCard.route)
            })
        }
        composable(route = Screen.AddNewCard.route) {
            AddNewCardScreen(
                onAddCard = {
                    navController.navigate(Screen.CardCreated.route)
                },
                onCancelAddCard = {
                    navController.navigate(Screen.Home.route){
                        popUpTo(Screen.Home.route, popUpToBuilder = {
                            inclusive = true
                        })
                    }
                }
            )
        }
        composable(route = Screen.CardCreated.route) {
            CardAddedSuccessfully(onBackToWalletClicked = {
                navController.navigate(Screen.Home.route) {
                    popUpTo(Screen.Home.route, popUpToBuilder = {
                        inclusive = true
                    })
                }
            })
        }
    }
}