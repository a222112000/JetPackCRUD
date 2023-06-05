package com.example.crudapp.util

sealed class UserEvents{

    object PopBackStack: UserEvents()
    data class Navigate(val route: String): UserEvents()
    data class ShowSnack(val message: String, val action: String? = null): UserEvents()
}
