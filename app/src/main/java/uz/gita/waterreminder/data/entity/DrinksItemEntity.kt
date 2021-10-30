package uz.gita.waterreminder.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class DrinksItemEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var imageId: Int,
    var time: String,
    var glassSize: Int,
    var haveNextTimeText: Boolean = false
) : Serializable
