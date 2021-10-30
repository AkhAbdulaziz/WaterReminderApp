package uz.gita.waterreminder.ui.viewmodels.intro_viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import uz.gita.waterreminder.domain.IntroRepository
import javax.inject.Inject

@HiltViewModel
class WakeUpPageViewModel @Inject constructor(
    private val introRepository: IntroRepository
)  : ViewModel() {

    private val _changeImageLiveData = MutableLiveData<Int>()
    val changeImageLiveData: LiveData<Int> get() = _changeImageLiveData

    fun getWakeUpTime(): String {
        return introRepository.gender!!
    }

    fun setWakeUpTime(wakeUpTime: String) {
        introRepository.wakeUpTime = wakeUpTime
    }

    fun getWakeUpPageImage() {
        _changeImageLiveData.value = introRepository.getImagesIdList()[2]
    }
}