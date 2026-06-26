package com.example.citassalon.presentacion.features.profile.historial_citas

import com.example.citassalon.presentacion.main.NetworkHelper
import com.example.domain.use_cases.GetAppointmentsUseCase
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before


class AppointmentHistoryViewModelTest {

    private lateinit var viewModel: AppointmentHistoryViewModel
    private val testDispatcher = StandardTestDispatcher()
    private val networkHelper: NetworkHelper = mockk()
    private val getAppointmentsUse: GetAppointmentsUseCase = mockk()
    private val deleteAppointmentUseCase: DeleteAppointmentUseCase = mockk()


    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = AppointmentHistoryViewModel(
            networkHelper = networkHelper,
            getAppointmentsUse = getAppointmentsUse,
            deleteAppointmentUseCase = deleteAppointmentUseCase
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.setMain(Dispatchers.Default)
    }


}