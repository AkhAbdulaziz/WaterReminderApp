package uz.gita.waterreminder.ui.screens.intro_pages

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.waterreminder.R
import uz.gita.waterreminder.databinding.PageIntroGenderBinding
import uz.gita.waterreminder.ui.viewmodels.intro_viewmodels.GenderPageViewModel
import uz.gita.waterreminder.util.scope

@AndroidEntryPoint
class GenderPage : Fragment(R.layout.page_intro_gender) {
    private val binding by viewBinding(PageIntroGenderBinding::bind)
    private val viewModel by viewModels<GenderPageViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = binding.scope {
        super.onViewCreated(view, savedInstanceState)

           requireActivity().onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    requireActivity().finish()
                }
            })

        maleImageAndText.setOnClickListener {
            maleText.setTextColor(ContextCompat.getColor(requireContext(), R.color.blue))
            it.alpha = 1F

            femaleText.setTextColor(ContextCompat.getColor(requireContext(), R.color.grey))
            femaleImageAndText.alpha = 0.5F

            viewModel.setGender("male")
        }

        femaleImageAndText.setOnClickListener {
            femaleText.setTextColor(ContextCompat.getColor(requireContext(), R.color.blue))
            it.alpha = 1F

            maleText.setTextColor(ContextCompat.getColor(requireContext(), R.color.grey))
            maleImageAndText.alpha = 0.5F

            viewModel.setGender("female")
        }
    }
}