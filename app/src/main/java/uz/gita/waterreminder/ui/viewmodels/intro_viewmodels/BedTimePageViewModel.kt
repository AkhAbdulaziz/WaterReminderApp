package uz.gita.waterreminder.ui.viewmodels.intro_viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import uz.gita.waterreminder.domain.IntroRepository
import javax.inject.Inject

@HiltViewModel
class BedTimePageViewModel @Inject constructor(
    private val introRepository: IntroRepository
) : ViewModel() {

    private val _changeImageLiveData = MutableLiveData<Int>()
    val changeImageLiveData: LiveData<Int> get() = _changeImageLiveData

    fun getBedTime(): String {
        return introRepository.gender!!
    }

    fun setBedTime(bedTime: String) {
        introRepository.bedTime = bedTime
    }

    fun getBedTimePageImage() {
        _changeImageLiveData.value = introRepository.getImagesIdList()[3]
    }
}