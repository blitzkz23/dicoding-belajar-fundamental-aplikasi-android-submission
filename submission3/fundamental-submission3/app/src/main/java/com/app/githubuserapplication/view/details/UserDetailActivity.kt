package com.app.githubuserapplication.view.details

import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.app.githubuserapplication.R
import com.app.githubuserapplication.databinding.ActivityUserDetailBinding
import com.app.githubuserapplication.model.database.FavoriteUser
import com.app.githubuserapplication.model.response.DetailResponse
import com.app.githubuserapplication.utils.Helper
import com.app.githubuserapplication.view.ViewModelFactory
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class UserDetailActivity : AppCompatActivity() {
	private var _binding: ActivityUserDetailBinding? = null
	private val binding get() = _binding

	private lateinit var userDetailViewModel: UserDetailViewModel
	private var detailUser = DetailResponse()
	private val helper = Helper()

	private var buttonState: Boolean = false
	private var favoriteUser: FavoriteUser? = null

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		_binding = ActivityUserDetailBinding.inflate(layoutInflater)
		setContentView(binding?.root)

		userDetailViewModel = obtainViewModel(this@UserDetailActivity)

		// Live data observe
		userDetailViewModel.isLoading.observe(this, {
			helper.showLoading(it, binding!!.progressBar2)
		})
		userDetailViewModel.status.observe(this, { status ->
			status?.let {
				Toast.makeText(this, status.toString(), Toast.LENGTH_SHORT).show()
			}
		})

		supportActionBar?.setDisplayHomeAsUpEnabled(true)

		setTabLayoutView()


		// Check if user is favorited
		userDetailViewModel.listDetail.observe(this, { detailList ->
			detailUser = detailList
			setDataToView(detailUser)
			favoriteUser = FavoriteUser(detailUser.id, detailUser.login)
			userDetailViewModel.getAllFavorites().observe(this, { favoriteList ->
				if (favoriteList != null) {
					for (data in favoriteList) {
						if (detailUser.id == data.id) {
							buttonState = true
							binding?.fabFavorite?.setImageResource(R.drawable.ic_favorite)
						}
					}
				}
			})

			// Favorite event
			binding?.fabFavorite?.setOnClickListener {
				if (!buttonState) {
					buttonState = true
					binding?.fabFavorite?.setImageResource(R.drawable.ic_favorite)
					insertToDatabase(detailUser)
				} else {
					buttonState = false
					binding?.fabFavorite?.setImageResource(R.drawable.ic_unfavorite)
					userDetailViewModel.delete(detailUser.id)
					helper.showToast(this, "Favorite user has been deleted.")
				}
			}
		})
	}

	/**
	 * Function to return View Model Factory
	 */
	private fun obtainViewModel(activity: AppCompatActivity): UserDetailViewModel {
		val factory = ViewModelFactory.getInstance(activity.application)
		return ViewModelProvider(activity, factory).get(UserDetailViewModel::class.java)
	}

	private fun insertToDatabase(detailList: DetailResponse) {
		favoriteUser.let { favoriteUser ->
			favoriteUser?.id = detailList.id
			favoriteUser?.login = detailList.login
			favoriteUser?.htmlUrl = detailList.htmlUrl
			favoriteUser?.avatarUrl = detailList.avatarUrl
			userDetailViewModel.insert(favoriteUser as FavoriteUser)
			helper.showToast(this, "User has been favorited.")
		}
	}

	/**
	 * Function to set the view of the tab layout.
	 */
	private fun setTabLayoutView() {
		val userIntent = intent.extras
		if (userIntent != null) {
			val userLogin = userIntent.getString(EXTRA_USER)
			if (userLogin != null) {
				userDetailViewModel.getGithubUser(userLogin)
				val login = Bundle()
				login.putString(EXTRA_FRAGMENT, userLogin)
				val sectionPagerAdapter = SectionsPagerAdapter(this, login)
				val viewPager: ViewPager2 = binding!!.viewPager

				viewPager.adapter = sectionPagerAdapter
				val tabs: TabLayout = binding!!.tabs
				val tabTitle = resources.getStringArray(R.array.tab_title)
				TabLayoutMediator(tabs, viewPager) { tab, position ->
					tab.text = tabTitle[position]
				}.attach()
			}
		}
	}

	/**
	 * Function to set the data from API into view.
	 */
	private fun setDataToView(detailList: DetailResponse) {
		binding?.apply {
			detailsIvAvatar.loadImage(detailList.avatarUrl)
			detailsTvName.text = detailList.name ?: resources.getString(R.string.noname)
			detailsTvUsername.text = detailList.login
			detailsTvBio.text =
				if (detailList.bio == null) resources.getString(R.string.nobio) else detailList.bio.toString()
					.trim()
			detailsTvFollower.text = resources.getString(R.string.follower, detailList.followers)
			detailsTvFollowing.text = resources.getString(R.string.following, detailList.following)
			detailsTvGist.text = resources.getString(R.string.gist, detailList.publicGists)
			detailsTvRepository.text =
				resources.getString(R.string.repository, detailList.publicRepos)
			detailsTvCompany.text = detailList.company ?: resources.getString(R.string.nocompany)
			detailsTvLocation.text = detailList.location ?: resources.getString(R.string.nolocation)
			detailsTvBlog.text =
				if (detailList.blog == "") resources.getString(R.string.noblog) else detailList.blog
		}
		supportActionBar?.title = resources.getString(R.string.username_detail, detailList.login)
	}

	override fun onSupportNavigateUp(): Boolean {
		finish()
		return true
	}

	/**
	 * Extension function to use the Glide library
	 */
	private fun ImageView.loadImage(url: String?) {
		Glide.with(this.context)
			.load(url)
			.circleCrop()
			.into(this)
	}

	override fun onDestroy() {
		super.onDestroy()
		_binding = null
	}

	companion object {
		const val EXTRA_USER = "extra_user"
		const val EXTRA_FRAGMENT = "extra_fragment"
	}
}