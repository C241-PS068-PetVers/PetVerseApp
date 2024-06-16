package com.capstone.petverse.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.petverse.R
import com.capstone.petverse.data.repository.UserRepository
import com.capstone.petverse.data.response.UserProfile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _selectedTabIndex = MutableStateFlow(0)
    val selectedTabIndex: StateFlow<Int> = _selectedTabIndex.asStateFlow()

    private val _userProfile = MutableStateFlow<UserProfile?>(null)
    val userProfile: StateFlow<UserProfile?> = _userProfile.asStateFlow()

    fun onTabSelected(index: Int) {
        _selectedTabIndex.value = index
    }

    val photos = listOf(
        R.drawable.pet_adoption, R.drawable.pet_adoption,
        R.drawable.pet_adoption, R.drawable.pet_adoption,
        R.drawable.pet_adoption, R.drawable.pet_adoption,
        R.drawable.pet_adoption, R.drawable.pet_adoption
    )

    fun fetchUserProfile(token: String) {
        viewModelScope.launch {
            val response = userRepository.getUserProfile(token)
            if (response.isSuccessful) {
                _userProfile.value = response.body()?.user
            }
        }
    }
}
