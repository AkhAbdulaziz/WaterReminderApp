package uz.gita.waterreminder.ui.dialogs

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.fragment.app.DialogFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.waterreminder.R
import uz.gita.waterreminder.databinding.DialogChangeTargetWaterBinding
import uz.gita.waterreminder.util.scope

@AndroidEntryPoint
class ChangeTargetWaterDialog(private val targetWaterSize: Int) :
    DialogFragment(R.layout.dialog_change_target_water) {
    private val binding by viewBinding(DialogChangeTargetWaterBinding::bind)

    private var okButtonClickListener: ((Int) -> Unit)? = null
    fun setOkButtonClickListener(block: (Int) -> Unit) {
        okButtonClickListener = block
    }

    private var selectedTargetWater: Int = targetWaterSize

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

        seekbar.max = 5000
        seekbar.progress = targetWaterSize
        seekBarResult.text = "$targetWaterSize ml"

        seekbar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, b: Boolean) {
                seekBarResult.text = "$progress ml"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                selectedTargetWater = seekBar.progress
            }
        })

        okBtn.setOnClickListener {
            okButtonClickListener?.invoke(selectedTargetWater)
            dismiss()
        }

        cancelBtn.setOnClickListener {
            dismiss()
        }
    }
}