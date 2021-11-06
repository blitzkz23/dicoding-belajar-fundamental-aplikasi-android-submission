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


class FollowingFragment : Fragment() {
	private var _binding: FragmentFollowsBinding? = null
	private val binding get() = _binding
	private lateinit var followingViewModel: FollowingViewModel
	private val helper = Helper()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		followingViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
			FollowingViewModel::class.java)
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

		followingViewModel.isLoading.observe(viewLifecycleOwner, {
			helper.showLoading(it, binding!!.progressBar3)
		})
		followingViewModel.listFollowing.observe(viewLifecycleOwner, { listFollowing ->
			setDataToFragment(listFollowing)
		})
		followingViewModel.status.observe(viewLifecycleOwner, { status ->
			status?.let {
				Toast.makeText(activity, status.toString(), Toast.LENGTH_SHORT).show()
			}
		})

		followingViewModel.getFollowing(arguments?.getString(UserDetailActivity.EXTRA_FRAGMENT).toString())
	}

	/**
	 * Function to set the data from API into fragment's view.
	 */
	private fun setDataToFragment(listFollowing: List<GithubUser>) {
		val listUser = ArrayList<GithubUser>()
		binding?.apply {
			for (user in listFollowing) {
				listUser.clear()
				listUser.addAll(listFollowing)
			}
			rvFollower.layoutManager = LinearLayoutManager(context)
			val adapter = FollowsAdapter(listFollowing)
			rvFollower.adapter = adapter
		}
	}

	override fun onDestroy() {
		super.onDestroy()
		_binding = null
	}
}