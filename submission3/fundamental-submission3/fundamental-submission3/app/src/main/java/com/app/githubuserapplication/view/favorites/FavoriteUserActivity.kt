package com.app.githubuserapplication.view.favorites

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.githubuserapplication.R
import com.app.githubuserapplication.databinding.ActivityFavoriteUserBinding
import com.app.githubuserapplication.view.ViewModelFactory

class FavoriteUserActivity : AppCompatActivity() {

	private var _binding: ActivityFavoriteUserBinding? = null
	private val binding get() = _binding
	private lateinit var adapter: FavoriteUserAdapter

	private lateinit var favoriteUserViewModel: FavoriteUserViewModel

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		_binding = ActivityFavoriteUserBinding.inflate(layoutInflater)
		setContentView(binding?.root)
		supportActionBar?.title = getString(R.string.favoriteduser)
		supportActionBar?.setDisplayHomeAsUpEnabled(true)


		favoriteUserViewModel = obtainViewModel(this@FavoriteUserActivity)
		favoriteUserViewModel.getAllFavorites().observe(this, { favoriteList ->
			if (favoriteList != null) {
				adapter.setFavorites(favoriteList)
			}
		})
		adapter = FavoriteUserAdapter()
		binding?.rvFavorites?.layoutManager = LinearLayoutManager(this)
		binding?.rvFavorites?.setHasFixedSize(false)
		binding?.rvFavorites?.adapter = adapter
	}

	/**
	 * Function to return View Model Factory
	 */
	private fun obtainViewModel(activity: AppCompatActivity): FavoriteUserViewModel {
		val factory = ViewModelFactory.getInstance(activity.application)
		return ViewModelProvider(activity, factory).get(FavoriteUserViewModel::class.java)
	}

	override fun onDestroy() {
		super.onDestroy()
		_binding = null
	}
}