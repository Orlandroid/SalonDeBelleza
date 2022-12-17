package com.example.citassalon.ui.base


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.example.citassalon.data.state.ApiState
import com.example.citassalon.ui.extensions.hideProgress
import com.example.citassalon.ui.extensions.showProgress

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

}