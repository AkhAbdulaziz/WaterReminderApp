package uz.gita.waterreminder.ui.screens.base_pages

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.waterreminder.R
import uz.gita.waterreminder.databinding.PageBaseSettingsBinding
import uz.gita.waterreminder.ui.dialogs.*
import uz.gita.waterreminder.ui.viewmodels.base_viewmodels.SettingsPageViewModel
import uz.gita.waterreminder.util.cancelRequest
import uz.gita.waterreminder.util.createPeriodicWorkRequest
import uz.gita.waterreminder.util.scope

@AndroidEntryPoint
class SettingsPage : Fragment(R.layout.page_base_settings) {
    private val binding by viewBinding(PageBaseSettingsBinding::bind)
    private val viewModel by viewModels<SettingsPageViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = binding.scope {
        super.onViewCreated(view, savedInstanceState)
        reminderSwitch.colorBorder = ContextCompat.getColor(requireContext(), R.color.blue)
        reminderSwitch.colorOn = ContextCompat.getColor(requireContext(), R.color.blue)
        setTexts()
        setClickListenerToViews()

    }

    private fun setTexts() = binding.scope {
        changeTargetWaterText.text = "${viewModel.getTargetSize()}"
        changeGenderText.text = viewModel.getGender()
        changeWeightText.text = "${viewModel.getWeight()} ${viewModel.getWeightUnit()}"
        changeWakeUpTimeText.text = viewModel.getWakeUpTime()
        changeSleepingTimeText.text = viewModel.getBedTime()
        if (viewModel.getNotificationStatus()) {
            reminderSwitch.labelOn
        } else {
            reminderSwitch.labelOff
        }
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
            cancelAndSetNewNotificationAndCheckNewDay()
        }

        changeSleepingTime.setOnClickListener {
            val changeSleepingTimeDialog =
                ChangeSleepingTimeDialog(viewModel.getBedTime())

            changeSleepingTimeDialog.setOkButtonClickListener { selectedHour, selectedMinute ->
                viewModel.setBedTime("$selectedHour:$selectedMinute")
                changeSleepingTimeText.text = "$selectedHour:$selectedMinute"
            }
            changeSleepingTimeDialog.show(childFragmentManager, "BedTimeChanged")
            cancelAndSetNewNotificationAndCheckNewDay()
        }

        changeReminder.setOnClickListener {
            if (viewModel.getNotificationStatus()) {
                viewModel.setNotificationStatus(false)
                reminderSwitch.labelOff
            } else {
                viewModel.setNotificationStatus(true)
                reminderSwitch.labelOn
            }
            cancelAndSetNewNotificationAndCheckNewDay()
        }
    }

    private fun cancelAndSetNewNotificationAndCheckNewDay() {
        cancelRequest(viewModel.getCurrentNotificationRequestId())
        viewModel.setCurrentNotificationRequestId(
            createPeriodicWorkRequest(
                2,
                viewModel.getNotificationMaxId()
            )
        )
        viewModel.checkNewDay()
    }
}
