package uz.gita.waterreminder.ui.screens.intro_pages

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.NumberPicker
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.waterreminder.R
import uz.gita.waterreminder.databinding.PageIntroWeightBinding
import uz.gita.waterreminder.ui.viewmodels.intro_viewmodels.WeightPageViewModel
import uz.gita.waterreminder.util.scope

@AndroidEntryPoint
class WeightPage : Fragment(R.layout.page_intro_weight) {
    private val binding by viewBinding(PageIntroWeightBinding::bind)
    private val viewModel by viewModels<WeightPageViewModel>()
    private val units = arrayOf("kg", "lbs")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = binding.scope {
        viewModel.changeImageLiveData.observe(viewLifecycleOwner, changeImageObserver)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            weightPicker.textColor = resources.getColor(R.color.blue)
            unitPicker.textColor = resources.getColor(R.color.blue)
        }

        weightPicker.apply {
            minValue = 0
            maxValue = 200
            value = 70
            setOnValueChangedListener(NumberPicker.OnValueChangeListener { picker, oldVal, newVal ->
                viewModel.setWeight(newVal)
            })
        }

        unitPicker.apply {
            minValue = 0
            maxValue = 1
            displayedValues = units
            setOnValueChangedListener(NumberPicker.OnValueChangeListener { picker, oldVal, newVal ->
                viewModel.setWeightUnit("${units[newVal]}")
            })
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getWeightPageImage()
    }

    private val changeImageObserver = Observer<Int> {
        binding.weightImg.setImageResource(it)
    }
}