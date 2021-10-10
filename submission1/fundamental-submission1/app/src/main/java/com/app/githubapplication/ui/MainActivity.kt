package com.app.githubapplication.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.githubapplication.R
import com.app.githubapplication.adapter.ListUserAdapter
import com.app.githubapplication.databinding.ActivityMainBinding
import com.app.githubapplication.model.User

class MainActivity : AppCompatActivity() {
	private lateinit var binding: ActivityMainBinding
	private var list = ArrayList<User>()
	private var title: String = "Github's User"

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setTheme(R.style.Theme_GithubApplication)
		binding = ActivityMainBinding.inflate(layoutInflater)
		setContentView(binding.root)

		binding.rvUser.setHasFixedSize(true)
		list.addAll(listUsers)
		setActionBarTitle(title)
		showRecyclerList()
	}

	private fun setActionBarTitle(title: String) {
		supportActionBar?.title = title
	}

	private fun showRecyclerList() {
		binding.rvUser.layoutManager = LinearLayoutManager(this)
		val listUserAdapter = ListUserAdapter(list)
		binding.rvUser.adapter = listUserAdapter

		listUserAdapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
			override fun onItemClicked(data: User) {
				showSelectedUser(data)
			}
		})
	}

	private fun showSelectedUser(user: User) {
		val moveWithParcelableIntent = Intent(this@MainActivity, UserDetailsActivity::class.java)
		moveWithParcelableIntent.putExtra(UserDetailsActivity.EXTRA_USER, user)
		startActivity(moveWithParcelableIntent)
	}

	private val listUsers: ArrayList<User>
		get() {
			val dataName = resources.getStringArray(R.array.name)
			val dataUsername = resources.getStringArray(R.array.username)
			val dataLocation = resources.getStringArray(R.array.location)
			val dataRepository = resources.getStringArray(R.array.repository)
			val dataCompany = resources.getStringArray(R.array.company)
			val dataFollowers = resources.getStringArray(R.array.followers)
			val dataFollowing = resources.getStringArray(R.array.following)
			val dataAvatar = resources.obtainTypedArray(R.array.avatar)
			val listUser = ArrayList<User>()
			for (i in dataName.indices) {
				val user = User(dataName[i], dataUsername[i], dataLocation[i], dataRepository[i], dataCompany[i], dataFollowers[i], dataFollowing[i], dataAvatar.getResourceId(i, -1))
				listUser.add(user)
			}
			dataAvatar.recycle()
			return listUser
		}
}