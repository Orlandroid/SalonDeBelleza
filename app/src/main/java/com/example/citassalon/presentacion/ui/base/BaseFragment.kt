package com.example.citassalon.presentacion.ui.base


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.navigation.fragment.findNavController
import com.example.citassalon.R
import com.example.citassalon.domain.state.ApiState
import com.example.citassalon.presentacion.main.AlertDialogs
import com.example.citassalon.presentacion.ui.extensions.gone
import com.example.citassalon.presentacion.ui.extensions.hideProgress
import com.example.citassalon.presentacion.ui.extensions.showProgress
import com.example.citassalon.presentacion.ui.extensions.visible
import com.example.citassalon.presentacion.ui.share_beetwen_sucursales.SucursalAdapter
import com.example.citassalon.presentacion.util.ERROR_SERVIDOR

abstract class BaseFragment<ViewBinding : ViewDataBinding>(@LayoutRes protected val contentLayoutId: Int) :
    Fragment() {

    protected lateinit var binding: ViewBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, contentLayoutId, container, false)
        return binding.root
    }

    protected abstract fun setUpUi()

    open fun observerViewModel() {

    }

    fun <T> showAndHideProgress(it: ApiState<T>) {
        if (it is ApiState.Loading) {
            showProgress()
        } else {
            hideProgress()
        }
    }


    fun <T> Fragment.observeApiResultGeneric(
        liveData: LiveData<ApiState<T>>,
        onLoading: () -> Unit = {},
        onFinishLoading: () -> Unit = {},
        haveTheViewProgress: Boolean = true,
        isDetailView: Boolean = false,
        onSuccess: (data: T) -> Unit,
    ) {
        liveData.observe(viewLifecycleOwner) { apiState ->
            if (apiState is ApiState.Loading) {
                if (haveTheViewProgress) {
                    showProgress()
                } else {
                    onLoading()
                }
            } else {
                if (haveTheViewProgress) {
                    hideProgress()
                } else {
                    onFinishLoading()
                }
            }
            when (apiState) {
                is ApiState.Success -> {
                    if (apiState.data != null) {
                        onSuccess(apiState.data)
                    }
                }
                is ApiState.Error -> {
                    val dialog = AlertDialogs(AlertDialogs.ERROR_MESSAGE, "Error al obtener datos")
                    activity?.let { dialog.show(it.supportFragmentManager, "alertMessage") }
                    if (isDetailView) {
                        findNavController().popBackStack()
                    }
                }
                is ApiState.ErrorNetwork -> {
                    val dialog =
                        AlertDialogs(AlertDialogs.ERROR_MESSAGE, "Verifica tu conexion de internet")
                    activity?.let { dialog.show(it.supportFragmentManager, "alertMessage") }
                    if (isDetailView) {
                        findNavController().popBackStack()
                    }
                }
                else -> {}
            }
        }
    }


}