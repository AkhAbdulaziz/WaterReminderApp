package uz.gita.waterreminder.ui.screens.base_pages

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
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
    private var canIDelete = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = binding.scope {
        drinksRecyclerView.adapter = adapter
        drinksRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        loadData()
        viewModel.drinksListLiveData.observe(viewLifecycleOwner, drinksListObserver)
        checkHeartImageStatus()

        requireActivity().onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    requireActivity().finish()
                }
            })

        adapter.setDeleteListener {pos, data ->
//            viewModel.reduceProgressValue(data.glassSize)
//            setTextsToProgress()
            viewModel.deleteDrunkItem(data)
            drinkList.remove(data)
            adapter.notifyItemRemoved(pos)
//            checkHeartImageStatus()
        }

        glassImage.setOnClickListener {
            it.isClickable = false
            lifecycleScope.launch {
                delay(1500)
                it.isClickable = true
            }
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
                R.drawable.glass_item,
                "$currentHour : $currentMinute",
                viewModel.getGlassSize(),
                false
            )
            viewModel.insertDrunkItem(data)
            drinkList.add(0, data)
            adapter.notifyItemInserted(0)
            drinksRecyclerView.scrollToPosition(0)

            checkHeartImageStatus()
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

    private fun checkHeartImageStatus() = binding.scope {
        if (viewModel.getConsumedWater() >= viewModel.getTargetSize()) {
            heartImgStatus.setImageResource(R.drawable.live_heart)
            heartImgStatus.alpha = 1f
        } else {
            heartImgStatus.setImageResource(R.drawable.dry_heart)
            heartImgStatus.alpha = 0.7f
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
