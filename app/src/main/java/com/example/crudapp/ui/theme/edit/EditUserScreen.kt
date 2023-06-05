package com.example.crudapp.ui.theme.edit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.crudapp.util.UserEvents

@Composable
fun EditUserScreen(
    onPopBack: ()->Unit,
    viewModels: EditUserViewModel = hiltViewModel()
){
    val scaffoldState = rememberScaffoldState()
    LaunchedEffect(key1 = true){
        viewModels.uiEvent.collect(){
            when(it){
                is UserEvents.PopBackStack-> onPopBack()
                is UserEvents.ShowSnack->{
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = it.message,
                        actionLabel = it.action
                    )
                }
                else -> Unit
            }
        }
    }
    Scaffold(scaffoldState = scaffoldState,
    modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModels.onEvent(EditEvent.onSaveUserChange)
            }) {
                Icon(imageVector = Icons.Default.Check, contentDescription = "Save")
            }
        }
        ){
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(it)) {

            TextField(value = viewModels.name, onValueChange = {
                viewModels.onEvent(EditEvent.onNameChange(it))
            },
                placeholder = {
                    Text(text = "Name",style = TextStyle(
                        color = Color.Gray,
                        fontSize = 18.sp
                    )
                    )
                }, modifier = Modifier.fillMaxWidth())

            Spacer(modifier = Modifier.height(8.dp))

            TextField(value = viewModels.age, onValueChange = {
                viewModels.onEvent(EditEvent.onAgeChange(it))
            },
                placeholder = {
                    Text(text = "Age",style = TextStyle(
                        color = Color.Gray,
                        fontSize = 18.sp
                    ))
                }, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(8.dp))

            TextField(value = viewModels.city, onValueChange = {
                viewModels.onEvent(EditEvent.onCityChange(it))
            },
                placeholder = {
                    Text(text = "City",style = TextStyle(
                        color = Color.Gray,
                        fontSize = 18.sp
                    ))
                }, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(8.dp))

            TextField(value = viewModels.gender, onValueChange = {
                viewModels.onEvent(EditEvent.onGenderChange(it))
            },
                placeholder = {
                    Text(text = "Gender",style = TextStyle(
                        color = Color.Gray,
                        fontSize = 18.sp
                    ))
                }, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}