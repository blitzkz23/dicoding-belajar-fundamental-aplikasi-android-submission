package com.app.githubapplication.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.githubapplication.databinding.ItemRowUserBinding
import com.app.githubapplication.model.User
import com.bumptech.glide.Glide

class ListUserAdapter(private val listUser: ArrayList<User>) : RecyclerView.Adapter<ListUserAdapter.ListViewHolder>() {
	private lateinit var onItemClickCallback: OnItemClickCallback

	interface OnItemClickCallback {
		fun onItemClicked(data: User)
	}

	fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
		this.onItemClickCallback = onItemClickCallback
	}

	class ListViewHolder(var binding: ItemRowUserBinding) : RecyclerView.ViewHolder(binding.root) {	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
		val binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
		return ListViewHolder(binding)
	}

	@SuppressLint("SetTextI18n")
	override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
		val user = listUser[position]

//		Use holder.binding.apply to apply all the binding to the view so we dont need to write every single holder.binding again.
		holder.binding.apply {
			Glide.with(root.context)
				.load(user.avatar)
				.circleCrop()
				.into(imgUserAvatar)
			tvName.text = user.name
			tvCompany.text = user.company
			tvFollower.text = "${user.follower} followers"
			tvRepository.text = "${user.repository} repositories"

			root.setOnClickListener { onItemClickCallback.onItemClicked(listUser[position]) }
		}
	}

	override fun getItemCount(): Int = listUser.size
}