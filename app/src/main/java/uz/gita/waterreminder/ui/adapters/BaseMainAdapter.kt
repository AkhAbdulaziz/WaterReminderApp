package uz.gita.waterreminder.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import uz.gita.waterreminder.ui.screens.base_pages.HomePage
import uz.gita.waterreminder.ui.screens.base_pages.SettingsPage

class BaseMainAdapter(fm: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fm, lifecycle) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> HomePage()
            else -> SettingsPage()
        }
    }
}