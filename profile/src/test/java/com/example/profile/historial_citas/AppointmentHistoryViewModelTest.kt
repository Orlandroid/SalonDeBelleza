package com.example.profile.historial_citas

import com.example.domain.repository.AppointmentsRepository
import com.example.domain.use_cases.SubmitAppointmentReviewUseCase
import com.example.domain.use_cases.loyalty.CompleteAppointmentUseCase
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
    private val appointmentsRepository: AppointmentsRepository = mockk()
    private val completeAppointmentUseCase: CompleteAppointmentUseCase = mockk()
    private val submitAppointmentReviewUseCase: SubmitAppointmentReviewUseCase = mockk()


    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = AppointmentHistoryViewModel(
            appointmentsRepository = appointmentsRepository,
            completeAppointmentUseCase = completeAppointmentUseCase,
            submitAppointmentReviewUseCase = submitAppointmentReviewUseCase
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.setMain(Dispatchers.Default)
    }


}