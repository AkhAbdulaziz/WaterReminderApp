package uz.gita.waterreminder.ui.screens.intro_screens

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.waterreminder.R
import uz.gita.waterreminder.databinding.ScreenIntroMainBinding
import uz.gita.waterreminder.ui.adapters.IntroMainAdapter
import uz.gita.waterreminder.ui.viewmodels.intro_viewmodels.IntroMainViewModel
import uz.gita.waterreminder.util.gone
import uz.gita.waterreminder.util.scope
import uz.gita.waterreminder.util.visible

@AndroidEntryPoint
class IntroMainScreen : Fragment(R.layout.screen_intro_main) {
    private val binding by viewBinding(ScreenIntroMainBinding::bind)
    private val viewModel: IntroMainViewModel by viewModels()

    @SuppressLint("RestrictedApi")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = binding.scope {
        super.onViewCreated(view, savedInstanceState)
        val adapter = IntroMainAdapter(childFragmentManager, lifecycle)
        introViewPager.adapter = adapter

        val navBarViewIds = arrayOf(
            bottomNavView.getChildAt(0),
            bottomNavView.getChildAt(1),
            bottomNavView.getChildAt(2),
            bottomNavView.getChildAt(3)
        )

        val navBarTextIds = arrayOf(
            bottomNavText.getChildAt(0),
            bottomNavText.getChildAt(1),
            bottomNavText.getChildAt(2),
            bottomNavText.getChildAt(3)
        )

        introViewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                viewModel.changePagePos(position)
                when (introViewPager.currentItem) {
                    0 -> {
                        navBarViewIds[0].setBackgroundResource(R.drawable.ic_bottom_nav_item_bg_colorful)
                        navBarViewIds[1].setBackgroundResource(R.drawable.ic_bottom_nav_item_bg_grey)
                        navBarViewIds[2].setBackgroundResource(R.drawable.ic_bottom_nav_item_bg_grey)
                        navBarViewIds[3].setBackgroundResource(R.drawable.ic_bottom_nav_item_bg_grey)
                        backBtn.gone()
                    }
                    1 -> {
                        navBarViewIds[0].setBackgroundResource(R.drawable.ic_bottom_nav_item_bg_grey)
                        navBarViewIds[1].setBackgroundResource(R.drawable.ic_bottom_nav_item_bg_colorful)
                        navBarViewIds[2].setBackgroundResource(R.drawable.ic_bottom_nav_item_bg_grey)
                        navBarViewIds[3].setBackgroundResource(R.drawable.ic_bottom_nav_item_bg_grey)
                        backBtn.visible()
                    }
                    2 -> {
                        navBarViewIds[0].setBackgroundResource(R.drawable.ic_bottom_nav_item_bg_grey)
                        navBarViewIds[1].setBackgroundResource(R.drawable.ic_bottom_nav_item_bg_grey)
                        navBarViewIds[2].setBackgroundResource(R.drawable.ic_bottom_nav_item_bg_colorful)
                        navBarViewIds[3].setBackgroundResource(R.drawable.ic_bottom_nav_item_bg_grey)
                        backBtn.visible()
                    }
                    3 -> {
                        navBarViewIds[0].setBackgroundResource(R.drawable.ic_bottom_nav_item_bg_grey)
                        navBarViewIds[1].setBackgroundResource(R.drawable.ic_bottom_nav_item_bg_grey)
                        navBarViewIds[2].setBackgroundResource(R.drawable.ic_bottom_nav_item_bg_grey)
                        navBarViewIds[3].setBackgroundResource(R.drawable.ic_bottom_nav_item_bg_colorful)
                        backBtn.visible()
                    }
                }
            }
        })

        viewModel.selectGenderPageLiveData.observe(viewLifecycleOwner, selectGenderPageObserver)
        viewModel.selectWeightPageLiveData.observe(viewLifecycleOwner, selectWeightPageObserver)
        viewModel.selectWakeUpPageLiveData.observe(viewLifecycleOwner, selectWakeUpPageObserver)
        viewModel.selectBedtimePageLiveData.observe(viewLifecycleOwner, selectBedtimePageObserver)

        backBtn.setOnClickListener {
            introViewPager.currentItem--
        }
        nextBtn.setOnClickListener {
            if (introViewPager.currentItem == 3) {
                (navBarTextIds[3] as TextView).apply {
                    text = viewModel.getBedTime()
                    visible()
                }
                val imgId = if (viewModel.getGender() == "male") {
                    R.drawable.male_gender_image
                } else {
                    R.drawable.female_gender_image
                }
                findNavController().navigate(
                    IntroMainScreenDirections.actionIntroMainScreenToGeneratingHydrationScreen(
                        imgId
                    )
                )
                // showToast("Last page clicked")
            } else {
                when (introViewPager.currentItem) {
                    0 -> (navBarTextIds[0] as TextView).apply {
                        text = viewModel.getGender()
                        visible()
                    }
                    1 -> (navBarTextIds[1] as TextView).apply {
                        text = "${viewModel.getWeight()} ${viewModel.getWeightUnit()}"
                        visible()
                    }
                    2 -> (navBarTextIds[2] as TextView).apply {
                        text = viewModel.getWakeUpTime()
                        visible()
                    }
                }
                introViewPager.currentItem++
            }
        }
    }

    private val selectGenderPageObserver = Observer<Unit> {
        binding.introViewPager.currentItem = 0
    }
    private val selectWeightPageObserver = Observer<Unit> {
        binding.introViewPager.currentItem = 1
    }
    private val selectWakeUpPageObserver = Observer<Unit> {
        binding.introViewPager.currentItem = 2
    }
    private val selectBedtimePageObserver = Observer<Unit> {
        binding.introViewPager.currentItem = 3
    }
}
