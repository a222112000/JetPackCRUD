package com.example.crudapp.ui.theme

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crudapp.data.UserRepository
import com.example.crudapp.data.Users
import com.example.crudapp.util.Route
import com.example.crudapp.util.UserEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {

    val users = repository.getUsers()

    private val _usersEvent = Channel<UserEvents>()
    val userEvent = _usersEvent.receiveAsFlow()

    private var deleteUser: Users? = null

    fun onEvent(events: UserListEvents){
        when(events){

            is UserListEvents.OnAddUser->{
               sendUiEvent(UserEvents.Navigate(Route.Edit_User))
            }
            is UserListEvents.deleteUser->{
                viewModelScope.launch {
                    deleteUser = events.user
                    repository.delete(events.user)
                    sendUiEvent(UserEvents.ShowSnack(
                        message = "User deleted",
                        action = "Undo"
                    ))
                }
            }
            is UserListEvents.onUserClick->{
                sendUiEvent(UserEvents.Navigate(Route.Edit_User+"?userId=${events.user.id}"))
            }
            is UserListEvents.onUndoDelete->{
                deleteUser?.let {
                    viewModelScope.launch {
                        repository.insertUser(it)
                    }
                }
            }is UserListEvents.OnChange->{
                viewModelScope.launch {
                    repository.insertUser(events.user.copy(userSelected = events.isSelected))
                }
            }

        }
    }

    private fun sendUiEvent(event: UserEvents){
        viewModelScope.launch {
            _usersEvent.send(event)
        }
    }
}