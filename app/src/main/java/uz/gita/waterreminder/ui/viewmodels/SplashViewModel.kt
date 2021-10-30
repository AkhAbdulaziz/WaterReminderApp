package uz.gita.waterreminder.ui.viewmodels

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import uz.gita.waterreminder.domain.BaseRepository
import uz.gita.waterreminder.domain.IntroRepository
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val baseRepository: BaseRepository,
    private val introRepository: IntroRepository
) : ViewModel() {

    fun checkNewDay(): Boolean {
        return baseRepository.checkNewDay()
    }

    fun isFirstTimeUser(): Boolean {
        return introRepository.isFirstTimeUser
    }

    fun noLongerFirstTimeUser() {
        introRepository.isFirstTimeUser = false
    }
}