package com.app.githubuserapplication.utils

import androidx.recyclerview.widget.DiffUtil
import com.app.githubuserapplication.model.database.FavoriteUser

class FavoriteDiffCallback(private val mOldFavList: List<FavoriteUser>, private val mNewFavList: List<FavoriteUser>):
	DiffUtil.Callback() {
	override fun getOldListSize(): Int {
		return mOldFavList.size
	}

	override fun getNewListSize(): Int {
		return mNewFavList.size
	}

	override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
		return mOldFavList[oldItemPosition].id == mNewFavList[newItemPosition].id
	}

	override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
		val oldFavList = mOldFavList[oldItemPosition]
		val newFavList = mNewFavList[newItemPosition]
		return oldFavList.login == newFavList.login && oldFavList.htmlUrl == newFavList.htmlUrl && oldFavList.avatarUrl == newFavList.avatarUrl
	}


}