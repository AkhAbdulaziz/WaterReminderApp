package uz.gita.waterreminder.domain

import android.content.Context
import uz.gita.waterreminder.app.App
import uz.gita.waterreminder.data.database.AppDatabase
import uz.gita.waterreminder.data.entity.DrinksItemEntity
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BaseRepository @Inject constructor() {
    /* companion object {
         private lateinit var repository: BaseRepository
         fun getBaseRepository(): BaseRepository {
             if (!Companion::repository.isInitialized) {
                 repository = BaseRepository()
             }
             return repository
         }
     }*/

    private val drinksDao = AppDatabase.getAppDatabase().getDrinksDao()
    private val pref = App.instance.getSharedPreferences("BasePref", Context.MODE_PRIVATE)

    private var lastDay
        set(value) = pref.edit().putInt("LAST_DAY", value).apply()
        get() = pref.getInt("LAST_DAY", Calendar.getInstance().get(Calendar.DAY_OF_MONTH))

    fun checkNewDay(): Boolean {
        val calendar = Calendar.getInstance()
        val currentDay = calendar.get(Calendar.DAY_OF_MONTH)

        if (lastDay != currentDay) {
            lastDay = currentDay
            val list: List<DrinksItemEntity> = drinksDao.getAllDrinksList()
            for (item in list) {
                drinksDao.delete(item)
            }
            consumedWater = 0
            progressValue = 0f
            return true
        }
        return false
    }

    private var notificationId
        set(value) = pref.edit().putInt("NOTIFICATION_ID", value).apply()
        get() = pref.getInt("NOTIFICATION_ID", 0)

    fun getNotificationMaxId(): Int {
        notificationId += 1
        return notificationId
    }

    var currentNotificationRequestId
        set(value) = pref.edit().putString("CURRENT_NOTIFICATION_REQUEST", value).apply()
        get() = pref.getString("CURRENT_NOTIFICATION_REQUEST", "0")

    var canIDelete = true

    var glassSize
        set(value) = pref.edit().putInt("GLASS_SIZE", value).apply()
        get() = pref.getInt("GLASS_SIZE", 100)

    var targetSize
        set(value) = pref.edit().putInt("TARGET_SIZE", value).apply()
        get() = pref.getInt("TARGET_SIZE", 2000)

    var consumedWater
        set(value) = pref.edit().putInt("CONSUMED_WATER", value).apply()
        get() = pref.getInt("CONSUMED_WATER", 0)

    var progressValue
        set(value) = pref.edit().putFloat("PROGRESS_VALUE", value).apply()
        get() = pref.getFloat("PROGRESS_VALUE", 0f)

    var isNotificationEnabled
        set(value) = pref.edit().putBoolean("NOTIFICATION_STATUS", value).apply()
        get() = pref.getBoolean("NOTIFICATION_STATUS", false)

    fun progressAddingValue(itsGlassSize: Int = glassSize): Float {
        return 1f / (targetSize / itsGlassSize)
    }

    fun addProgressValue() {
        consumedWater += glassSize
        progressValue += progressAddingValue()
    }

    fun reduceProgressValue(itsGlassSize: Int = glassSize) {
        consumedWater -= itsGlassSize
        progressValue -= progressAddingValue(itsGlassSize)
    }

    fun refreshProgressValue() {
        progressValue = ((consumedWater.toFloat() * 100f / targetSize.toFloat()) / 100f)
    }

    fun getAllDrinksList(): List<DrinksItemEntity> {
        return drinksDao.getAllDrinksList()
    }

    fun insertDrunkItem(data: DrinksItemEntity) {
        drinksDao.insert(data)
    }

    fun updateDrunkItem(data: DrinksItemEntity) {
        drinksDao.update(data)
    }

    fun deleteDrunkItem(data: DrinksItemEntity) {
        drinksDao.delete(data)
    }
}
