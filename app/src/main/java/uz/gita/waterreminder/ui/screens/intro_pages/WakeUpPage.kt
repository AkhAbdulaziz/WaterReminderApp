package uz.gita.waterreminder.ui.screens.intro_pages

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.waterreminder.R
import uz.gita.waterreminder.databinding.PageIntroWakeupBinding
import uz.gita.waterreminder.ui.viewmodels.intro_viewmodels.WakeUpPageViewModel
import uz.gita.waterreminder.util.scope

@AndroidEntryPoint
class WakeUpPage : Fragment() {
    private val binding by viewBinding(PageIntroWakeupBinding::bind)
    private val viewModel by viewModels<WakeUpPageViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.page_intro_wakeup, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = binding.scope {
        viewModel.changeImageLiveData.observe(viewLifecycleOwner, changeImageObserver)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            hourPicker.textColor = resources.getColor(R.color.blue)
            minutePicker.textColor = resources.getColor(R.color.blue)
        }

        val hours = ArrayList<String>()
        for (i in 0 until 24) {
            if (i < 10) {
                hours.add("0$i")
            } else {
                hours.add("$i")
            }
        }

        hourPicker.apply {
            minValue = 0
            maxValue = 23
            value = 6
            displayedValues = hours.toTypedArray()
            setOnValueChangedListener(NumberPicker.OnValueChangeListener { picker, oldVal, newVal ->
                viewModel.setWakeUpTime("${hours[newVal]}:00")
            })
        }

        val minutes = ArrayList<String>()
        for (i in 0 until 60) {
            if (i < 10) {
                minutes.add("0$i")
            } else {
                minutes.add("$i")
            }
        }

        minutePicker.apply {
            minValue = 0
            maxValue = 59
            value = 0
            displayedValues = minutes.toTypedArray()
            setOnValueChangedListener(NumberPicker.OnValueChangeListener { picker, oldVal, newVal ->
                viewModel.setWakeUpTime("${hourPicker.value}:${minutes[newVal]}")
            })
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getWakeUpPageImage()
    }

    private val changeImageObserver = Observer<Int> {
        binding.wakeupImg.setImageResource(it)
    }
}
