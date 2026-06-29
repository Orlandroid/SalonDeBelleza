package com.example.citassalon.presentacion.features.schedule_appointment

import androidx.activity.compose.LocalActivity
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.citassalon.presentacion.features.app_navigation.AppNavigationRoutes
import com.example.citassalon.presentacion.features.app_navigation.MainActivityCompose
import com.example.citassalon.presentacion.features.extensions.sharedViewModel
import com.example.citassalon.presentacion.features.schedule_appointment.branches.BranchFlow
import com.example.citassalon.presentacion.features.schedule_appointment.branches.BranchesScreen
import com.example.citassalon.presentacion.features.schedule_appointment.cita_agendada.AppointmentScheduledScreen
import com.example.citassalon.presentacion.features.schedule_appointment.detail_staff.DetailStaffScreen
import com.example.citassalon.presentacion.features.schedule_appointment.home.HomeScreen
import com.example.citassalon.presentacion.features.schedule_appointment.home.HomeScreenEvents
import com.example.citassalon.presentacion.features.schedule_appointment.mainflow.AppointmentFlowViewModel
import com.example.citassalon.presentacion.features.schedule_appointment.schedule.ScheduleScreen
import com.example.citassalon.presentacion.features.schedule_appointment.schedule_confirmation.ScheduleConfirmationScreen
import com.example.citassalon.presentacion.features.schedule_appointment.schedule_staff.ScheduleStaffScreen
import com.example.citassalon.presentacion.features.schedule_appointment.service.ServiceScreen
import com.example.domain.entities.remote.migration.Staff


fun NavGraphBuilder.scheduleNavigationGraph(
    navController: NavHostController,
    goToInfoNavigation: () -> Unit,
    goToProfileNavigation: () -> Unit
) {
    navigation<AppNavigationRoutes.ScheduleNavigationRoute>(
        startDestination = ScheduleAppointmentScreens.HomeRoute
    ) {
        composable<ScheduleAppointmentScreens.HomeRoute> {
            val activity = LocalActivity.current
            HomeScreen(
                event = { event ->
                    when (event) {
                        HomeScreenEvents.NavigateToChoseBranch -> {
                            navController.navigate(ScheduleAppointmentScreens.ChoseBranchRoute)
                        }

                        HomeScreenEvents.NavigateToInfoNavigationFlow -> {
                            goToInfoNavigation()
                        }

                        HomeScreenEvents.NavigateToProfile -> {
                            goToProfileNavigation()
                        }

                        HomeScreenEvents.OnCloseScreen -> {
                            (activity as MainActivityCompose).finish()
                        }
                    }
                }
            )

        }
        composable<ScheduleAppointmentScreens.ChoseBranchRoute> {
            val mainViewModel = it.sharedViewModel<AppointmentFlowViewModel>(navController = navController)
            mainViewModel.currentFlowBranch = BranchFlow.SCHEDULE_APPOINTMENT
            BranchesScreen(
                navController = navController,
                mainViewModel = mainViewModel
            )
        }
        composable<ScheduleAppointmentScreens.ScheduleStaffRoute> {
            val mainViewModel = it.sharedViewModel<AppointmentFlowViewModel>(navController = navController)
            ScheduleStaffScreen(navController = navController, mainViewModel = mainViewModel)
        }
        composable<ScheduleAppointmentScreens.DetailStaffRoute> {
            val mainViewModel = it.sharedViewModel<AppointmentFlowViewModel>(navController = navController)
            DetailStaffScreen(
                navController = navController,
                currentStaff = mainViewModel.staffUiState.value.currentStaff ?: Staff.mockStaff()
            )
        }
        composable<ScheduleAppointmentScreens.ServicesRoute> {
            val mainViewModel = it.sharedViewModel<AppointmentFlowViewModel>(navController = navController)
            ServiceScreen(
                mainViewModel = mainViewModel,
                navController = navController,
                state = mainViewModel.staffUiState.collectAsState().value
            )
        }
        composable<ScheduleAppointmentScreens.ScheduleRoute> {
            val mainViewModel = it.sharedViewModel<AppointmentFlowViewModel>(navController = navController)
            ScheduleScreen(flowMainViewModel = mainViewModel, navController = navController)
        }
        composable<ScheduleAppointmentScreens.ScheduleConfirmationRoute> {
            val mainViewModel = it.sharedViewModel<AppointmentFlowViewModel>(navController = navController)
            ScheduleConfirmationScreen(
                navController = navController,
                flowMainViewModel = mainViewModel
            ) {
                navController.navigate(ScheduleAppointmentScreens.AppointmentScheduledRoute)
            }
        }
        composable<ScheduleAppointmentScreens.AppointmentScheduledRoute> {
            val activity = LocalActivity.current
            AppointmentScheduledScreen(navController = navController) {
                (activity as MainActivityCompose).closeAndOpenActivity()
            }
        }
    }
}