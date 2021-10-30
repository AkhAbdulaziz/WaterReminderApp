package uz.gita.waterreminder.ui.screens.base_pages

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.waterreminder.R
import uz.gita.waterreminder.databinding.PageBaseSettingsBinding
import uz.gita.waterreminder.ui.dialogs.*
import uz.gita.waterreminder.ui.viewmodels.base_viewmodels.SettingsPageViewModel
import uz.gita.waterreminder.util.scope
import uz.gita.waterreminder.util.showToast

@AndroidEntryPoint
class SettingsPage : Fragment(R.layout.page_base_settings) {
    private val binding by viewBinding(PageBaseSettingsBinding::bind)
    private val viewModel by viewModels<SettingsPageViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = binding.scope {
        super.onViewCreated(view, savedInstanceState)
        setTexts()
        setClickListenerToViews()

    }

    private fun setTexts() = binding.scope {
        changeTargetWaterText.text = "${viewModel.getTargetSize()}"
        changeGenderText.text = viewModel.getGender()
        changeWeightText.text = "${viewModel.getWeight()} ${viewModel.getWeightUnit()}"
        changeWakeUpTimeText.text = viewModel.getWakeUpTime()
        changeSleepingTimeText.text = viewModel.getBedTime()
        changeReminder.isChecked = viewModel.getNotificationStatus()
    }

    private fun setClickListenerToViews() = binding.scope {

        changeTargetWater.setOnClickListener {
            val changeTargetWaterDialog = ChangeTargetWaterDialog(viewModel.getTargetSize())

            changeTargetWaterDialog.setOkButtonClickListener { selectedTargetWater ->
                viewModel.setTargetSize(selectedTargetWater)
                viewModel.refreshProgressValue()
                changeTargetWaterText.text = "$selectedTargetWater ml"
            }
            changeTargetWaterDialog.show(childFragmentManager, "TargetWaterChanged")

        }

        changeGender.setOnClickListener {
            val changeGenderDialog = ChangeGenderDialog(viewModel.getGender())

            changeGenderDialog.setOkButtonClickListener { selectedGender ->
                viewModel.setGender(selectedGender)
                changeGenderText.text = selectedGender
            }
            changeGenderDialog.show(childFragmentManager, "GenderChanged")
        }

        changeWeight.setOnClickListener {
            val changeWeightDialog =
                ChangeWeightDialog(viewModel.getWeight(), viewModel.getWeightUnit())

            changeWeightDialog.setOkButtonClickListener { selectedWeight, selectedWeightUnit ->
                viewModel.setWeight(selectedWeight)
                viewModel.setWeightUnit(selectedWeightUnit)
                changeWeightText.text = "$selectedWeight $selectedWeightUnit"
            }
            changeWeightDialog.show(childFragmentManager, "WeightChanged")
        }

        changeWakeUpTime.setOnClickListener {
            val changeWakeUpTimeDialog =
                ChangeWakeUpTimeDialog(viewModel.getWakeUpTime())

            changeWakeUpTimeDialog.setOkButtonClickListener { selectedHour, selectedMinute ->
                viewModel.setWakeUpTime("$selectedHour:$selectedMinute")
                changeWakeUpTimeText.text = "$selectedHour:$selectedMinute"
            }
            changeWakeUpTimeDialog.show(childFragmentManager, "WakeUpTimeChanged")
        }

        changeSleepingTime.setOnClickListener {
            val changeSleepingTimeDialog =
                ChangeSleepingTimeDialog(viewModel.getBedTime())

            changeSleepingTimeDialog.setOkButtonClickListener { selectedHour, selectedMinute ->
                viewModel.setBedTime("$selectedHour:$selectedMinute")
                changeSleepingTimeText.text = "$selectedHour:$selectedMinute"
            }
            changeSleepingTimeDialog.show(childFragmentManager, "BedTimeChanged")
        }

        changeReminder.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                viewModel.setNotificationStatus(true)
                showToast("Swtich ON")
            } else {
                viewModel.setNotificationStatus(false)
                showToast("Switch OFF ")
            }
        }
    }
}