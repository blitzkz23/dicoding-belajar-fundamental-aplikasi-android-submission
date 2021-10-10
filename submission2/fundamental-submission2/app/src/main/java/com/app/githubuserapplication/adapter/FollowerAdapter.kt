package com.app.githubuserapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.githubuserapplication.databinding.ItemRowUserBinding
import com.app.githubuserapplication.model.GithubUser
import com.bumptech.glide.Glide

class FollowerAdapter(private val listFollower: List<GithubUser>) : RecyclerView.Adapter<FollowerAdapter.ViewHolder>(){
	class ViewHolder(var binding: ItemRowUserBinding) : RecyclerView.ViewHolder(binding.root)

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		val binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
		return ViewHolder(binding)
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		val follower = listFollower[position]

		with(holder.binding) {
			Glide.with(root.context)
				.load(follower.avatarUrl)
				.circleCrop()
				.into(imgUserAvatar)
			tvName.text = follower.login
			tvUrl.text = follower.htmlUrl
		}
	}

	override fun getItemCount(): Int = listFollower.size
}