package com.example.citassalon.presentacion.features.schedule_appointment

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.citassalon.presentacion.features.app_navigation.AppNavigationRoutes
import com.example.citassalon.presentacion.features.extensions.sharedViewModel
import com.example.citassalon.presentacion.features.flow_main.FlowMainViewModel
import com.example.citassalon.presentacion.features.schedule_appointment.branch.BranchesScreen
import com.example.citassalon.presentacion.features.schedule_appointment.detail_staff.DetailStaffScreen
import com.example.citassalon.presentacion.features.schedule_appointment.home.HomeScreen
import com.example.citassalon.presentacion.features.schedule_appointment.schedule.ScheduleScreen
import com.example.citassalon.presentacion.features.schedule_appointment.schedule_confirmation.ScheduleConfirmationScreen
import com.example.citassalon.presentacion.features.schedule_appointment.service.ServiceScreen
import com.example.citassalon.presentacion.features.schedule_appointment.staff.ScheduleStaffScreen
import com.example.citassalon.presentacion.features.schedule_appointment.branch.Flow


fun NavGraphBuilder.scheduleNavigationGraph(
    navController: NavHostController,
    goToInfoNavigation: () -> Unit,
    goToProfileNavigation: () -> Unit
) {
    navigation<AppNavigationRoutes.ScheduleNavigationRoute>(
        startDestination = ScheduleAppointmentScreens.HomeRoute
    ) {
        composable<ScheduleAppointmentScreens.HomeRoute> {
            HomeScreen(
                navController = navController,
                goToInfoNavigation = goToInfoNavigation,
                goToProfileNavigation = goToProfileNavigation
            )
        }
        composable<ScheduleAppointmentScreens.ChoseBranchRoute> {
            val mainViewModel = it.sharedViewModel<FlowMainViewModel>(navController = navController)
            BranchesScreen(
                navController = navController,
                mainViewModel = mainViewModel,
                flow = Flow.SCHEDULE_APPOINTMENT
            )
        }
        composable<ScheduleAppointmentScreens.ScheduleStaffRoute> {
            val mainViewModel = it.sharedViewModel<FlowMainViewModel>(navController = navController)
            ScheduleStaffScreen(navController = navController, mainViewModel = mainViewModel)
        }
        composable<ScheduleAppointmentScreens.DetailStaffRoute> {
            val mainViewModel = it.sharedViewModel<FlowMainViewModel>(navController = navController)
            DetailStaffScreen(
                navController = navController,
                currentStaff = mainViewModel.currentStaff
            )
        }
        composable<ScheduleAppointmentScreens.ServicesRoute> {
            val mainViewModel = it.sharedViewModel<FlowMainViewModel>(navController = navController)
            ServiceScreen(mainViewModel = mainViewModel, navController = navController)
        }
        composable<ScheduleAppointmentScreens.ScheduleRoute> {
            val mainViewModel = it.sharedViewModel<FlowMainViewModel>(navController = navController)
            ScheduleScreen(flowMainViewModel = mainViewModel, navController = navController)
        }
        composable<ScheduleAppointmentScreens.ScheduleConfirmationRoute> {
            val mainViewModel = it.sharedViewModel<FlowMainViewModel>(navController = navController)
            ScheduleConfirmationScreen(
                navController = navController,
                flowMainViewModel = mainViewModel
            )
        }
    }
}