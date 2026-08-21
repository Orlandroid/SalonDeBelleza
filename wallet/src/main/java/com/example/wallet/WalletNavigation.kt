package com.example.wallet

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.core.navigation.AppNavigationRoutes
import com.example.core.navigation.wallet.WalletNavigationRoutes
import com.example.wallet.balance.WalletScreen

fun NavGraphBuilder.walletNavigationGraph(
    navController: NavHostController,
    onRestart: () -> Unit
) {
    navigation<AppNavigationRoutes.WalletNavigationRoute>(
        startDestination = WalletNavigationRoutes.Balance
    ) {
        composable<WalletNavigationRoutes.Balance> {
            WalletScreen(navController = navController)
        }
    }
}