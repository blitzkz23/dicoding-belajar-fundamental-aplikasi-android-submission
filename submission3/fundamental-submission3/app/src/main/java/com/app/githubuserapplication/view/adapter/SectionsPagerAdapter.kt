package com.app.githubuserapplication.view.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.app.githubuserapplication.view.fragments.FollowerFragment
import com.app.githubuserapplication.view.fragments.FollowingFragment

class SectionsPagerAdapter(activity: AppCompatActivity, private val login: Bundle) :
	FragmentStateAdapter(activity) {
	override fun getItemCount(): Int = 2


	override fun createFragment(position: Int): Fragment {
		var fragment: Fragment? = null
		when (position) {
			0 -> fragment = FollowerFragment()
			1 -> fragment = FollowingFragment()
		}
		fragment?.arguments = login
		return fragment as Fragment
	}
}