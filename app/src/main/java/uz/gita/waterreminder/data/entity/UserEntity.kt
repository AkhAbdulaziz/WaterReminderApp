package uz.gita.waterreminder.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var gender: String,
    var weight: Int,
    var wakeUpTime: String,
    var bedTime: String
) : Serializable