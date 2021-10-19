package com.app.githubuserapplication.model.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavoriteUser(
	@PrimaryKey(autoGenerate = true)
	@ColumnInfo(name = "id")
	var id: Int = 0,

	@ColumnInfo(name = "login")
	var login: String,

	@ColumnInfo(name = "html_url")
	var htmlUrl: String,

	@ColumnInfo(name = "avatar_url")
	var avatarUrl: String
)
