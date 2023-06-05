package com.example.crudapp.ui.theme

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.crudapp.data.Users

@Composable
fun UserItem(
    user: Users,
    onEvent:(UserListEvents) -> Unit,
    modifier: Modifier = Modifier
    ){
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.Center) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "Name: ", color = Color.Gray, fontWeight = FontWeight.Light)
                        Text(text = "${user.name} ")
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(text = "Age: ", color = Color.Gray, fontWeight = FontWeight.Light)
                        Text(text = "${user.age} ")
                    }
                    Spacer(modifier = Modifier.width(3.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "Gender: ", color = Color.Gray, fontWeight = FontWeight.Light)
                        Text(text = "${user.gender} ")
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(text = "City: ", color = Color.Gray, fontWeight = FontWeight.Light)
                        Text(text = "${user.city} ")
                    }
                }
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(onClick = {
                    onEvent(UserListEvents.deleteUser(user))
                }){
                    Icon(imageVector = Icons.Default.Delete ,
                        contentDescription = "Delete")
                }
            }
        }
        Checkbox(checked = user.userSelected, onCheckedChange ={
            onEvent(UserListEvents.OnChange(user,it))
        } )
    }
}