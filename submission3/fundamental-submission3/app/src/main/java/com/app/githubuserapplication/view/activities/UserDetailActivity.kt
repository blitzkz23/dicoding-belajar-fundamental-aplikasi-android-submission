package com.app.githubuserapplication.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.app.githubuserapplication.R
import com.app.githubuserapplication.view.adapter.SectionsPagerAdapter
import com.app.githubuserapplication.databinding.ActivityUserDetailBinding
import com.app.githubuserapplication.model.response.DetailResponse
import com.app.githubuserapplication.model.GithubUser
import com.app.githubuserapplication.view.viewmodels.UserDetailViewModel
import com.app.githubuserapplication.utils.Helper
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class UserDetailActivity : AppCompatActivity() {
	private var _binding: ActivityUserDetailBinding? = null
	private val binding get() = _binding

	private val userDetailViewModel by viewModels<UserDetailViewModel>()
	private val helper = Helper()

	private var buttonState: Boolean = false

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		_binding = ActivityUserDetailBinding.inflate(layoutInflater)
		setContentView(binding?.root)

		// Live data observe
		userDetailViewModel.listDetail.observe(this, { detailList ->
			setDataToView(detailList)
		})
		userDetailViewModel.isLoading.observe(this, {
			helper.showLoading(it, binding!!.progressBar2)
		})
		userDetailViewModel.status.observe(this, { status ->
			status?.let {
				Toast.makeText(this, status.toString(), Toast.LENGTH_SHORT).show()
			}
		})

		setTabLayoutView()
		binding?.fabFavorite?.setOnClickListener {
			if (!buttonState) {
				buttonState = true
				binding?.fabFavorite?.setImageResource(R.drawable.ic_favorite)
			} else {
				buttonState = false
				binding?.fabFavorite?.setImageResource(R.drawable.ic_unfavorite)
			}
		}
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
		val viewPager: ViewPager2 = binding!!.viewPager

		viewPager.adapter = sectionPagerAdapter
		val tabs: TabLayout = binding!!.tabs
		val tabTitle = resources.getStringArray(R.array.tab_title)
		TabLayoutMediator(tabs, viewPager) { tab, position ->
			tab.text = tabTitle[position]
		}.attach()
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
		supportActionBar?.title = detailList.login
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