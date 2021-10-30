package uz.gita.waterreminder.ui.viewmodels.intro_viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import uz.gita.waterreminder.domain.IntroRepository
import javax.inject.Inject

@HiltViewModel
class IntroMainViewModel @Inject constructor(
    private val introRepository: IntroRepository
) : ViewModel() {

    private val _selectGenderPageLiveData = MutableLiveData<Unit>()
    val selectGenderPageLiveData: LiveData<Unit> get() = _selectGenderPageLiveData

    private val _selectWeightPageLiveData = MutableLiveData<Unit>()
    val selectWeightPageLiveData: LiveData<Unit> get() = _selectWeightPageLiveData

    private val _selectWakeUpPageLiveData = MutableLiveData<Unit>()
    val selectWakeUpPageLiveData: LiveData<Unit> get() = _selectWakeUpPageLiveData

    private val _selectBedtimePageLiveData = MutableLiveData<Unit>()
    val selectBedtimePageLiveData: LiveData<Unit> get() = _selectBedtimePageLiveData

    private var position = 0

    fun changePagePos(pos: Int) {
        if (pos == position) return

        when (pos) {
            0 -> _selectGenderPageLiveData
            1 -> _selectWeightPageLiveData
            2 -> _selectWakeUpPageLiveData
            else -> _selectBedtimePageLiveData
        }.value = Unit
    }

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

    fun getImagesIdList(): Array<Int> {
        return introRepository.getImagesIdList()
    }
}