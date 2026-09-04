package com.example.domain.use_cases

import com.example.domain.entities.remote.products.Product
import com.example.domain.repository.BusinessRepository
import com.example.domain.state.ApiResult
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

import com.google.common.truth.Truth.assertThat
import io.mockk.coVerify

@OptIn(ExperimentalCoroutinesApi::class)
class GetCartInfoUseCaseTest {

    private var getCartInfoUseCase: GetCartInfoUseCase = mockk()
    private val testDispatcher = StandardTestDispatcher()
    private val repository: BusinessRepository = mockk()
    private val getWalletUseCase: GetWalletUseCase = mockk()


    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        getCartInfoUseCase = GetCartInfoUseCase(
            repository = repository,
            getWalletUseCase = getWalletUseCase
        )
    }


    @After
    fun tearDown() {
        Dispatchers.setMain(Dispatchers.Default)
    }


    @Test
    fun `When getAllProducts Return Error `() = runTest {

        coEvery { repository.getAllProducts() } returns ApiResult.Error()
        coEvery { getWalletUseCase.invoke() } returns ApiResult.Error()

        val getInfoResult = getCartInfoUseCase.invoke()

        assertThat(getInfoResult).isInstanceOf(ApiResult.Error::class.java)
        coVerify(exactly = 1) { repository.getAllProducts() }
        coVerify(exactly = 1) { getWalletUseCase.invoke() }

    }


    @Test
    fun `When getAllProducts Return Success but getWallet return Error `() = runTest {

        coEvery { repository.getAllProducts() } returns ApiResult.Error()
        coEvery { getWalletUseCase.invoke() } returns ApiResult.Error()

        val getInfoResult = getCartInfoUseCase.invoke()


        coVerify(exactly = 1) { repository.getAllProducts() }
        coVerify(exactly = 1) { getWalletUseCase.invoke() }
        assertThat(getInfoResult).isInstanceOf(ApiResult.Error::class.java)

    }


    @Test
    fun `When getAllProducts Return Success but getWallet return Success `() = runTest {

        val product: Product = mockk(relaxed = true)
        val productList = listOf(product)

        coEvery { repository.getAllProducts() } returns ApiResult.Success(productList)
        coEvery { getWalletUseCase.invoke() } returns ApiResult.Success(mockk(relaxed = true))

        val getInfoResult = getCartInfoUseCase.invoke()


        coVerify(exactly = 1) { repository.getAllProducts() }
        coVerify(exactly = 1) { getWalletUseCase.invoke() }
        assertThat(getInfoResult).isInstanceOf(ApiResult.Success::class.java)

    }

}