package com.app.githubuserapplication.view.details.follows

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.githubuserapplication.databinding.ItemRowUserBinding
import com.app.githubuserapplication.model.response.GithubUser
import com.bumptech.glide.Glide

class FollowsAdapter(private val listFollow: List<GithubUser>) :
	RecyclerView.Adapter<FollowsAdapter.ViewHolder>() {
	class ViewHolder(var binding: ItemRowUserBinding) : RecyclerView.ViewHolder(binding.root)

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		val binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
		return ViewHolder(binding)
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		val follow = listFollow[position]

		with(holder.binding) {
			Glide.with(root.context)
				.load(follow.avatarUrl)
				.circleCrop()
				.into(imgUserAvatar)
			tvName.text = follow.login
			tvUrl.text = follow.htmlUrl
		}
	}

	override fun getItemCount(): Int = listFollow.size
}