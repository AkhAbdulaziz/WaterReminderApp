package uz.gita.waterreminder.ui.screens

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.waterreminder.R
import uz.gita.waterreminder.databinding.ScreeningGeneratingHydrationBinding
import uz.gita.waterreminder.util.scope

@AndroidEntryPoint
class GeneratingHydrationScreen : Fragment(R.layout.screening_generating_hydration) {
    private val binding by viewBinding(ScreeningGeneratingHydrationBinding::bind)
    private val args: GeneratingHydrationScreenArgs by navArgs()
    private val handler = Handler(Looper.getMainLooper())

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = binding.scope {
        userGenderImage.setImageResource(args.userGenderImageId)

        waveHeader.velocity = 3f
        handler.postDelayed({
            findNavController().navigate(GeneratingHydrationScreenDirections.actionGeneratingHydrationScreenToBaseMainScreen())
        }, 2000)
    }
}
