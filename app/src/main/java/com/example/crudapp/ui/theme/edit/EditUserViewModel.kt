package com.example.crudapp.ui.theme.edit

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crudapp.data.UserRepository
import com.example.crudapp.data.Users
import com.example.crudapp.util.UserEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditUserViewModel @Inject constructor(
    private val repository: UserRepository,
    saveState:SavedStateHandle
): ViewModel(){

    var user by mutableStateOf<Users?>(null)
        private set

    var name by mutableStateOf("")
        private set

    var age by mutableStateOf("")
        private set

    var city by mutableStateOf("")
        private set

    var gender by mutableStateOf("")
        private set

    private val _uiEvent = Channel<UserEvents>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        val userId = saveState.get<Int>("userId")
        if(userId != -1){
            viewModelScope.launch {
                if (userId != null) {
                    repository.getUserById(userId)?.let {
                        name = it.name
                        age = it.age.toString() ?: ""
                        city = it.city
                        gender = it.gender
                        this@EditUserViewModel.user = it
                    }
                }

            }
        }
    }
    fun onEvent(event: EditEvent){
        when(event){
            is EditEvent.onNameChange->{
                name = event.name
            }
            is EditEvent.onAgeChange ->{
                age = event.age

            }
            is EditEvent.onCityChange->{
                city = event.city
            }
            is EditEvent.onGenderChange->{
                gender = event.gender
            }
            is EditEvent.onSaveUserChange->{
                viewModelScope.launch {
                    if(name.isBlank()){
                        sendEditEvent(UserEvents.ShowSnack(
                            message = "Name should not be empty"
                        ))
                        return@launch
                    }
                    if(age.isBlank()){
                        sendEditEvent(UserEvents.ShowSnack(
                            message = "Age should not be empty"
                        ))
                        return@launch
                    }

                    if(gender.isBlank()){
                        sendEditEvent(UserEvents.ShowSnack(
                            message = "Gender should not be empty"
                        ))
                        return@launch
                    }
                    if(city.isBlank()){
                        sendEditEvent(UserEvents.ShowSnack(
                            message = "City should not be empty"
                        ))
                        return@launch
                    }
                    user?.let {
                        Users(
                            id = user?.id ?: 0,
                            name = name,
                            age = it.age,
                            city = city,
                            gender = gender,
                            userSelected = user?.userSelected ?: false
                        )
                    }?.let {
                        repository.insertUser(
                            it
                        )
                    }
                    sendEditEvent(UserEvents.PopBackStack)
                }
            }
        }
    }
    private fun sendEditEvent(events: UserEvents){
        viewModelScope.launch {
            _uiEvent.send(events)
        }
    }
}