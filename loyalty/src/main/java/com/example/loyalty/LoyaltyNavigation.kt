package com.example.loyalty


import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.core.navigation.AppNavigationRoutes
import com.example.core.navigation.loyalty.LoyaltyNavigationRoutes
import com.example.loyalty.rewards.RewardsScreen

fun NavGraphBuilder.loyaltyNavigationGraph(
    navController: NavHostController
) {
    navigation<AppNavigationRoutes.LoyaltyNavigationRoute>(
        startDestination = LoyaltyNavigationRoutes.RewardsRoute
    ) {
        composable<LoyaltyNavigationRoutes.RewardsRoute> {
            RewardsScreen(navController = navController)
        }
    }
}