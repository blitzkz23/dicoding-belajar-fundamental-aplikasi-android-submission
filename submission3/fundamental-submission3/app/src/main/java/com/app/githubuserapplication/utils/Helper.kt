package com.app.githubuserapplication.utils

import android.content.Context
import android.view.View
import android.widget.Toast

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

	/**
	 * Function to show toast
	 */
	fun showToast(context: Context,  message: String) {
		Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
	}
}