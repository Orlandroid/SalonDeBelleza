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
import com.example.citassalon.presentacion.features.schedule_appointment.service.ServiceScreen
import com.example.citassalon.presentacion.features.schedule_appointment.staff.ScheduleStaffScreen


fun NavGraphBuilder.scheduleNavigationGraph(
    navController: NavHostController,
    goToInfoNavigation: () -> Unit,
    goToProfileNavigation: () -> Unit
) {
    navigation(
        route = AppNavigationRoutes.ScheduleNavigation.route,
        startDestination = ScheduleAppointmentScreens.Home.route
    ) {
        composable(route = ScheduleAppointmentScreens.Home.route) {
            HomeScreen(
                navController = navController,
                goToInfoNavigation = goToInfoNavigation,
                goToProfileNavigation = goToProfileNavigation
            )
        }
        composable(route = ScheduleAppointmentScreens.ChoseBranch.route) {
            val mainViewModel = it.sharedViewModel<FlowMainViewModel>(navController = navController)
            BranchesScreen(navController = navController, mainViewModel = mainViewModel)
        }
        composable(route = ScheduleAppointmentScreens.ScheduleStaff.route) {
            val mainViewModel = it.sharedViewModel<FlowMainViewModel>(navController = navController)
            ScheduleStaffScreen(navController = navController, mainViewModel = mainViewModel)
        }
        composable(route = ScheduleAppointmentScreens.DetailStaff.route) {
            val mainViewModel = it.sharedViewModel<FlowMainViewModel>(navController = navController)
            DetailStaffScreen(
                navController = navController, currentStaff = mainViewModel.currentStaff
            )
        }
        composable(route = ScheduleAppointmentScreens.Services.route) {
            val mainViewModel = it.sharedViewModel<FlowMainViewModel>(navController = navController)
            ServiceScreen(mainViewModel = mainViewModel, navController = navController)
        }
        composable(route = ScheduleAppointmentScreens.Schedule.route) {
            val mainViewModel = it.sharedViewModel<FlowMainViewModel>(navController = navController)
            ScheduleScreen(flowMainViewModel = mainViewModel, navController = navController)
        }
    }
}