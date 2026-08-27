package com.example.wallet

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.example.core.navigation.AppNavigationRoutes
import com.example.core.navigation.wallet.WalletNavigationRoutes
import com.example.wallet.balance.WalletScreen
import com.example.wallet.transactions.TransactionsScreen
import com.example.wallet.transactions_details.TransactionDetailScreen

fun NavGraphBuilder.walletNavigationGraph(
    navController: NavHostController
) {
    navigation<AppNavigationRoutes.WalletNavigationRoute>(
        startDestination = WalletNavigationRoutes.Balance
    ) {
        composable<WalletNavigationRoutes.Balance> {
            WalletScreen(navController = navController)
        }
        composable<WalletNavigationRoutes.Transactions> {
            TransactionsScreen(navController = navController)
        }
        composable<WalletNavigationRoutes.TransactionDetail> {
            val arguments = it.toRoute<WalletNavigationRoutes.TransactionDetail>()
            TransactionDetailScreen(
                navController = navController,
                transactionId = arguments.transactionId
            )
        }
    }
}