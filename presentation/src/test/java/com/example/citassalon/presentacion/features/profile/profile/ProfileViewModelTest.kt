package com.example.citassalon.presentacion.features.profile.profile

import app.cash.turbine.test
import com.example.data.preferences.LoginPreferences
import com.example.data.remote.auth.AuthRepository
import com.example.domain.state.ApiResult
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@Suppress("UNCHECKED_CAST")
class ProfileViewModelTest {

    private lateinit var viewModel: ProfileViewModel
    private val testDispatcher = StandardTestDispatcher()
    private val repository: com.example.data.Repository = mockk()
    private val authRepository: AuthRepository = mockk()
    private val loginPreferences: LoginPreferences = mockk()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        coEvery { authRepository.getUser() } returns ApiResult.Success(null)
        every { loginPreferences.destroyUserSession() } returns Unit
        coEvery { authRepository.logout() } returns ApiResult.Success(Unit)
        viewModel = ProfileViewModel(
            repository = repository,
            authRepository = authRepository,
            loginPreferences = loginPreferences
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.setMain(Dispatchers.Default)
    }

    @Test
    fun `initial state should have correct default values`() = runTest(testDispatcher) {
        viewModel.uiState.test {
            val initialState = awaitItem()
            assertThat(initialState.showAlertCloseSession).isFalse()
            assertThat(initialState.user).isNull()
            assertThat(initialState.menusProfile).isNotEmpty()
        }
    }

    @Test
    fun `OnCloseSession should open alert dialog`() = runTest(testDispatcher) {
        viewModel.uiState.test {
            awaitItem()  // Skip initial state

            viewModel.onEvents(ProfileEvents.OnCloseSession)

            val updatedState = awaitItem()
            assertThat(updatedState.showAlertCloseSession).isTrue()
        }
    }

    @Test
    fun `OnContactClicked should send NavigateToContacts effect`() = runTest(testDispatcher) {
        viewModel.effects.test {
            viewModel.onEvents(ProfileEvents.OnContactClicked)
            val effect = awaitItem()
            assertThat(effect).isInstanceOf(ProfileEffects.NavigateToContacts::class.java)
        }
    }


    @Test
    fun `OnProfileClicked should send NavigateToProfile effect`() = runTest(testDispatcher) {
        viewModel.effects.test {
            viewModel.onEvents(ProfileEvents.OnProfileClicked)
            val effect = awaitItem()
            assertThat(effect).isInstanceOf(ProfileEffects.NavigateToProfile::class.java)
        }
    }

    @Test
    fun `OnHistoricalClicked should send NavigateToHistory effect`() = runTest(testDispatcher) {
        viewModel.effects.test {
            viewModel.onEvents(ProfileEvents.OnHistoricalClicked)
            val effect = awaitItem()
            assertThat(effect).isInstanceOf(ProfileEffects.NavigateToHistory::class.java)
        }
    }


    @Test
    fun `OnTermAndCondictionsClicked should send NavigateToTermAndCondictions effect`() =
        runTest(testDispatcher) {
            viewModel.effects.test {
                viewModel.onEvents(ProfileEvents.OnTermAndCondictionsClicked)
                val effect = awaitItem()
                assertThat(effect).isInstanceOf(ProfileEffects.NavigateToTermAndCondictions::class.java)
            }
        }

    // ========== Dialog Management Tests ==========


    @Test
    fun `OnDismissDialog should close alert dialog`() = runTest(testDispatcher) {
        viewModel.uiState.test {
            awaitItem()  // Skip initial state

            // First open the dialog
            viewModel.onEvents(ProfileEvents.OnCloseSession)
            awaitItem()

            // Then dismiss it
            viewModel.onEvents(ProfileEvents.OnDismissDialog)

            val updatedState = awaitItem()
            assertThat(updatedState.showAlertCloseSession).isFalse()
        }
    }

    @Test
    fun `OnCancel should close alert dialog`() = runTest(testDispatcher) {
        viewModel.uiState.test {
            awaitItem()  // Skip initial state

            // First open the dialog
            viewModel.onEvents(ProfileEvents.OnCloseSession)
            awaitItem()

            // Then cancel
            viewModel.onEvents(ProfileEvents.OnCancel)

            val updatedState = awaitItem()
            assertThat(updatedState.showAlertCloseSession).isFalse()
        }
    }

    // ========== Logout Tests ==========

    @Test
    fun `OnConfirmClicked should close dialog and send CloseAndOpenActivity effect`() =
        runTest(testDispatcher) {
            viewModel.effects.test {
                viewModel.uiState.test {
                    awaitItem()  // Skip initial ui state

                    // Open dialog first
                    viewModel.onEvents(ProfileEvents.OnCloseSession)
                    awaitItem()

                    // Confirm logout
                    viewModel.onEvents(ProfileEvents.OnConfirmClicked)

                    // Check UI state was updated
                    val finalState = awaitItem()
                    assertThat(finalState.showAlertCloseSession).isFalse()

                    // Check effect was sent
                    val effect = awaitItem()
                    assertThat(effect).isInstanceOf(ProfileEffects.CloseAndOpenActivity::class.java)
                }
            }
        }

    @Test
    fun `OnConfirmClicked should destroy user session`() = runTest(testDispatcher) {
        viewModel.onEvents(ProfileEvents.OnConfirmClicked)
        verify { loginPreferences.destroyUserSession() }
    }

    @Test
    fun `OnConfirmClicked should call logout on authRepository`() = runTest(testDispatcher) {
        viewModel.onEvents(ProfileEvents.OnConfirmClicked)
        coVerify { authRepository.logout() }
    }

    @Test
    fun `OnConfirmClicked when logout fails should still send CloseAndOpenActivity effect`() =
        runTest(testDispatcher) {
            coEvery { authRepository.logout() } returns ApiResult.Error("Logout failed")

            viewModel.effects.test {
                viewModel.onEvents(ProfileEvents.OnConfirmClicked)

                val effect = awaitItem()
                assertThat(effect).isInstanceOf(ProfileEffects.CloseAndOpenActivity::class.java)
            }
        }

    // ========== Multiple Events Sequence Tests ==========

    @Test
    fun `multiple navigation events should send multiple effects in order`() =
        runTest(testDispatcher) {
            viewModel.effects.test {
                viewModel.onEvents(ProfileEvents.OnProfileClicked)
                val firstEffect = awaitItem()
                assertThat(firstEffect).isInstanceOf(ProfileEffects.NavigateToProfile::class.java)

                viewModel.onEvents(ProfileEvents.OnHistoricalClicked)
                val secondEffect = awaitItem()
                assertThat(secondEffect).isInstanceOf(ProfileEffects.NavigateToHistory::class.java)

                viewModel.onEvents(ProfileEvents.OnContactClicked)
                val thirdEffect = awaitItem()
                assertThat(thirdEffect).isInstanceOf(ProfileEffects.NavigateToContacts::class.java)
            }
        }

    @Test
    fun `dialog flow should be open then close then open again`() = runTest(testDispatcher) {
        viewModel.uiState.test {
            awaitItem()  // Skip initial state

            // Open dialog
            viewModel.onEvents(ProfileEvents.OnCloseSession)
            assertThat(awaitItem().showAlertCloseSession).isTrue()

            // Close dialog
            viewModel.onEvents(ProfileEvents.OnDismissDialog)
            assertThat(awaitItem().showAlertCloseSession).isFalse()

            // Open dialog again
            viewModel.onEvents(ProfileEvents.OnCloseSession)
            assertThat(awaitItem().showAlertCloseSession).isTrue()

            // Cancel this time
            viewModel.onEvents(ProfileEvents.OnCancel)
            assertThat(awaitItem().showAlertCloseSession).isFalse()
        }
    }

}