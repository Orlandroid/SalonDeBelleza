package com.example.citassalon.data.remote


import com.example.citassalon.data.api.FakeStoreService
import com.example.citassalon.data.api.WebServices
import com.example.citassalon.data.firebase.FireBaseSource
import com.example.citassalon.data.models.remote.Cart
import com.example.citassalon.data.models.remote.Product
import com.example.citassalon.data.models.remote.Servicio
import com.example.citassalon.data.models.remote.Staff
import com.example.citassalon.data.models.remote.migration.SucursalesResponse
import com.example.citassalon.data.models.remote.ramdomuser.RandomUserResponse
import com.example.citassalon.domain.RemoteDataSource
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSourceImpl @Inject constructor(
    private val fakeStoreService: FakeStoreService,
    private val webServices: WebServices,
    private val fireBaseSource: FireBaseSource,
) : RemoteDataSource {
    override suspend fun getProducts(category: String): List<Product> =
        fakeStoreService.getProducts(category)

    override suspend fun getSingleProduct(id: Int): Product = fakeStoreService.getSingleProduct(id)

    override suspend fun getCategories(): List<String> = fakeStoreService.getCategories()

    override suspend fun getSingleCart(id: Int): Cart = fakeStoreService.getSingleCart(id)

    override suspend fun getSucursales(): SucursalesResponse = webServices.getSucursales()

    override suspend fun getStaffs(): List<Staff> = webServices.getStaff()

    override suspend fun getServices(): List<Servicio> = webServices.getServicios()

    override fun getUser(): FirebaseUser? = fireBaseSource.getUser()

    override fun login(email: String, password: String) = fireBaseSource.login(email, password)

    override fun register(email: String, password: String) =
        fireBaseSource.registrer(email, password)

    override fun forgetPassword(email: String) = fireBaseSource.forgetPassword(email)

    override fun signInWithCredential(credential: AuthCredential) =
        fireBaseSource.signInWithCredential(credential)

    override fun logout() = fireBaseSource.logout()
    override suspend fun randomUser(): RandomUserResponse =
        webServices.randomUser("https://randomuser.me/api/")

}