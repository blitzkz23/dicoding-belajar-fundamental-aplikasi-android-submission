package com.app.githubapplication.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.githubapplication.R
import com.app.githubapplication.databinding.ActivityUserDetailsBinding
import com.app.githubapplication.model.User

class UserDetailsActivity : AppCompatActivity() {
	companion object {
		const val EXTRA_USER = "extra_user"
	}

	private lateinit var binding: ActivityUserDetailsBinding

	@SuppressLint("SetTextI18n")
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityUserDetailsBinding.inflate(layoutInflater)
		setContentView(binding.root)

		val user = intent.getParcelableExtra<User>(EXTRA_USER) as User
//		Using another binding.apply so we dont neet to write every binding again.
		binding.apply {
			detailsImgAvatar.setImageResource(user.avatar)
			detailsTvName.text = user.name
			detailsTvUsername.text = user.username
			detailsTvCompany.text = user.company
			detailsTvLocation.text = user.location
			detailsTvFollower.text = getString(R.string.followers, user.follower)
			detailsTvFollowing.text = getString(R.string.following, user.following)
			detailsTvRepositories.text = getString(R.string.repositories, user.repository)
		}

		setActionBarTitle(user.username)

		binding.detailsBtnShare.setOnClickListener {
			val sendIntent: Intent = Intent().apply {
				action = Intent.ACTION_SEND
				putExtra(Intent.EXTRA_TEXT, "Follow @${user.username} on GitHub now!")
				type = "text/plain"
			}
			val shareIntent = Intent.createChooser(sendIntent, null)
			startActivity(shareIntent)
		}
	}

	private fun setActionBarTitle(title: String) {
		supportActionBar?.title = title
	}
}