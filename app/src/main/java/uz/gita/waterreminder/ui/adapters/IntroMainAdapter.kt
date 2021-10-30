package uz.gita.waterreminder.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import uz.gita.waterreminder.ui.screens.intro_pages.BedtimePage
import uz.gita.waterreminder.ui.screens.intro_pages.GenderPage
import uz.gita.waterreminder.ui.screens.intro_pages.WakeUpPage
import uz.gita.waterreminder.ui.screens.intro_pages.WeightPage

class IntroMainAdapter(fm: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fm, lifecycle) {

    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> GenderPage()
            1 -> WeightPage()
            2 -> WakeUpPage()
            else -> BedtimePage()
        }
    }
}