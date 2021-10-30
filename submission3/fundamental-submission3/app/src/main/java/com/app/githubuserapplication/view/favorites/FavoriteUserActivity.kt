package com.app.githubuserapplication.view.favorites

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.githubuserapplication.R
import com.app.githubuserapplication.databinding.ActivityFavoriteUserBinding
import com.app.githubuserapplication.databinding.ActivityMainBinding

class FavoriteUserActivity : AppCompatActivity() {

	private var _binding: ActivityFavoriteUserBinding? = null
	private val binding get() = _binding

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_favorite_user)
	}
}