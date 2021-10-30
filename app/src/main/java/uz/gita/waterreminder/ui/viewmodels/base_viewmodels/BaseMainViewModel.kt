package uz.gita.waterreminder.ui.viewmodels.base_viewmodels

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import uz.gita.waterreminder.domain.BaseRepository
import uz.gita.waterreminder.domain.IntroRepository
import javax.inject.Inject

@HiltViewModel
class BaseMainViewModel @Inject constructor(
    private val baseRepository: BaseRepository,
    private val introRepository: IntroRepository
) : ViewModel() {

    fun getGender(): String {
        return introRepository.gender!!
    }

    fun getWeight(): Int {
        return introRepository.weight!!
    }

    fun getWeightUnit(): String {
        return introRepository.weightUnit!!
    }

    fun getWakeUpTime(): String {
        return introRepository.wakeUpTime!!
    }

    fun getBedTime(): String {
        return introRepository.bedTime!!
    }

    fun getGlassSize(): Int {
        return baseRepository.glassSize
    }

    fun getTargetSize(): Int {
        return baseRepository.targetSize
    }

    fun getConsumedWater(): Int {
        return baseRepository.consumedWater
    }

    fun getNotificationMaxId(): Int {
        return baseRepository.getNotificationMaxId()
    }

    fun checkNewDay(): Boolean {
        return baseRepository.checkNewDay()
    }

    fun setCurrentNotificationRequestId(status: String) {
        baseRepository.currentNotificationRequestId = status
    }

    fun getCurrentNotificationRequestId(): String {
        return baseRepository.currentNotificationRequestId!!
    }
}