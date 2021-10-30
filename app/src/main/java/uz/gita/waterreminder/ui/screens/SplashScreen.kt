package uz.gita.waterreminder.ui.screens

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.airbnb.lottie.LottieAnimationView
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.waterreminder.R
import uz.gita.waterreminder.databinding.ScreenSlpashBinding
import uz.gita.waterreminder.ui.viewmodels.SplashViewModel
import uz.gita.waterreminder.util.scope

@AndroidEntryPoint
class SplashScreen : Fragment(R.layout.screen_slpash) {
    private val binding by viewBinding(ScreenSlpashBinding::bind)
    private val viewModel by viewModels<SplashViewModel>()
    private var animation: LottieAnimationView? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = binding.scope {
        super.onViewCreated(view, savedInstanceState)
        animation = animationView
        animation!!.playAnimation()

        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            if (viewModel.isFirstTimeUser()) {
                viewModel.noLongerFirstTimeUser()
                findNavController().navigate(SplashScreenDirections.actionSplashScreenToIntroMainScreen())

            } else {
                viewModel.checkNewDay()
                findNavController().navigate(SplashScreenDirections.actionSplashScreenToBaseMainScreen())
            }
        }, 3000)
    }

    override fun onDestroy() {
        super.onDestroy()
        animation = null
    }
}