package com.app.githubuserapplication.view.details.follows

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.githubuserapplication.databinding.FragmentFollowsBinding
import com.app.githubuserapplication.model.response.GithubUser
import com.app.githubuserapplication.view.details.UserDetailActivity
import com.app.githubuserapplication.utils.Helper


class FollowerFragment : Fragment() {
	private var _binding: FragmentFollowsBinding? = null
	private val binding get() = _binding
	private lateinit var followerViewModel: FollowerViewModel
	private val helper = Helper()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		followerViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
			FollowerViewModel::class.java
		)
	}

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		_binding = FragmentFollowsBinding.inflate(inflater, container, false)
		return binding?.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		followerViewModel.isLoading.observe(viewLifecycleOwner, {
			helper.showLoading(it, binding!!.progressBar3)
		})
		followerViewModel.listFollower.observe(viewLifecycleOwner, { listFollower ->
			setDataToFragment(listFollower)
		})
		followerViewModel.status.observe(viewLifecycleOwner, { status ->
			status?.let {
				Toast.makeText(activity, status.toString(), Toast.LENGTH_SHORT).show()
			}
		})

		followerViewModel.getFollower(
			arguments?.getString(UserDetailActivity.EXTRA_FRAGMENT).toString()
		)
	}

	/**
	 * Function to set the data from API into fragment's view.
	 */
	private fun setDataToFragment(listFollower: List<GithubUser>) {
		val listUser = ArrayList<GithubUser>()
		binding?.apply {
			for (user in listFollower) {
				listUser.clear()
				listUser.addAll(listFollower)
			}
			rvFollower.layoutManager = LinearLayoutManager(context)
			val adapter = FollowsAdapter(listFollower)
			rvFollower.adapter = adapter
		}
	}

	override fun onDestroy() {
		super.onDestroy()
		_binding = null
	}

}