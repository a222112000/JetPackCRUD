package com.example.crudapp.ui.theme

import com.example.crudapp.data.Users

sealed class UserListEvents {
    data class deleteUser(val user: Users): UserListEvents()
    data class OnChange(val user: Users, val isSelected: Boolean): UserListEvents()
    object onUndoDelete: UserListEvents()
    data class onUserClick(val user: Users): UserListEvents()
    object OnAddUser: UserListEvents()
}