package com.app.githubuserapplication.model.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FavoriteUserDao {
	@Insert(onConflict = OnConflictStrategy.IGNORE)
	fun insert(user: FavoriteUser)

	@Update
	fun update(user: FavoriteUser)

	@Delete
	fun delete(user: FavoriteUser)

	@Query("SELECT * from FavoriteUser ORDER BY id ASC")
	fun getAllUser(): LiveData<List<FavoriteUser>>
}