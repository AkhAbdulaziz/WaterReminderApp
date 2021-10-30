package uz.gita.waterreminder.ui.viewmodels.base_viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import uz.gita.waterreminder.data.entity.DrinksItemEntity
import uz.gita.waterreminder.domain.BaseRepository
import javax.inject.Inject

@HiltViewModel
class HomePageViewModel @Inject constructor(
    private val baseRepository: BaseRepository
) : ViewModel() {

    private var _drinksListLiveData = MutableLiveData<List<DrinksItemEntity>>()
    val drinksListLiveData: LiveData<List<DrinksItemEntity>> get() = _drinksListLiveData

    fun getGlassSize(): Int {
        return baseRepository.glassSize
    }

    fun setGlassSize(size: Int) {
        baseRepository.glassSize = size
    }

    fun getTargetSize(): Int {
        return baseRepository.targetSize
    }

    fun setTargetSize(size: Int) {
        baseRepository.targetSize = size
    }

    fun getProgressAddingValue(): Float {
        return baseRepository.progressAddingValue()
    }

    fun addProgressValue() {
        baseRepository.addProgressValue()
    }

    fun reduceProgressValue(glassSize: Int = baseRepository.glassSize) {
        baseRepository.reduceProgressValue(glassSize)
    }

    fun getProgressValue(): Float {
        return baseRepository.progressValue
    }

    fun getConsumedWater(): Int {
        return baseRepository.consumedWater
    }


    fun getAllDrinksList() {
        _drinksListLiveData.value = baseRepository.getAllDrinksList()
    }

    fun insertDrunkItem(data: DrinksItemEntity) {
        baseRepository.insertDrunkItem(data)
    }

    fun updateDrunkItem(data: DrinksItemEntity) {
        baseRepository.updateDrunkItem(data)
    }

    fun deleteDrunkItem(data: DrinksItemEntity) {
        baseRepository.deleteDrunkItem(data)
    }

    fun setCanIDelete(canIDelete: Boolean) {
        baseRepository.canIDelete = canIDelete
    }

    fun refreshProgressValue() {
        baseRepository.refreshProgressValue()
    }
}