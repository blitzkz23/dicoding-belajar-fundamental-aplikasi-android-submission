package com.app.githubuserapplication.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.app.githubuserapplication.R
import com.app.githubuserapplication.adapter.SectionsPagerAdapter
import com.app.githubuserapplication.databinding.ActivityUserDetailBinding
import com.app.githubuserapplication.model.DetailResponse
import com.app.githubuserapplication.model.GithubUser
import com.app.githubuserapplication.ui.viewmodels.UserDetailViewModel
import com.app.githubuserapplication.utils.Helper
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class UserDetailActivity : AppCompatActivity() {
	private var _binding: ActivityUserDetailBinding? = null
	private val binding get() = _binding!!
	private val userDetailViewModel by viewModels<UserDetailViewModel>()
	private val helper = Helper()

	companion object {
		const val EXTRA_USER = "extra_user"
		const val EXTRA_FRAGMENT = "extra_fragment"
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		_binding = ActivityUserDetailBinding.inflate(layoutInflater)
		setContentView(binding.root)

		userDetailViewModel.listDetail.observe(this, { detailList ->
			setDataToView(detailList)
		})

		userDetailViewModel.isLoading.observe(this, {
			helper.showLoading(it, binding.progressBar2)
		})

		setTabLayoutView()
	}

	/**
	 * Function to set the view of the tab layout.
	 */
	private fun setTabLayoutView() {
		val userIntent = intent.getParcelableExtra<GithubUser>(EXTRA_USER) as GithubUser
		userDetailViewModel.getGithubUser(userIntent.login)

		val login = Bundle()
		login.putString(EXTRA_FRAGMENT, userIntent.login)

		val sectionPagerAdapter = SectionsPagerAdapter(this, login)
		val viewPager: ViewPager2 = binding.viewPager

		viewPager.adapter = sectionPagerAdapter
		val tabs: TabLayout = binding.tabs
		val tabTitle = resources.getStringArray(R.array.tab_title)
		TabLayoutMediator(tabs, viewPager) { tab, position ->
			tab.text = tabTitle[position]
		}.attach()
	}

	/**
	 * Function to set the data from API into view.
	 */
	private fun setDataToView(detailList: DetailResponse) {
		binding.apply {
			Glide.with(this@UserDetailActivity)
				.load(detailList.avatarUrl)
				.circleCrop()
				.into(detailsIvAvatar)
			detailsTvName.text = detailList.name ?: "No Name."
			detailsTvUsername.text = detailList.login
			detailsTvBio.text = if (detailList.bio == null) "This person haven\'t set their bio yet." else detailList.bio.toString()
			detailsTvFollower.text = resources.getString(R.string.follower, detailList.followers)
			detailsTvFollowing.text = resources.getString(R.string.following, detailList.following)
			detailsTvGist.text = resources.getString(R.string.gist, detailList.publicGists)
			detailsTvRepository.text = resources.getString(R.string.repository, detailList.publicRepos)
			detailsTvCompany.text = detailList.company ?: "No company."
			detailsTvLocation.text = detailList.location ?: "No location."
			detailsTvBlog.text = if (detailList.blog == "") "No website/blog." else detailList.blog
		}
	}

	override fun onDestroy() {
		super.onDestroy()
		_binding = null
	}
}