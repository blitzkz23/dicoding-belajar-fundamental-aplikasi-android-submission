package com.app.githubuserapplication.ui.activities

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.githubuserapplication.R
import com.app.githubuserapplication.adapter.SearchAdapter
import com.app.githubuserapplication.databinding.ActivityMainBinding
import com.app.githubuserapplication.model.GithubUser
import com.app.githubuserapplication.ui.viewmodels.MainViewModel
import com.app.githubuserapplication.utils.Helper

class MainActivity : AppCompatActivity() {
	private var _binding: ActivityMainBinding? = null
	private val binding get() = _binding
	private val mainViewModel by viewModels<MainViewModel>()
	private var listGithubUser = ArrayList<GithubUser>()
	private val helper = Helper()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setTheme(R.style.Theme_GithubUserApplication)
		_binding = ActivityMainBinding.inflate(layoutInflater)
		setContentView(binding?.root)

		mainViewModel.listGithubUser.observe(this, { listGithubUser ->
			setUserData(listGithubUser)
		})

		mainViewModel.isLoading.observe(this, {
			helper.showLoading(it, binding!!.progressBar)
		})

		mainViewModel.totalCount.observe(this, {
			showText(it)
		})

		val layoutManager = LinearLayoutManager(this@MainActivity)
		binding!!.rvUser.layoutManager = layoutManager
	}

	/**
	 * Function to initialize search view on action bar
	 */
	override fun onCreateOptionsMenu(menu: Menu?): Boolean {
		val inflater = menuInflater
		inflater.inflate(R.menu.main_menu, menu)

		val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
		val searchView = menu!!.findItem(R.id.search).actionView as SearchView

		searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
		searchView.queryHint = resources.getString(R.string.search_hint)
		searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
			override fun onQueryTextSubmit(query: String?): Boolean {
				query?.let {
					binding!!.rvUser.visibility = View.VISIBLE
					mainViewModel.searchGithubUser(it)
					setUserData(listGithubUser)
				}
				hideKeyboard()
				return true
			}

			override fun onQueryTextChange(newText: String?): Boolean {
				return false
			}
		})
		return true
	}

	/**
	 * Function to set the data from API into UI
	 */
	private fun setUserData(listGithubUser: List<GithubUser>) {
		val listUser = ArrayList<GithubUser>()
		for (user in listGithubUser) {
			listUser.clear()
			listUser.addAll(listGithubUser)
		}
		val adapter = SearchAdapter(listUser)
		binding!!.rvUser.adapter = adapter

		adapter.setOnItemClickCallback(object : SearchAdapter.OnItemClickCallback {
			override fun onItemClicked(data: GithubUser) {
				showSelectedUser(data)
			}
		})
	}

	/**
	 * Function to open detail activity after clicking one of the user list.
	 */
	private fun showSelectedUser(data: GithubUser) {
		val moveWithParcelableIntent = Intent(this@MainActivity, UserDetailActivity::class.java)
		moveWithParcelableIntent.putExtra(UserDetailActivity.EXTRA_USER, data)
		startActivity(moveWithParcelableIntent)
	}

	/**
	 * Function to show or hide text view based on the result of total count.
	 */
	private fun showText(totalCount: Int) {
		binding?.apply {
			if (totalCount == 0) {
				tvNotice.visibility = View.VISIBLE
				tvNotice.text = resources.getString(R.string.user_not_found)
			} else {
				tvNotice.visibility = View.INVISIBLE
			}
		}
	}

	/**
	 * Function to hide keyboard after submitting input.
	 */
	private fun hideKeyboard() {
		val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
		imm.hideSoftInputFromWindow(binding!!.rvUser.windowToken, 0)
	}

	override fun onDestroy() {
		super.onDestroy()
		_binding = null
	}
}