package com.example.domain.usecases

import com.example.domain.AppointmentFirebase
import com.example.domain.entities.remote.User
import com.example.domain.repository.AppointmentsRepository
import com.example.domain.repository.ReminderManager
import com.example.domain.repository.UserRepository
import com.example.model.state.ApiResult
import com.example.model.state.getErrorMessage
import com.example.model.state.models.TransactionType
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SaveAppointmentUseCaseTest {

    private val testDispatcher = StandardTestDispatcher()
    private val appointmentsRepository: AppointmentsRepository = mockk(relaxed = true)
    private val purchaseProductsUseCase: PurchaseProductsUseCase = mockk()
    private val reminderManager: ReminderManager = mockk(relaxed = true)
    private val userRepository: UserRepository = mockk()

    private lateinit var saveAppointmentUseCase: SaveAppointmentUseCase

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        saveAppointmentUseCase = SaveAppointmentUseCase(
            appointmentsRepository = appointmentsRepository,
            purchaseProductsUseCase = purchaseProductsUseCase,
            reminderManager = reminderManager,
            userRepository = userRepository
        )
    }

    @After
    fun tearDown() {
        Dispatchers.setMain(Dispatchers.Default)
    }

    @Test
    fun `when user is logged in, appointment uses real user name and succeeds`() = runTest {
        val user = User(
            uid = "user123",
            name = "Ana Garcia",
            phone = "1234567890",
            email = "ana@example.com",
            password = "password",
            birthDay = "01/01/1990"
        )
        coEvery { userRepository.getUser() } returns ApiResult.Success(user)
        coEvery { appointmentsRepository.saveAppointment(any()) } returns ApiResult.Success(Unit)
        coEvery {
            purchaseProductsUseCase.invoke(
                amount = any(),
                transactionType = any(),
                description = any()
            )
        } returns ApiResult.Success(Unit)

        val appointmentSlot = slot<AppointmentFirebase>()

        val result = saveAppointmentUseCase(
            establishment = "Centro Salon",
            employeeName = "Carlos",
            employeeId = "emp1",
            service = "Haircut",
            date = "15/03/2025",
            hour = "14:30",
            total = "300"
        )

        assertThat(result).isInstanceOf(ApiResult.Success::class.java)

        coVerify { appointmentsRepository.saveAppointment(capture(appointmentSlot)) }
        assertThat(appointmentSlot.captured.clientName).isEqualTo("Ana Garcia")
        assertThat(appointmentSlot.captured.establishment).isEqualTo("Centro Salon")

        coVerify(exactly = 1) {
            appointmentsRepository.bookMasterSchedule(
                branchName = "Centro Salon",
                date = "15/03/2025",
                staffName = "Carlos",
                time = "14:30",
                appointmentId = appointmentSlot.captured.idAppointment
            )
        }

        verify(exactly = 1) {
            reminderManager.scheduleReminder(
                serviceName = "Haircut",
                branchName = "Centro Salon",
                appointmentDateTime = any()
            )
        }

        coVerify(exactly = 1) {
            purchaseProductsUseCase.invoke(
                amount = 100L,
                transactionType = TransactionType.SERVICE_PAYMENT,
                description = "Haircut at Centro Salon"
            )
        }
    }

    @Test
    fun `when getUser fails, clientName falls back to Customer`() = runTest {
        coEvery { userRepository.getUser() } returns ApiResult.Error("User not found")
        coEvery { appointmentsRepository.saveAppointment(any()) } returns ApiResult.Success(Unit)
        coEvery { purchaseProductsUseCase.invoke(any(), any(), any()) } returns ApiResult.Success(Unit)

        val appointmentSlot = slot<AppointmentFirebase>()

        val result = saveAppointmentUseCase(
            establishment = "Centro Salon",
            employeeName = "Carlos",
            employeeId = "emp1",
            service = "Haircut",
            date = "15/03/2025",
            hour = "14:30",
            total = "300"
        )

        assertThat(result).isInstanceOf(ApiResult.Success::class.java)
        coVerify { appointmentsRepository.saveAppointment(capture(appointmentSlot)) }
        assertThat(appointmentSlot.captured.clientName).isEqualTo("Customer")
    }

    @Test
    fun `when saveAppointment fails, execution short circuits and side effects are skipped`() = runTest {
        coEvery { userRepository.getUser() } returns ApiResult.Error()
        coEvery { appointmentsRepository.saveAppointment(any()) } returns ApiResult.Error("Database error")

        val result = saveAppointmentUseCase(
            establishment = "Centro Salon",
            employeeName = "Carlos",
            employeeId = "emp1",
            service = "Haircut",
            date = "15/03/2025",
            hour = "14:30",
            total = "300"
        )

        assertThat(result).isInstanceOf(ApiResult.Error::class.java)
        assertThat(result.getErrorMessage()).isEqualTo("Database error")

        coVerify(exactly = 0) { appointmentsRepository.bookMasterSchedule(any(), any(), any(), any(), any()) }
        verify(exactly = 0) { reminderManager.scheduleReminder(any(), any(), any()) }
        coVerify(exactly = 0) { purchaseProductsUseCase.invoke(any(), any(), any()) }
    }

    @Test
    fun `when saveAppointment fails with null message, fallback error message is returned`() = runTest {
        coEvery { userRepository.getUser() } returns ApiResult.Error()
        coEvery { appointmentsRepository.saveAppointment(any()) } returns ApiResult.Error(null)

        val result = saveAppointmentUseCase(
            establishment = "Centro Salon",
            employeeName = "Carlos",
            employeeId = "emp1",
            service = "Haircut",
            date = "15/03/2025",
            hour = "14:30",
            total = "300"
        )

        assertThat(result).isInstanceOf(ApiResult.Error::class.java)
        assertThat(result.getErrorMessage()).isEqualTo("Error saving appointment")
    }

    @Test
    fun `when date format is invalid, reminder is skipped but appointment process succeeds`() = runTest {
        coEvery { userRepository.getUser() } returns ApiResult.Error()
        coEvery { appointmentsRepository.saveAppointment(any()) } returns ApiResult.Success(Unit)
        coEvery { purchaseProductsUseCase.invoke(any(), any(), any()) } returns ApiResult.Success(Unit)

        val result = saveAppointmentUseCase(
            establishment = "Centro Salon",
            employeeName = "Carlos",
            employeeId = "emp1",
            service = "Haircut",
            date = "invalid-date-format",
            hour = "invalid-hour",
            total = "300"
        )

        assertThat(result).isInstanceOf(ApiResult.Success::class.java)

        verify(exactly = 0) { reminderManager.scheduleReminder(any(), any(), any()) }
        coVerify(exactly = 1) { appointmentsRepository.bookMasterSchedule(any(), any(), any(), any(), any()) }
        coVerify(exactly = 1) { purchaseProductsUseCase.invoke(any(), any(), any()) }
    }

    @Test
    fun `when purchase fails, returns purchase error message`() = runTest {
        coEvery { userRepository.getUser() } returns ApiResult.Error()
        coEvery { appointmentsRepository.saveAppointment(any()) } returns ApiResult.Success(Unit)
        coEvery { purchaseProductsUseCase.invoke(any(), any(), any()) } returns ApiResult.Error("Insufficient balance")

        val result = saveAppointmentUseCase(
            establishment = "Centro Salon",
            employeeName = "Carlos",
            employeeId = "emp1",
            service = "Haircut",
            date = "15/03/2025",
            hour = "14:30",
            total = "300"
        )

        assertThat(result).isInstanceOf(ApiResult.Error::class.java)
        assertThat(result.getErrorMessage()).isEqualTo("Insufficient balance")
    }

    @Test
    fun `when purchase fails with null message, fallback error message is returned`() = runTest {
        coEvery { userRepository.getUser() } returns ApiResult.Error()
        coEvery { appointmentsRepository.saveAppointment(any()) } returns ApiResult.Success(Unit)
        coEvery { purchaseProductsUseCase.invoke(any(), any(), any()) } returns ApiResult.Error(null)

        val result = saveAppointmentUseCase(
            establishment = "Centro Salon",
            employeeName = "Carlos",
            employeeId = "emp1",
            service = "Haircut",
            date = "15/03/2025",
            hour = "14:30",
            total = "300"
        )

        assertThat(result).isInstanceOf(ApiResult.Error::class.java)
        assertThat(result.getErrorMessage()).isEqualTo("Error processing payment")
    }
}
