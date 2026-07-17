package com.example.citassalon.presentacion.features.profile.historial_citas

import com.example.citassalon.presentacion.main.NetworkHelper
import com.example.data.remote.appointments.AppointmentsRepository
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
    private val appointmentsRepository: AppointmentsRepository = mockk()


    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = AppointmentHistoryViewModel(
            appointmentsRepository = appointmentsRepository
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.setMain(Dispatchers.Default)
    }


}