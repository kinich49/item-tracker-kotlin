package mx.kinich49.itemtracker.models.database.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import mx.kinich49.itemtracker.models.database.User

@Dao
interface UserDao {

    @Query("SELECT * FROM Users WHERE id = :id")
    fun getUserBy(id: Long): LiveData<User>
}