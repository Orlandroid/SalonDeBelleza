package com.example.scheduleappointment

import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.core.navigation.AppNavigationRoutes
import com.example.core.navigation.schedule.ScheduleNavigationRoutes
import com.example.core.ui.components.SuccessScreen
import com.example.scheduleappointment.branches.BranchesScreen
import com.example.scheduleappointment.detail_staff.DetailStaffScreen
import com.example.scheduleappointment.home.HomeScreen
import com.example.scheduleappointment.home.HomeScreenEvents
import com.example.scheduleappointment.schedule.ScheduleScreen
import com.example.scheduleappointment.schedule_confirmation.ScheduleConfirmationScreen
import com.example.scheduleappointment.schedule_staff.ScheduleStaffScreen
import com.example.scheduleappointment.service.ServiceScreen


fun NavGraphBuilder.scheduleNavigationGraph(
    navController: NavHostController,
    goToInfoNavigation: () -> Unit,
    goToProfileNavigation: () -> Unit,
    goToWallet: () -> Unit,
    onRestart: () -> Unit
) {
    navigation<AppNavigationRoutes.ScheduleNavigationRoute>(
        startDestination = ScheduleNavigationRoutes.HomeRoute
    ) {
        composable<ScheduleNavigationRoutes.HomeRoute> {
            HomeScreen(
                event = { event ->
                    when (event) {
                        HomeScreenEvents.NavigateToChoseBranch -> {
                            navController.navigate(ScheduleNavigationRoutes.ChoseBranchRoute)
                        }

                        HomeScreenEvents.NavigateToInfoNavigationFlow -> {
                            goToInfoNavigation()
                        }

                        HomeScreenEvents.NavigateToProfile -> {
                            goToProfileNavigation()
                        }

                        HomeScreenEvents.OnCloseScreen -> {
                            onRestart()
                        }

                        HomeScreenEvents.NavigateToWallet -> {
                            goToWallet()
                        }
                    }
                }
            )

        }
        composable<ScheduleNavigationRoutes.ChoseBranchRoute> {
            BranchesScreen(
                navController = navController
            )
        }
        composable<ScheduleNavigationRoutes.ScheduleStaffRoute> {
            ScheduleStaffScreen(navController = navController)
        }
        composable<ScheduleNavigationRoutes.DetailStaffRoute> {
            DetailStaffScreen(navController = navController)
        }
        composable<ScheduleNavigationRoutes.ServicesRoute> {
            ServiceScreen(navController = navController)
        }
        composable<ScheduleNavigationRoutes.ScheduleRoute> {
            ScheduleScreen(navController = navController)
        }
        composable<ScheduleNavigationRoutes.ScheduleConfirmationRoute> {
            ScheduleConfirmationScreen(
                navController = navController
            ) {
                navController.navigate(ScheduleNavigationRoutes.SuccessScheduleRoute)
            }
        }
        composable<ScheduleNavigationRoutes.SuccessScheduleRoute> {
            SuccessScreen(
                title = stringResource(R.string.appointment_success_title),
                description = stringResource(R.string.appointment_success_subtitle)
            ) {
                onRestart()
            }
        }
    }
}