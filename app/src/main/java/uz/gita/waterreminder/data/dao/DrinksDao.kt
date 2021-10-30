package uz.gita.waterreminder.data.dao

import androidx.room.*
import uz.gita.waterreminder.data.entity.DrinksItemEntity

@Dao
interface DrinksDao {
    @Query("SELECT * FROM DrinksItemEntity ORDER BY id DESC")
    fun getAllDrinksList(): List<DrinksItemEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(data: DrinksItemEntity)

    @Update
    fun update(data: DrinksItemEntity)

    @Delete
    fun delete(data: DrinksItemEntity)
}