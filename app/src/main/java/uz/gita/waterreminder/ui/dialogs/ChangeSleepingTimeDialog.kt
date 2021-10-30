package uz.gita.waterreminder.ui.dialogs

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.NumberPicker
import androidx.fragment.app.DialogFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.waterreminder.R
import uz.gita.waterreminder.databinding.DialogChangeSleepingTimeBinding
import uz.gita.waterreminder.util.scope

@AndroidEntryPoint
class ChangeSleepingTimeDialog(
    private val sleepingTime: String
) :
    DialogFragment(R.layout.dialog_change_sleeping_time) {
    private val binding by viewBinding(DialogChangeSleepingTimeBinding::bind)

    private var okButtonClickListener: ((String, String) -> Unit)? = null
    fun setOkButtonClickListener(block: (String, String) -> Unit) {
        okButtonClickListener = block
    }

    private var selectedHour: String = sleepingTime.substring(0, sleepingTime.indexOf(':'))
    private var selectedMinute: String = sleepingTime.substring(sleepingTime.indexOf(':') + 1)

    override fun onResume() {
        super.onResume()
        val params: ViewGroup.LayoutParams = dialog!!.window!!.attributes
        params.width = ViewGroup.LayoutParams.MATCH_PARENT
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT
        dialog!!.window!!.attributes = params as WindowManager.LayoutParams
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = binding.scope {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            hourPicker.textColor = resources.getColor(R.color.blue)
            minutePicker.textColor = resources.getColor(R.color.blue)
        }

        hourPicker.value = selectedHour.toInt()
        minutePicker.value = selectedMinute.toInt()
        sleepingResult.text = "$selectedHour $selectedMinute"

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
                selectedHour = hours[newVal]
                sleepingResult.text = "${hours[newVal]} $selectedMinute"
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
                selectedMinute = minutes[newVal]
                sleepingResult.text = "$selectedHour ${minutes[newVal]}"
            })
        }

        okBtn.setOnClickListener {
            okButtonClickListener?.invoke(selectedHour, selectedMinute)
            dismiss()
        }

        cancelBtn.setOnClickListener {
            dismiss()
        }
    }
}
