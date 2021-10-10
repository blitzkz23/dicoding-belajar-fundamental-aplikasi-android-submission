package com.app.githubuserapplication.utils

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide

class Helper {
	/**
	 * Function to show or hide progress bar.
	 */
	fun showLoading(isLoading: Boolean, view: View) {
		if (isLoading) {
			view.visibility = View.VISIBLE
		} else {
			view.visibility = View.INVISIBLE
		}
	}
}