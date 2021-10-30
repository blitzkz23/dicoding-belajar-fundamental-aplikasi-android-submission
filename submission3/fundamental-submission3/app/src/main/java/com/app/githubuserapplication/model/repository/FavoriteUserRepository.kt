package com.app.githubuserapplication.model.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.app.githubuserapplication.model.database.FavoriteUser
import com.app.githubuserapplication.model.database.FavoriteUserDao
import com.app.githubuserapplication.model.database.FavoriteUserRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteUserRepository(application: Application) {
	private val mFavoriteUserDao: FavoriteUserDao
	private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

	init {
		val db = FavoriteUserRoomDatabase.getDatabase(application)
		mFavoriteUserDao = db.favoriteUserDao()
	}

	fun getAllFavorites(): LiveData<List<FavoriteUser>> = mFavoriteUserDao.getAllUser()

	fun insert(user: FavoriteUser) {
		executorService.execute { mFavoriteUserDao.insert(user) }
	}

	fun update(user: FavoriteUser) {
		executorService.execute { mFavoriteUserDao.update(user) }
	}

	fun delete(user: FavoriteUser) {
		executorService.execute { mFavoriteUserDao.delete(user) }
	}
}