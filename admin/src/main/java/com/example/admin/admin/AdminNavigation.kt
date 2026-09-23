package com.example.admin.admin

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.core.navigation.AppNavigationRoutes
import com.example.core.navigation.admin.AdminNavigationRoutes

fun NavGraphBuilder.adminNavigationGraph(
    navController: NavHostController
) {
    navigation<AppNavigationRoutes.AdminNavigationRoute>(
        startDestination = AdminNavigationRoutes.AdminDashboardRoute
    ) {
        composable<AdminNavigationRoutes.AdminDashboardRoute> {
            AdminDashboardScreen(navController)
        }
    }
}