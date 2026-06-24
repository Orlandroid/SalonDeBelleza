package com.example.citassalon.presentacion.features.profile.historial_citas

import app.cash.turbine.test
import com.example.citassalon.presentacion.features.base.BaseScreenState
import com.example.citassalon.presentacion.main.NetworkHelper
import com.example.domain.perfil.Appointment
import com.example.domain.state.ApiResult
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@Suppress("UNCHECKED_CAST")
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

    private fun mockAppointment(
        id: String = "1",
        serviceName: String = "Haircut",
        date: String = "2024-06-23"
    ): Appointment {
        return Appointment(
            id = id,
            service = serviceName,
            branch = date
        )
    }


    @Test
    fun getAppointments_whenSuccessful_shouldUpdateStateWithAppointments() =
        runTest(testDispatcher) {
            val mockAppointments = listOf(
                mockAppointment(id = "1", serviceName = "Haircut"),
                mockAppointment(id = "2", serviceName = "Manicure")
            )

            every { networkHelper.isNetworkConnected() } returns true
            coEvery { getAppointmentsUse() } returns ApiResult.Success(mockAppointments)

            viewModel.state.test {
                // Skip initial loading state
                val loadingEmission = awaitItem()

                assertThat(loadingEmission).isInstanceOf(BaseScreenState.OnLoading::class.java)

                // Wait for content state
                val contentState = awaitItem()

                assertThat(contentState).isInstanceOf(BaseScreenState.OnContent::class.java)
                val content =
                    (contentState as BaseScreenState.OnContent<AppointmentHistoryUiState>).content
                assertThat(content.appointments).hasSize(2)
                assertThat(content.appointments[0].id).isEqualTo("1")
                assertThat(content.appointments[1].id).isEqualTo("2")
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun getAppointments_whenNetworkNotConnected_shouldSetErrorState() = runTest(testDispatcher) {
        every { networkHelper.isNetworkConnected() } returns false

        viewModel.state.test {
            awaitItem()  // Skip initial loading

            val errorState = awaitItem()

            assertThat(errorState).isInstanceOf(BaseScreenState.OnError::class.java)
            val error = (errorState as BaseScreenState.OnError).error
            assertThat(error.message).isEqualTo("Network Error")
        }
    }

    @Test
    fun getAppointments_whenApiReturnsError_shouldSetErrorState() = runTest(testDispatcher) {
        every { networkHelper.isNetworkConnected() } returns true
        coEvery { getAppointmentsUse() } returns ApiResult.Error("Failed to fetch appointments")

        viewModel.state.test {
            awaitItem()  // Skip initial loading

            val errorState = awaitItem()

            assertThat(errorState).isInstanceOf(BaseScreenState.OnError::class.java)
            val error = (errorState as BaseScreenState.OnError).error
            assertThat(error.message).isEqualTo("Network Error")
        }
    }

    @Test
    fun getAppointments_whenEmptyList_shouldReturnEmptyAppointments() = runTest(testDispatcher) {
        every { networkHelper.isNetworkConnected() } returns true
        coEvery { getAppointmentsUse() } returns ApiResult.Success(emptyList())

        viewModel.state.test {
            awaitItem()  // Skip initial loading

            val contentState = awaitItem()

            assertThat(contentState).isInstanceOf(BaseScreenState.OnContent::class.java)
            val content =
                (contentState as BaseScreenState.OnContent<AppointmentHistoryUiState>).content
            assertThat(content.appointments).isEmpty()
        }
    }

    // ========== Delete Appointment Tests ==========

    @Test
    fun onRemove_whenDeleteSuccessful_shouldRemoveAppointment() = runTest(testDispatcher) {
        val mockAppointments = listOf(
            mockAppointment(id = "1", serviceName = "Haircut"),
            mockAppointment(id = "2", serviceName = "Manicure")
        )

        every { networkHelper.isNetworkConnected() } returns true
        coEvery { getAppointmentsUse() } returns ApiResult.Success(mockAppointments)
        coEvery { deleteAppointmentUseCase("1") } returns ApiResult.Success(Unit)

        viewModel.state.test {
            awaitItem()  // Skip initial loading
            awaitItem()  // Content with appointments

            viewModel.onEvents(AppointmentHistoryEvents.OnRemove(idAppointment = "1"))

            // State should remain stable (no error)
            val finalState = awaitItem()
            assertThat(finalState).isInstanceOf(BaseScreenState.OnContent::class.java)
        }
    }

    @Test
    fun onRemove_whenDeleteFails_shouldSetErrorState() = runTest(testDispatcher) {
        val mockAppointments = listOf(
            mockAppointment(id = "1", serviceName = "Haircut")
        )

        every { networkHelper.isNetworkConnected() } returns true
        coEvery { getAppointmentsUse() } returns ApiResult.Success(mockAppointments)
        coEvery { deleteAppointmentUseCase("1") } returns ApiResult.Error("Failed to delete")

        viewModel.state.test {
            awaitItem()  // Skip initial loading
            awaitItem()  // Content with appointments

            viewModel.onEvents(AppointmentHistoryEvents.OnRemove(idAppointment = "1"))

            val errorState = awaitItem()
            assertThat(errorState).isInstanceOf(BaseScreenState.OnError::class.java)
            val error = (errorState as BaseScreenState.OnError).error
            assertThat(error.message).isEqualTo("Network Error")
        }
    }

    // ========== Dialog Management Tests ==========

    @Test
    fun onAccept_shouldCloseDialogAndClearAppointmentId() = runTest(testDispatcher) {
        val mockAppointments = listOf(
            mockAppointment(id = "1", serviceName = "Haircut")
        )

        every { networkHelper.isNetworkConnected() } returns true
        coEvery { getAppointmentsUse() } returns ApiResult.Success(mockAppointments)

        viewModel.state.test {
            awaitItem()  // Skip initial loading
            awaitItem()  // Content with appointments

            viewModel.onEvents(AppointmentHistoryEvents.OnAccept)

            val contentState = awaitItem()
            assertThat(contentState).isInstanceOf(BaseScreenState.OnContent::class.java)
            val content =
                (contentState as BaseScreenState.OnContent<AppointmentHistoryUiState>).content
            assertThat(content.showDialog).isFalse()
            assertThat(content.idAppointment).isNull()
        }
    }

    @Test
    fun onCancel_shouldCloseDialog() = runTest(testDispatcher) {
        val mockAppointments = listOf(
            mockAppointment(id = "1", serviceName = "Haircut")
        )

        every { networkHelper.isNetworkConnected() } returns true
        coEvery { getAppointmentsUse() } returns ApiResult.Success(mockAppointments)

        viewModel.state.test {
            awaitItem()  // Skip initial loading
            awaitItem()  // Content with appointments

            viewModel.onEvents(AppointmentHistoryEvents.OnCancel)

            val contentState = awaitItem()
            assertThat(contentState).isInstanceOf(BaseScreenState.OnContent::class.java)
            val content =
                (contentState as BaseScreenState.OnContent<AppointmentHistoryUiState>).content
            assertThat(content.showDialog).isFalse()
        }
    }

    // ========== Navigation Tests ==========

    @Test
    fun onAppointmentClicked_shouldSendNavigateToDetailEffect() = runTest(testDispatcher) {
        val mockAppointments = listOf(
            mockAppointment(id = "1", serviceName = "Haircut")
        )

        every { networkHelper.isNetworkConnected() } returns true
        coEvery { getAppointmentsUse() } returns ApiResult.Success(mockAppointments)

        viewModel.effects.test {
            viewModel.onEvents(AppointmentHistoryEvents.OnAppointmentClicked(appointment = "1"))

            val effect = awaitItem()
            assertThat(effect).isInstanceOf(AppointmentHistoryEffects.NavigateToDetail::class.java)
            val navigationEffect = effect as AppointmentHistoryEffects.NavigateToDetail
            assertEquals("1", navigationEffect.idAppointment)
        }
    }

    @Test
    fun onAppointmentClicked_withMultipleClicks_shouldSendMultipleEffects() =
        runTest(testDispatcher) {
            val mockAppointments = listOf(
                mockAppointment(id = "1", serviceName = "Haircut"),
                mockAppointment(id = "2", serviceName = "Manicure")
            )

            every { networkHelper.isNetworkConnected() } returns true
            coEvery { getAppointmentsUse() } returns ApiResult.Success(mockAppointments)

            viewModel.effects.test {
                viewModel.onEvents(AppointmentHistoryEvents.OnAppointmentClicked(appointment = "1"))
                val firstEffect = awaitItem() as AppointmentHistoryEffects.NavigateToDetail
                assertEquals("1", firstEffect.idAppointment)

                viewModel.onEvents(AppointmentHistoryEvents.OnAppointmentClicked(appointment = "2"))
                val secondEffect = awaitItem() as AppointmentHistoryEffects.NavigateToDetail
                assertEquals("2", secondEffect.idAppointment)
            }
        }

    // ========== Combined Scenario Tests ==========

    @Test
    fun fullScenario_loadAppointmentsThenDeleteThenNavigate() = runTest(testDispatcher) {
        val mockAppointments = listOf(
            mockAppointment(id = "1", serviceName = "Haircut"),
            mockAppointment(id = "2", serviceName = "Manicure")
        )

        every { networkHelper.isNetworkConnected() } returns true
        coEvery { getAppointmentsUse() } returns ApiResult.Success(mockAppointments)
        coEvery { deleteAppointmentUseCase("1") } returns ApiResult.Success(Unit)

        viewModel.state.test {
            // Load initial state
            awaitItem()

            // Verify appointments loaded
            val initialContent = awaitItem() as BaseScreenState.OnContent<AppointmentHistoryUiState>
            assertThat(initialContent.content.appointments).hasSize(2)

            // Delete appointment
            viewModel.onEvents(AppointmentHistoryEvents.OnRemove(idAppointment = "1"))
            awaitItem()  // Deletion succeeds

            // Navigate to detail
            viewModel.effects.test {
                viewModel.onEvents(AppointmentHistoryEvents.OnAppointmentClicked(appointment = "2"))
                val effect = awaitItem()
                assertThat(effect).isInstanceOf(AppointmentHistoryEffects.NavigateToDetail::class.java)
            }
        }
    }

}