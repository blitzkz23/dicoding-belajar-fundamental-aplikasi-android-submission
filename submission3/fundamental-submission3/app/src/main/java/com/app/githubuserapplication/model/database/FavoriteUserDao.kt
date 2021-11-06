package com.app.githubuserapplication.model.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FavoriteUserDao {
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	fun insertFavorite(user: FavoriteUser)

	@Query("DELETE FROM FavoriteUser WHERE FavoriteUser.id = :id")
	fun removeFavorite(id: Int)

	@Query("SELECT * FROM FavoriteUser ORDER BY login ASC")
	fun getAllUser(): LiveData<List<FavoriteUser>>

	@Query("SELECT * FROM FavoriteUser WHERE FavoriteUser.id = :id")
	fun getUserById(id: Int): LiveData<FavoriteUser>

}