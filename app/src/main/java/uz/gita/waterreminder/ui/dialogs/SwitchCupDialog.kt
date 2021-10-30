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
import uz.gita.waterreminder.databinding.DialogSwitchCupBinding
import uz.gita.waterreminder.util.scope

@AndroidEntryPoint
class SwitchCupDialog(private val currentGlassSize: Int) :
    DialogFragment(R.layout.dialog_switch_cup) {
    private val binding by viewBinding(DialogSwitchCupBinding::bind)
    private var selectedGlassSize = currentGlassSize

    private var okButtonClickListener: ((Int) -> Unit)? = null
    fun setOkButtonClickListener(block: (Int) -> Unit) {
        okButtonClickListener = block
    }

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

        loadClickListeners()

        okBtn.setOnClickListener {
            okButtonClickListener?.invoke(selectedGlassSize)
            dismiss()
        }

        cancelBtn.setOnClickListener {
            dismiss()
        }
    }

    private fun loadClickListeners() = binding.scope {
        val glassSizes = arrayOf(100, 125, 150, 175, 200, 300, 400, 500)

        val images = arrayOf(
            glass100ml,
            glass125ml,
            glass150ml,
            glass175ml,
            glass200ml,
            glass300ml,
            glass400ml,
            glass500ml
        )

        val texts = arrayOf(
            textGlass100ml,
            textGlass125ml,
            textGlass150ml,
            textGlass175ml,
            textGlass200ml,
            textGlass300ml,
            textGlass400ml,
            textGlass500ml
        )

        for (i in glassSizes.indices) {
            images[i].setImageResource(R.drawable.ic_glass1)
            texts[i].setTextColor(ContextCompat.getColor(requireContext(), R.color.item_black))
        }
        images[glassSizes.indexOf(currentGlassSize)].setImageResource(R.drawable.ic_glass2)
        texts[glassSizes.indexOf(currentGlassSize)].setTextColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.blue
            )
        )


        for (i in glassSizes.indices) {
            images[i].setOnClickListener {
                images[i].setImageResource(R.drawable.ic_glass2)
                texts[i].setTextColor(ContextCompat.getColor(requireContext(), R.color.blue))
                selectedGlassSize = glassSizes[i]
                for (j in glassSizes.indices) {
                    if (j != i) {
                        images[j].setImageResource(R.drawable.ic_glass1)
                        texts[j].setTextColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.item_black
                            )
                        )
                    }
                }
            }

            texts[i].setOnClickListener {
                texts[i].setTextColor(ContextCompat.getColor(requireContext(), R.color.blue))
                images[i].setImageResource(R.drawable.ic_glass2)
                selectedGlassSize = glassSizes[i]
                for (j in glassSizes.indices) {
                    if (j != i) {
                        images[j].setImageResource(R.drawable.ic_glass1)
                        texts[j].setTextColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.item_black
                            )
                        )
                    }
                }
            }
        }

        /*  glass100ml.setOnClickListener {
              glass100ml.setImageResource(R.drawable.ic_glass2)
              selectedGlassSize = 100
          }
          textGlass100ml.setOnClickListener {
              glass100ml.setImageResource(R.drawable.ic_glass2)
              selectedGlassSize = 100
          }

          glass125ml.setOnClickListener {
                glass125ml.setImageResource(R.drawable.ic_glass2)
              selectedGlassSize = 125
          }
          textGlass125ml.setOnClickListener {
                glass125ml.setImageResource(R.drawable.ic_glass2)
              selectedGlassSize = 125
          }

          glass150ml.setOnClickListener {
                glass150ml.setImageResource(R.drawable.ic_glass2)
              selectedGlassSize = 150
          }
          textGlass150ml.setOnClickListener {
                glass150ml.setImageResource(R.drawable.ic_glass2)
              selectedGlassSize = 150
          }

          glass175ml.setOnClickListener {
                glass175ml.setImageResource(R.drawable.ic_glass2)
              selectedGlassSize = 175
          }
          textGlass175ml.setOnClickListener {
                glass175ml.setImageResource(R.drawable.ic_glass2)
              selectedGlassSize = 175
          }

          glass200ml.setOnClickListener {
                glass200ml.setImageResource(R.drawable.ic_glass2)
              selectedGlassSize = 200
          }
          textGlass200ml.setOnClickListener {
                glass200ml.setImageResource(R.drawable.ic_glass2)
              selectedGlassSize = 200
          }

          glass300ml.setOnClickListener {
                glass300ml.setImageResource(R.drawable.ic_glass2)
              selectedGlassSize = 300
          }
          textGlass300ml.setOnClickListener {
                glass300ml.setImageResource(R.drawable.ic_glass2)
              selectedGlassSize = 300
          }

          glass400ml.setOnClickListener {
                glass400ml.setImageResource(R.drawable.ic_glass2)
              selectedGlassSize = 400
          }
          textGlass400ml.setOnClickListener {
                glass400ml.setImageResource(R.drawable.ic_glass2)
              selectedGlassSize = 400
          }

          glass500ml.setOnClickListener {
                glass500ml.setImageResource(R.drawable.ic_glass2)
              selectedGlassSize = 500
          }
          textGlass500ml.setOnClickListener {
                glass500ml.setImageResource(R.drawable.ic_glass2)
              selectedGlassSize = 500
          }*/
    }
}