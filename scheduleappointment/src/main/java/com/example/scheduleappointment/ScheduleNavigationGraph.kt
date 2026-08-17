package com.example.scheduleappointment

import androidx.activity.compose.LocalActivity
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.scheduleappointment.branches.BranchFlow
import com.example.scheduleappointment.branches.BranchesScreen
import com.example.scheduleappointment.cita_agendada.AppointmentScheduledScreen
import com.example.scheduleappointment.detail_staff.DetailStaffScreen
import com.example.scheduleappointment.home.HomeScreen
import com.example.scheduleappointment.home.HomeScreenEvents
import com.example.scheduleappointment.mainflow.AppointmentFlowViewModel
import com.example.scheduleappointment.schedule.ScheduleScreen
import com.example.scheduleappointment.schedule_staff.ScheduleStaffScreen
import com.example.scheduleappointment.service.ServiceScreen
import com.example.core.navigation.AppNavigationRoutes
import com.example.core.navigation.schedule.ScheduleNavigationRoutes
import com.example.core.util.sharedViewModel
import com.example.domain.entities.remote.migration.Staff
import com.example.scheduleappointment.schedule_confirmation.ScheduleConfirmationScreen


fun NavGraphBuilder.scheduleNavigationGraph(
    navController: NavHostController,
    goToInfoNavigation: () -> Unit,
    goToProfileNavigation: () -> Unit,
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
                    }
                }
            )

        }
        composable<ScheduleNavigationRoutes.ChoseBranchRoute> {
            val mainViewModel =
                it.sharedViewModel<AppointmentFlowViewModel>(navController = navController)
            mainViewModel.currentFlowBranch = BranchFlow.SCHEDULE_APPOINTMENT
            BranchesScreen(
                navController = navController,
                mainViewModel = mainViewModel
            )
        }
        composable<ScheduleNavigationRoutes.ScheduleStaffRoute> {
            val mainViewModel =
                it.sharedViewModel<AppointmentFlowViewModel>(navController = navController)
            ScheduleStaffScreen(navController = navController, mainViewModel = mainViewModel)
        }
        composable<ScheduleNavigationRoutes.DetailStaffRoute> {
            val mainViewModel =
                it.sharedViewModel<AppointmentFlowViewModel>(navController = navController)
            DetailStaffScreen(
                navController = navController,
                currentStaff = mainViewModel.staffUiState.value.currentStaff ?: Staff.mockStaff()
            )
        }
        composable<ScheduleNavigationRoutes.ServicesRoute> {
            val mainViewModel =
                it.sharedViewModel<AppointmentFlowViewModel>(navController = navController)
            ServiceScreen(
                mainViewModel = mainViewModel,
                navController = navController,
                state = mainViewModel.staffUiState.collectAsState().value
            )
        }
        composable<ScheduleNavigationRoutes.ScheduleRoute> {
            val mainViewModel =
                it.sharedViewModel<AppointmentFlowViewModel>(navController = navController)
            ScheduleScreen(flowMainViewModel = mainViewModel, navController = navController)
        }
        composable<ScheduleNavigationRoutes.ScheduleConfirmationRoute> {
            val mainViewModel =
                it.sharedViewModel<AppointmentFlowViewModel>(navController = navController)
            ScheduleConfirmationScreen(
                navController = navController,
                flowMainViewModel = mainViewModel
            ) {
                navController.navigate(ScheduleNavigationRoutes.AppointmentScheduledRoute)
            }
        }
        composable<ScheduleNavigationRoutes.AppointmentScheduledRoute> {
            AppointmentScheduledScreen(navController = navController) {
                onRestart()
            }
        }
    }
}