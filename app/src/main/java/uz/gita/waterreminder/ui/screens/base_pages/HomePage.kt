package uz.gita.waterreminder.ui.screens.base_pages

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.waterreminder.R
import uz.gita.waterreminder.data.entity.DrinksItemEntity
import uz.gita.waterreminder.databinding.PageBaseHomeBinding
import uz.gita.waterreminder.ui.adapters.DrinksListAdapter
import uz.gita.waterreminder.ui.dialogs.SwitchCupDialog
import uz.gita.waterreminder.ui.viewmodels.base_viewmodels.HomePageViewModel
import uz.gita.waterreminder.util.animateBottomToTop
import uz.gita.waterreminder.util.scope
import uz.gita.waterreminder.util.visible
import java.util.*
import kotlin.collections.ArrayList

@AndroidEntryPoint
class HomePage() : Fragment(R.layout.page_base_home) {

    private val binding by viewBinding(PageBaseHomeBinding::bind)
    private val viewModel by viewModels<HomePageViewModel>()
    private val drinkList = ArrayList<DrinksItemEntity>()
    private val adapter by lazy { DrinksListAdapter(drinkList) }
    private lateinit var calendar: Calendar
    private var currentHour = 0
    private var currentMinute = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = binding.scope {
        drinksRecyclerView.adapter = adapter
        drinksRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        loadData()
        viewModel.drinksListLiveData.observe(viewLifecycleOwner, drinksListObserver)

        adapter.setDeleteListener { pos ->
            viewModel.reduceProgressValue(drinkList[pos].glassSize)
            setTextsToProgress()
            viewModel.deleteDrunkItem(drinkList[pos])
            drinkList.removeAt(pos)
            adapter.notifyItemRemoved(pos)
        }

        glassImage.setOnClickListener {
            calendar = Calendar.getInstance()
            currentHour = calendar.get(Calendar.HOUR_OF_DAY)
            currentMinute = calendar.get(Calendar.MINUTE)

            addingAnimationText.apply {
                text = "+${viewModel.getGlassSize()} ml Well Done"
                y = targetWaterText.y
                visible()
                animateBottomToTop {
                    alpha = 1f
                    y = targetWaterText.y
//                    translationY = 200f
                    /* scaleX = 1f
                     scaleY = 1f*/
                }
            }

            viewModel.addProgressValue()
            setTextsToProgress()

            val data = DrinksItemEntity(
                0,
                R.drawable.ic_glass1,
                "$currentHour : $currentMinute",
                viewModel.getGlassSize(),
                false
            )
            viewModel.insertDrunkItem(data)
            drinkList.add(0, data)
            adapter.notifyItemInserted(0)
            drinksRecyclerView.scrollToPosition(0)
        }

        switchCupImage.setOnClickListener {
            val switchCupDialog = SwitchCupDialog(viewModel.getGlassSize())
            switchCupDialog.setOkButtonClickListener { selectedGlassSize ->
                viewModel.setGlassSize(selectedGlassSize)
                viewModel.refreshProgressValue()
                setTextsToProgress()
            }
            switchCupDialog.show(childFragmentManager, "GlassCupSwitched")
        }
    }

    private val drinksListObserver = Observer<List<DrinksItemEntity>> {
        drinkList.addAll(it)
        adapter.notifyDataSetChanged()
    }

    override fun onResume() {
        super.onResume()
        setTextsToProgress()
    }

    private fun loadData() {
        drinkList.clear()
        viewModel.getAllDrinksList()
        viewModel.refreshProgressValue()
        setTextsToProgress()
    }

    private fun setTextsToProgress() = binding.scope {
        glassSizeText.text = "${viewModel.getGlassSize()}"
        progressView.progress = viewModel.getProgressValue()
        targetWaterText.text = "/${viewModel.getTargetSize()} ml"
        consumedWaterText.text = "${viewModel.getConsumedWater()}"
    }
}
