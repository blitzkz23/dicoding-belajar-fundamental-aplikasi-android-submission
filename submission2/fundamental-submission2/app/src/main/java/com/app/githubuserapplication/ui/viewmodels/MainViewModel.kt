package com.app.githubuserapplication.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.githubuserapplication.api.ApiConfig
import com.app.githubuserapplication.model.GithubUser
import com.app.githubuserapplication.model.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

	private val _listGithubUser = MutableLiveData<List<GithubUser>>()
	val listGithubUser: LiveData<List<GithubUser>> = _listGithubUser

	private val _isLoading = MutableLiveData<Boolean>()
	val isLoading: LiveData<Boolean> = _isLoading

	private val _totalCount = MutableLiveData<Int>()
	val totalCount: LiveData<Int> = _totalCount

	companion object {
		private const val TAG = "MainViewModel"
	}

	internal fun searchGithubUser(query: String) {
		_isLoading.value = true
		val client = ApiConfig.getApiService().searchUser(query)
		client.enqueue(object : Callback<UserResponse> {
			override fun onResponse(
				call: Call<UserResponse>,
				response: Response<UserResponse>
			) {
				_isLoading.value = false
				if (response.isSuccessful) {
					val responseBody = response.body()
					if (responseBody != null) {
						_listGithubUser.value = response.body()?.githubUsers
						_totalCount.value = response.body()?.totalCount
					}
				} else {
					Log.e(TAG, "onFailure: ${response.message()}")
				}
			}

			override fun onFailure(call: Call<UserResponse>, t: Throwable) {
				_isLoading.value = false
				Log.e(TAG, "onFailure: ${t.message}")
			}
		})
	}


}