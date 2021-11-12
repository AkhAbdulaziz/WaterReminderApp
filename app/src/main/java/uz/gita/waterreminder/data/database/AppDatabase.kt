package uz.gita.waterreminder.data.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import uz.gita.waterreminder.app.App
import uz.gita.waterreminder.data.dao.DrinksDao
import uz.gita.waterreminder.data.entity.DrinksItemEntity
import uz.gita.waterreminder.data.entity.UserEntity

@Database(entities = [DrinksItemEntity::class, UserEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getDrinksDao(): DrinksDao

    companion object {
        private lateinit var instance: AppDatabase

        fun getAppDatabase(): AppDatabase {
            if (!::instance.isInitialized) {
                instance =
                    Room.databaseBuilder(App.instance, AppDatabase::class.java, "WaterReminder1")
                        .allowMainThreadQueries()
                        .build()
            }
            return instance
        }
    }
}