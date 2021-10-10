package com.app.githubapplication.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
	var name: String,
	var username: String,
	var location: String,
	var repository: String,
	var company: String,
	var follower: String,
	var following: String,
	var avatar: Int
) : Parcelable
