package uz.gita.waterreminder.ui.viewmodels.intro_viewmodels

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import uz.gita.waterreminder.domain.IntroRepository
import javax.inject.Inject

@HiltViewModel
class GenderPageViewModel  @Inject constructor(
    private val introRepository: IntroRepository
): ViewModel() {

    fun getGender(): String {
        return introRepository.gender!!
    }

    fun setGender(gender: String) {
        introRepository.gender = gender
    }

    fun getImagesIdList(): Array<Int> {
        return introRepository.getImagesIdList()
    }
}