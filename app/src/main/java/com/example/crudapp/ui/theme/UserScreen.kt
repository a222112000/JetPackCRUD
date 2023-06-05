package com.example.crudapp.ui.theme

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarResult
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.crudapp.util.UserEvents
import kotlinx.coroutines.flow.collect

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun UserScreen(
    onNav:(UserEvents.Navigate)->Unit,
    viewModel: UserViewModel = hiltViewModel()
){
    val users = viewModel.users.collectAsState(initial = emptyList())
    val scaffoldState = rememberScaffoldState()
    LaunchedEffect(key1 = true){
      viewModel.userEvent.collect{
          when(it){
              is UserEvents.Navigate->{
                  onNav(it)
              }
              is UserEvents.ShowSnack->{
                 val res = scaffoldState.snackbarHostState.showSnackbar(
                      message = it.message,
                      actionLabel = it.action
                  )
                  if (res == SnackbarResult.ActionPerformed){
                      viewModel.onEvent(UserListEvents.onUndoDelete)
                  }
              }
              else-> Unit
          }
      }
    }
    Scaffold(scaffoldState = scaffoldState,
    floatingActionButton = {
        FloatingActionButton(onClick = {
            viewModel.onEvent(UserListEvents.OnAddUser)
        }) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
        }
    }) {
        LazyColumn(modifier = Modifier.fillMaxSize()){
         items(users.value){
             UserItem(user = it, onEvent = viewModel::onEvent, modifier = Modifier
                 .fillMaxWidth()
                 .clickable { viewModel.onEvent(UserListEvents.onUserClick(it)) }.padding(16.dp))
         }
        }
    }
}