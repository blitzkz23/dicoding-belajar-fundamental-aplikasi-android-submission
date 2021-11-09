package com.app.githubuserapplication.view.favorites

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.app.githubuserapplication.model.database.FavoriteUser
import com.app.githubuserapplication.model.repository.FavoriteUserRepository

class FavoriteUserViewModel(application: Application) : ViewModel() {
	private val mFavoriteUserRepository: FavoriteUserRepository = FavoriteUserRepository(application)

	fun getAllFavorites(): LiveData<List<FavoriteUser>> = mFavoriteUserRepository.getAllFavorites()
}