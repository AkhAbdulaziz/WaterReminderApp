package uz.gita.waterreminder.domain

import android.content.Context
import uz.gita.waterreminder.R
import uz.gita.waterreminder.app.App
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class IntroRepository @Inject constructor(){
    /*   companion object {
           private lateinit var introRepository: IntroRepository
           fun getIntroRepository(): IntroRepository {
               if (!Companion::introRepository.isInitialized) {
                   introRepository = IntroRepository()
               }
               return introRepository
           }
       }*/

    private val pref = App.instance.getSharedPreferences("AppPref", Context.MODE_PRIVATE)

    var isFirstTimeUser
        set(value) = pref.edit().putBoolean("IS_FIRST_TIME", value).apply()
        get() = pref.getBoolean("IS_FIRST_TIME", true)

    var gender
        set(value) = pref.edit().putString("GENDER", value).apply()
        get() = pref.getString("GENDER", "male")

    var weight
        set(value) = pref.edit().putInt("WEIGHT", value).apply()
        get() = pref.getInt("WEIGHT", 70)

    var weightUnit
        set(value) = pref.edit().putString("WEIGHT_UNIT", value).apply()
        get() = pref.getString("WEIGHT_UNIT", "kg")

    var wakeUpTime
        set(value) = pref.edit().putString("WAKE_UP_TIME", value).apply()
        get() = pref.getString("WAKE_UP_TIME", "06:00")

    var bedTime
        set(value) = pref.edit().putString("BED_TIME", value).apply()
        get() = pref.getString("BED_TIME", "22:00")

    fun getWakeUpTimeHourInt(): Int {
        return wakeUpTime!!.substring(0, wakeUpTime!!.indexOf(':')).trim().toInt()
    }

    fun getWakeUpTimeMinuteInt(): Int {
        return wakeUpTime!!.substring(wakeUpTime!!.indexOf(':') + 1).trim().toInt()
    }

    fun getBedTimeHourInt(): Int {
        return bedTime!!.substring(0, bedTime!!.indexOf(':')).trim().toInt()
    }

    fun getBedTimeMinuteInt(): Int {
        return bedTime!!.substring(bedTime!!.indexOf(':') + 1).trim().toInt()
    }

    private val maleImages = arrayOf(
        R.drawable.male_gender_image,
        R.drawable.male_weight_image,
        R.drawable.male_wakeup_image,
        R.drawable.male_bedtime_image
    )

    private val femaleImages = arrayOf(
        R.drawable.female_gender_image,
        R.drawable.female_weight_image,
        R.drawable.female_wakeup_image,
        R.drawable.female_bedtime_image
    )

    fun getImagesIdList(): Array<Int> {
        return if (gender.equals("male")) {
            maleImages
        } else {
            femaleImages
        }
    }
}