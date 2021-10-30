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
import uz.gita.waterreminder.databinding.DialogChangeWeightBinding
import uz.gita.waterreminder.util.scope

@AndroidEntryPoint
class ChangeWeightDialog(private val currentWeight: Int, private val currentWeightUnit: String) :
    DialogFragment(R.layout.dialog_change_weight) {
    private val binding by viewBinding(DialogChangeWeightBinding::bind)

    private var okButtonClickListener: ((Int, String) -> Unit)? = null
    fun setOkButtonClickListener(block: (Int, String) -> Unit) {
        okButtonClickListener = block
    }

    private var selectedWeight = currentWeight
    private var selectedWeightUnit = currentWeightUnit
    private val units = arrayOf("kg", "lbs")

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
            weightPicker.textColor = resources.getColor(R.color.blue)
            unitPicker.textColor = resources.getColor(R.color.blue)
        }

        weightPicker.value = selectedWeight
        unitPicker.value = if (selectedWeightUnit == "kg") 0 else 1
        weightResult.text = "$selectedWeight $selectedWeightUnit"

        weightPicker.apply {
            minValue = 0
            maxValue = 200
            value = selectedWeight
            setOnValueChangedListener(NumberPicker.OnValueChangeListener { picker, oldVal, newVal ->
                selectedWeight = newVal
                weightResult.text = "$newVal $currentWeightUnit"
            })
        }

        unitPicker.apply {
            minValue = 0
            maxValue = 1
            value = if (selectedWeightUnit == "kg") 0 else 1
            displayedValues = units
            setOnValueChangedListener(NumberPicker.OnValueChangeListener { picker, oldVal, newVal ->
                selectedWeightUnit = units[newVal]
                weightResult.text = "$selectedWeight ${units[newVal]}"
            })
        }

        okBtn.setOnClickListener {
            okButtonClickListener?.invoke(selectedWeight, selectedWeightUnit)
            dismiss()
        }

        cancelBtn.setOnClickListener {
            dismiss()
        }
    }
}