package com.example.citassalon.presentacion.features.auth.forgetpassword

import app.cash.turbine.test
import com.google.android.gms.tasks.Task
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import com.example.data.remote.auth.AuthRepository


@OptIn(ExperimentalCoroutinesApi::class)
class ForgetPasswordViewmodelTest {

    private lateinit var viewModel: ForgetPasswordViewmodel
    private lateinit var authRepository: AuthRepository
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        authRepository = mockk(relaxed = true)
        viewModel =
            spyk(objToCopy = ForgetPasswordViewmodel(authRepository), recordPrivateCalls = true)
    }

    @After
    fun tearDown() {
        Dispatchers.setMain(Dispatchers.Default)
    }

    @Test
    fun `initial state should have correct default values`() = runTest {
        val initialState = viewModel.state.value

        assertEquals(null, initialState.userEmail)
        assertEquals(false, initialState.enableButton)
        assertEquals(false, initialState.showErrorInvalidEmail)
        assertEquals(false, initialState.isLoading)
    }

    @Test
    fun `on ResetPassword should update state`() = runTest(testDispatcher) {

        val mockTask = mockk<Task<Void>>()
        every { mockTask.isSuccessful } returns false


        viewModel.onEvents(ForgetPasswordViewmodel.ForgetPasswordEvents.OnResetPassword)

        val state = viewModel.state.value

        assertEquals(true, state.isLoading)
        verify(exactly = 1) { authRepository.forgetPassword(any()) }

        viewModel.effects.test {
            val effect = awaitItem()
            assert(effect is ForgetPasswordViewmodel.ForgetPasswordEffects.ShowSnackBar)
        }
    }
}