package com.example.citassalon.presentacion.features.perfil.perfil

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.fragment.app.viewModels
import com.example.citassalon.R
import com.example.citassalon.databinding.FragmentGenericBindingBinding
import com.example.citassalon.presentacion.features.MainActivity
import com.example.citassalon.presentacion.features.base.BaseFragment
import com.example.citassalon.presentacion.features.extensions.navigate
import com.example.citassalon.presentacion.features.theme.Background
import com.example.citassalon.presentacion.interfaces.ClickOnItem
import com.example.citassalon.presentacion.interfaces.ListenerAlertDialogWithButtons
import com.example.citassalon.presentacion.util.AlertsDialogMessages
import com.example.domain.perfil.MENU
import com.example.domain.perfil.ProfileItem
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PerfilFragment : BaseFragment<FragmentGenericBindingBinding>(R.layout.fragment_generic_binding),
    ListenerAlertDialogWithButtons, ClickOnItem<String> {

    private val viewModelPerfil: PerfilViewModel by viewModels()


    override fun configureToolbar() = MainActivity.ToolbarConfiguration(
        showToolbar = true, toolbarTitle = getString(R.string.perfil)
    )

    override fun setUpUi() {

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                ProfileScreen(viewModelPerfil)
            }
        }
    }

    @Composable
    fun ProfileScreen(myProfileViewModel: PerfilViewModel) {
        val firebaseUser = myProfileViewModel.firebaseUser.observeAsState()
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .background(Background)
        ) {
            val (image, textUser, listProfile) = createRefs()
            val myGuideline = createGuidelineFromTop(0.40f)
            Image(painter = painterResource(id = R.drawable.perfil),
                contentDescription = null,
                modifier = Modifier.constrainAs(image) {
                    linkTo(parent.start, parent.end)
                    top.linkTo(parent.top, 24.dp)
                    width = Dimension.value(100.dp)
                    height = Dimension.value(100.dp)
                })
            Text(text = firebaseUser.value?.email ?: "",
                fontSize = 20.sp,
                modifier = Modifier.constrainAs(textUser) {
                    linkTo(parent.start, parent.end)
                    top.linkTo(image.bottom)
                    bottom.linkTo(listProfile.top)
                    width = Dimension.wrapContent
                    height = Dimension.wrapContent
                })
            LazyColumn(modifier = Modifier
                .border(
                    shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
                    border = BorderStroke(0.dp, Color.White)
                )
                .background(Color.White)
                .constrainAs(listProfile) {
                    linkTo(parent.start, parent.end)
                    bottom.linkTo(parent.bottom)
                    top.linkTo(myGuideline)
                    width = Dimension.matchParent
                    height = Dimension.fillToConstraints
                }) {
                setElementsMenu().forEach { itemProfile ->
                    item {
                        ItemProfile(elementProfile = itemProfile) {
                            clickOnItem(itemProfile)
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun ItemProfile(
        elementProfile: ProfileItem, clickOnItem: () -> Unit
    ) {
        Card(shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            onClick = { clickOnItem.invoke() }) {
            ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
                val (row, icon) = createRefs()
                Row(verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.constrainAs(row) {
                        start.linkTo(parent.start)
                        end.linkTo(icon.start, 16.dp)
                        width = Dimension.matchParent
                    }) {
                    Spacer(modifier = Modifier.width(16.dp))
                    Image(
                        painter = painterResource(id = elementProfile.image),
                        contentDescription = null,
                        modifier = Modifier
                            .height(50.dp)
                            .width(50.dp)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = elementProfile.name, fontSize = 20.sp
                    )
                }
                Icon(painter = painterResource(id = R.drawable.ic_baseline_arrow_forward_24),
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier.constrainAs(icon) {
                        end.linkTo(parent.end, 16.dp)
                        linkTo(parent.top, parent.bottom)
                    })
            }
        }
    }

    private fun setElementsMenu(): List<ProfileItem> {
        val elementsMenu = arrayListOf<ProfileItem>()
        val perfil = ProfileItem("Perfil", R.drawable.perfil, MENU.PROFILE)
        val historial = ProfileItem("Historial de citas", R.drawable.historial_menu, MENU.HISTORY)
        val contactanos = ProfileItem("Contactanos", R.drawable.contactos, MENU.CONTACTS)
        val terminos =
            ProfileItem("Terminos y condiciones", R.drawable.terminos, MENU.TERMS_AND_CONDITIONS)
        val cerrarSesion = ProfileItem("Cerrar sesion", R.drawable.cerrar, MENU.CLOSE_SESSION)
        elementsMenu.add(perfil)
        elementsMenu.add(historial)
        elementsMenu.add(contactanos)
        elementsMenu.add(terminos)
        elementsMenu.add(cerrarSesion)
        return elementsMenu
    }

    fun clickOnItem(itemProfile: ProfileItem) {
        when (itemProfile.menu) {
            MENU.PROFILE -> {
                navigate(PerfilFragmentDirections.actionPerfilToUserProfileFragment())
            }

            MENU.HISTORY -> {
                navigate(PerfilFragmentDirections.actionPerfilToHistorialDeCitas())
            }

            MENU.CONTACTS -> {
                Log.w("uno", "unos")
            }

            MENU.TERMS_AND_CONDITIONS -> {
                showTermAndCondition()
            }

            MENU.CLOSE_SESSION -> {
                logout()
            }
        }
    }


    override fun clickOnItem(element: String, position: Int?) {
        when (position) {
            1 -> {
                val action = PerfilFragmentDirections.actionPerfilToUserProfileFragment()
                navigate(action)
            }

            2 -> {
                val action = PerfilFragmentDirections.actionPerfilToHistorialDeCitas()
                navigate(action)
            }

            3 -> Log.w("uno", "unos")
            4 -> showTermAndCondition()
            5 -> logout()
        }
    }

    override fun observerViewModel() {
        super.observerViewModel()
        viewModelPerfil.firebaseUser.observe(viewLifecycleOwner) { firebaseUser ->
            firebaseUser?.let {
                //binding.nombreUsuario.text = it.email
            }
        }
    }

    private fun logout() {
        val alert = AlertsDialogMessages(requireContext())
        alert.alertPositiveAndNegative(
            this,
            resources.getString(R.string.cerrar_session),
            resources.getString(R.string.seguro_que_deseas_cerrar_sesion)
        )
    }

    private fun showTermAndCondition() {
        val alert = AlertsDialogMessages(requireContext())
        alert.showTermAndConditions()
    }


    override fun clickOnConfirmar() {
        viewModelPerfil.logout()
        val intent = Intent(requireContext(), MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    override fun clickOnCancel() {

    }

}