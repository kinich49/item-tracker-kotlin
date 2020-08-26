package mx.kinich49.itemtracker.entities.database.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import mx.kinich49.itemtracker.entities.database.models.User

@Dao
interface UserDao {

    @Query("SELECT * FROM Users WHERE id = :id")
    fun getUserBy(id: Long): LiveData<User>
}