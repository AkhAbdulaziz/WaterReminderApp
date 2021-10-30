package uz.gita.waterreminder.ui.viewmodels.intro_viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import uz.gita.waterreminder.domain.IntroRepository
import javax.inject.Inject

@HiltViewModel
class WeightPageViewModel @Inject constructor(
    private val introRepository: IntroRepository
) : ViewModel() {

    private val _changeImageLiveData = MutableLiveData<Int>()
    val changeImageLiveData: LiveData<Int> get() = _changeImageLiveData

    fun getWeight(): Int {
        return introRepository.weight!!
    }

    fun setWeight(weight: Int) {
        introRepository.weight = weight
    }

    fun getWeightUnit(): String {
        return introRepository.weightUnit!!
    }

    fun setWeightUnit(weightUnit: String) {
        introRepository.weightUnit = weightUnit
    }

    fun getWeightPageImage() {
        _changeImageLiveData.value = introRepository.getImagesIdList()[1]
    }
}