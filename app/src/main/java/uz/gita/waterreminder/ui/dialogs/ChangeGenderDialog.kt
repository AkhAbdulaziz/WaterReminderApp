package uz.gita.waterreminder.ui.dialogs

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.waterreminder.R
import uz.gita.waterreminder.databinding.DialogChangeGenderBinding
import uz.gita.waterreminder.util.scope

@AndroidEntryPoint
class ChangeGenderDialog(private val currentGender: String) :
    DialogFragment(R.layout.dialog_change_gender) {
    private val binding by viewBinding(DialogChangeGenderBinding::bind)
    private var okButtonClickListener: ((String) -> Unit)? = null
    fun setOkButtonClickListener(block: (String) -> Unit) {
        okButtonClickListener = block
    }

    private var selectedGender: String = currentGender

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

        if (currentGender == "male") {
            makeMaleDominant()
        } else {
            makeFemaleDominant()
        }

        maleImageAndText.setOnClickListener {
            makeMaleDominant()
        }

        femaleImageAndText.setOnClickListener {
            makeFemaleDominant()
        }

        okBtn.setOnClickListener {
            okButtonClickListener?.invoke(selectedGender)
            dismiss()
        }

        cancelBtn.setOnClickListener {
            dismiss()
        }
    }

    private fun makeMaleDominant() = binding.scope {
        maleText.setTextColor(ContextCompat.getColor(requireContext(), R.color.blue))
        maleImageAndText.alpha = 1F

        femaleText.setTextColor(ContextCompat.getColor(requireContext(), R.color.grey))
        femaleImageAndText.alpha = 0.5F

        selectedGender = "male"
    }

    private fun makeFemaleDominant() = binding.scope {
        femaleText.setTextColor(ContextCompat.getColor(requireContext(), R.color.blue))
        femaleImageAndText.alpha = 1F

        maleText.setTextColor(ContextCompat.getColor(requireContext(), R.color.grey))
        maleImageAndText.alpha = 0.5F

        selectedGender = "female"
    }
}
