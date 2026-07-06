package com.example.citassalon.presentacion.features.info.nuestro_staff

import app.cash.turbine.test
import com.example.citassalon.presentacion.features.base.BaseScreenState
import com.example.data.Repository
import com.example.domain.entities.remote.dummyUsers.User
import com.example.domain.state.ApiResult
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class OurStaffViewModelTest {


    private val testDispatcher = StandardTestDispatcher()
    private val repository: Repository = mockk()
    private lateinit var viewModel: OurStaffViewModel

    val users = List(6) { index ->
        mockk<User> {
            every { id } returns index + 1
            every { firstName } returns "User ${index + 1}"
        }
    }

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = OurStaffViewModel(
            repository = repository,
            ioDispatcher = testDispatcher
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }


    @Test
    fun `initial state is OnLoading`() = runTest {
        coEvery { repository.getStaffUsers() } returns ApiResult.Success(emptyList())


        viewModel.state.test {
            assertThat(awaitItem()).isInstanceOf(BaseScreenState.OnLoading::class.java)
        }
    }

    private suspend fun assertStaffLoadedSuccessfully(users: List<User>) {

        coEvery { repository.getStaffUsers() } returns ApiResult.Success(users)


        viewModel.state.test {

            assertThat(awaitItem()).isInstanceOf(BaseScreenState.OnLoading::class.java)

            val content = awaitItem()

            assertThat(content).isInstanceOf(BaseScreenState.OnContent::class.java)
            assertThat((content as BaseScreenState.OnContent).content).isEqualTo(
                OurStaffUiState(
                    staffs = users
                )
            )
            coVerify(exactly = 1) { repository.getStaffUsers() }
        }
    }

    @Test
    fun `state emits OnContent with staff list on success`() = runTest {
        assertStaffLoadedSuccessfully(users)
    }

    @Test
    fun `state emits OnContent with empty list when repository returns empty`() = runTest {
        assertStaffLoadedSuccessfully(emptyList())
    }


    @Test
    fun `state emits OnError when repository throws`() = runTest {
        val exception = RuntimeException("Network error")
        coEvery { repository.getStaffUsers() } throws exception


        viewModel.state.test {

            assertThat(awaitItem()).isInstanceOf(BaseScreenState.OnLoading::class.java)

            val error = awaitItem()
            assertThat(error).isInstanceOf(BaseScreenState.OnError::class.java)
            assertThat(exception).isEqualTo((error as BaseScreenState.OnError).error)
            coVerify(exactly = 1) { repository.getStaffUsers() }

        }
    }

}