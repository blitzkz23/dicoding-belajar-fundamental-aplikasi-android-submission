package com.app.githubuserapplication.view.favorites

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.githubuserapplication.databinding.ItemRowUserBinding
import com.app.githubuserapplication.model.database.FavoriteUser
import com.app.githubuserapplication.utils.FavoriteDiffCallback
import com.app.githubuserapplication.view.details.UserDetailActivity
import com.bumptech.glide.Glide

class FavoriteUserAdapter : RecyclerView.Adapter<FavoriteUserAdapter.FavoriteUserViewHolder>() {
	private val listFavorites = ArrayList<FavoriteUser>()

	fun setFavorites(favorites: List<FavoriteUser>) {
		val diffCallback = FavoriteDiffCallback(this.listFavorites, favorites)
		val diffResult = DiffUtil.calculateDiff(diffCallback)
		this.listFavorites.clear()
		this.listFavorites.addAll(favorites)
		diffResult.dispatchUpdatesTo(this)
	}

	class FavoriteUserViewHolder(private val binding: ItemRowUserBinding) : RecyclerView.ViewHolder(binding.root) {
		fun bind(favorites: FavoriteUser) {
			with(binding) {
				tvName.text = favorites.login
				tvUrl.text = favorites.htmlUrl
				itemView.setOnClickListener {
					val intent = Intent(itemView.context, UserDetailActivity::class.java)
					intent.putExtra(UserDetailActivity.EXTRA_USER, favorites.login)
					itemView.context.startActivity(intent)
				}
			}
			Glide.with(itemView.context)
				.load(favorites.avatarUrl)
				.circleCrop()
				.into(binding.imgUserAvatar)
		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteUserViewHolder {
		val itemRowUserBinding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
		return FavoriteUserViewHolder(itemRowUserBinding)
	}

	override fun onBindViewHolder(holder: FavoriteUserViewHolder, position: Int) {
		val favorites = listFavorites[position]
		holder.bind(favorites)
	}

	override fun getItemCount(): Int = listFavorites.size
}