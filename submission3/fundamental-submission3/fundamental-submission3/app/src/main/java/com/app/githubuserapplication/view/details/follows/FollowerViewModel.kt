package com.app.githubuserapplication.view.details.follows

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.githubuserapplication.api.ApiConfig
import com.app.githubuserapplication.model.response.GithubUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowerViewModel : ViewModel() {
	private val _listFollower = MutableLiveData<List<GithubUser>>()
	val listFollower: LiveData<List<GithubUser>> = _listFollower

	private val _isLoading = MutableLiveData<Boolean>()
	val isLoading: LiveData<Boolean> = _isLoading

	private val _status = MutableLiveData<String>()
	val status: LiveData<String> = _status

	internal fun getFollower(username: String) {
		_isLoading.value = true
		val client = ApiConfig.getApiService().getUserFollowers(username)
		client.enqueue(object : Callback<List<GithubUser>> {
			override fun onResponse(
				call: Call<List<GithubUser>>,
				response: Response<List<GithubUser>>
			) {
				_isLoading.value = false
				if (response.isSuccessful) {
					val responseBody = response.body()
					if (responseBody != null) {
						_listFollower.value = response.body()
					} else {
						Log.e(TAG, "onFailure: ${response.message()}")
					}
				}
			}

			override fun onFailure(call: Call<List<GithubUser>>, t: Throwable) {
				_isLoading.value = false
				Log.e(TAG, "onFailure: ${t.message}")
				_status.value = "Data failed to load, please try again."
			}
		})
	}

	companion object {
		private const val TAG = "FollowerViewModel"
	}
}