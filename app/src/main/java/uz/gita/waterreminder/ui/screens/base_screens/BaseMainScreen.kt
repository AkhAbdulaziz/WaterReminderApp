package uz.gita.waterreminder.ui.screens.base_screens

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.waterreminder.R
import uz.gita.waterreminder.databinding.ScreenBaseMainBinding
import uz.gita.waterreminder.ui.adapters.BaseMainAdapter
import uz.gita.waterreminder.ui.viewmodels.base_viewmodels.BaseMainViewModel
import uz.gita.waterreminder.util.createPeriodicWorkRequest
import uz.gita.waterreminder.util.scope

@AndroidEntryPoint
class BaseMainScreen : Fragment(R.layout.screen_base_main) {
    private val binding by viewBinding(ScreenBaseMainBinding::bind)
    private val viewModel by viewModels<BaseMainViewModel>()
    private lateinit var adapter: BaseMainAdapter
    private var bedTimeHour: Float = 0f
    private var wakeUpTimeHour: Float = 0f
    private var interval: Float = 0f

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = binding.scope {
        super.onViewCreated(view, savedInstanceState)
        viewModel.setCurrentNotificationRequestId(
            createPeriodicWorkRequest(
                20,
                viewModel.getNotificationMaxId()
            )
        )
        viewModel.checkNewDay()

        adapter = BaseMainAdapter(childFragmentManager, lifecycle)
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, pos ->

            when (pos) {
                0 -> {
                    val tabView1 = layoutInflater.inflate(R.layout.tablayout_custom_view, null)
                    tabView1.findViewById<ImageView>(R.id.tabImg)
                        .setImageResource(R.drawable.ic_drop)
                    tabView1.findViewById<TextView>(R.id.tabTxt).text = "Home"
                    tab.customView = tabView1
                }
                1 -> {
                    val tabView2 = layoutInflater.inflate(R.layout.tablayout_custom_view, null)
                    tabView2.findViewById<ImageView>(R.id.tabImg)
                        .setImageResource(R.drawable.ic_settings)
                    tabView2.findViewById<TextView>(R.id.tabTxt).text = "Settings"
                    tab.customView = tabView2
                }
            }
        }.attach()
    }

    private fun getInterval(): Long {
        val btStr = viewModel.getBedTime().substring(0, viewModel.getBedTime().indexOf(':'))
        bedTimeHour = if (btStr.isEmpty()) 24f else btStr.toFloat()
        Log.d("RRR", "bedTimeHour = $bedTimeHour")

        val wtStr = viewModel.getWakeUpTime().substring(0, viewModel.getWakeUpTime().indexOf(':'))
        wakeUpTimeHour = if (wtStr.isEmpty()) 24f else wtStr.toFloat()

        Log.d("RRR", "wakeUpTimeHour = $wakeUpTimeHour")
        Log.d("RRR", "getTargetSize = ${viewModel.getTargetSize()}")
        Log.d("RRR", "getConsumedWater = ${viewModel.getConsumedWater()}")
        Log.d("RRR", "getGlassSize = ${viewModel.getGlassSize()}")

        interval =
            (bedTimeHour - wakeUpTimeHour) / ((viewModel.getTargetSize()
                .toFloat() - viewModel.getConsumedWater().toFloat()) / viewModel.getGlassSize()
                .toFloat())
        Log.d("RRR", "interval = $interval")
        Log.d("RRR", "interval in minutes = ${interval * 60}")

        return (interval * 60).toLong()
    }
}

/*

12:55   initial done
13:15
13:35
13:55
14:15
14:35
14:55


 */