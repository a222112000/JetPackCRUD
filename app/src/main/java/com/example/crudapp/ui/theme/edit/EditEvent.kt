package com.example.crudapp.ui.theme.edit

sealed class EditEvent{
    data class onNameChange(val name: String): EditEvent()
    data class onAgeChange(val age: String): EditEvent()
    data class onGenderChange(val gender: String): EditEvent()
    data class onCityChange(val city: String): EditEvent()
    object onSaveUserChange: EditEvent()
}
