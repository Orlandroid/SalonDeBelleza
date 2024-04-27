package com.example.citassalon.presentacion.features.splashscreen

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.citassalon.R
import com.example.citassalon.databinding.FragmentGenericBindingBinding
import com.example.citassalon.presentacion.features.base.BaseFragment
import com.example.citassalon.presentacion.features.extensions.navigateAction
import com.example.citassalon.presentacion.features.theme.StatusBarColor


@SuppressLint("CustomSplashScreen")
class SplashScreenFragment :
    BaseFragment<FragmentGenericBindingBinding>(R.layout.fragment_generic_binding) {


    override fun setUpUi() {

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                SplashScreen()
            }
        }
    }

    @Composable
    fun SplashScreen() {
        ConstraintLayout(
            Modifier
                .fillMaxSize()
                .background(StatusBarColor)
        ) {
            val (image, text) = createRefs()
            Text(
                color = Color.White,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 40.sp,
                text = stringResource(id = R.string.skeduly),
                modifier = Modifier.constrainAs(text) {
                    linkTo(parent.start, parent.end)
                    linkTo(parent.top, image.top)
                    width = Dimension.fillToConstraints
                    height = Dimension.wrapContent
                }
            )
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "ImageSplashScreen",
                modifier = Modifier.constrainAs(image) {
                    linkTo(parent.start, parent.end)
                    linkTo(parent.top, parent.bottom)
                    width = Dimension.fillToConstraints
                    height = Dimension.value(300.dp)
                }
            )
            //Todo add efect of fadeout to the all screen
            AnimatedVisibility(
                visible = true,
                exit = fadeOut(animationSpec = tween(1000))
            ) {
                navigateAction(SplashScreenFragmentDirections.actionSplashScreenFragmentToLogin())
            }
        }
    }
    
}