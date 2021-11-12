package uz.gita.waterreminder.ui.viewmodels.base_viewmodels

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import uz.gita.waterreminder.data.entity.DrinksItemEntity
import uz.gita.waterreminder.domain.BaseRepository
import uz.gita.waterreminder.domain.IntroRepository
import javax.inject.Inject

@HiltViewModel
class SettingsPageViewModel @Inject constructor(
    private val baseRepository: BaseRepository,
    private val introRepository: IntroRepository
) : ViewModel() {

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

    fun getGender(): String {
        return introRepository.gender!!
    }

    fun setGender(gender: String) {
        introRepository.gender = gender
    }

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

    fun getWakeUpTime(): String {
        return introRepository.wakeUpTime!!
    }

    fun setWakeUpTime(wakeUpTime: String) {
        introRepository.wakeUpTime = wakeUpTime
    }

    fun getBedTime(): String {
        return introRepository.bedTime!!
    }

    fun setBedTime(bedTime: String) {
        introRepository.bedTime = bedTime
    }

    fun refreshProgressValue() {
        baseRepository.refreshProgressValue()
    }

    fun setNotificationStatus(status: Boolean) {
        baseRepository.isNotificationEnabled = status
    }

    fun getNotificationStatus(): Boolean {
        return baseRepository.isNotificationEnabled
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
