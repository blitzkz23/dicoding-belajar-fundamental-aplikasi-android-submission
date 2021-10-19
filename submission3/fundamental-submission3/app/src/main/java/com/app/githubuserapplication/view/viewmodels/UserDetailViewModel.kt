package com.app.githubuserapplication.view.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.githubuserapplication.api.ApiConfig
import com.app.githubuserapplication.model.response.DetailResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserDetailViewModel : ViewModel() {
	private val _listDetail = MutableLiveData<DetailResponse>()
	val listDetail: LiveData<DetailResponse> = _listDetail

	private val _isLoading = MutableLiveData<Boolean>()
	val isLoading: LiveData<Boolean> = _isLoading

	private val _status = MutableLiveData<String>()
	val status: LiveData<String> = _status

	internal fun getGithubUser(login: String) {
		_isLoading.value = true
		val client = ApiConfig.getApiService().getUserDetail(login)
		client.enqueue(object : Callback<DetailResponse> {
			override fun onResponse(
				call: Call<DetailResponse>,
				response: Response<DetailResponse>
			) {
				_isLoading.value = false
				if (response.isSuccessful) {
					val responseBody = response.body()
					if (responseBody != null) {
						_listDetail.value = response.body()
					}
				} else {
					Log.e(TAG, "onFailure: ${response.message()}")
				}
			}

			override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
				_isLoading.value = false
				Log.e(TAG, "onFailure: ${t.message}")
			}
		})
	}

	companion object {
		private const val TAG = "UserDetailModel"
	}
}