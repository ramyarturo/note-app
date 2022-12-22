package com.ramydevs.noteapp.ui.screens.note_list.components

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties

@Composable
fun SearchNoteDialog(
    openDialog: Boolean = false,
    onDismissDialog: () -> Unit = {}
) {

    BackHandler {
        onDismissDialog()
    }
    if (openDialog) {

        Popup(
            properties = PopupProperties(
                focusable = true,
                excludeFromSystemGesture = true,
            ),
            onDismissRequest = onDismissDialog
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colors.primary)
                    .padding(top = 40.dp),
                contentAlignment = Alignment.TopCenter,
            ) {
                SearchTextField(
                    onValueChanged = {}
                )
            }

        }
    }

}

@Composable
fun SearchTextField(
    onValueChanged: (String) -> Unit,
) {
    var value by remember {
        mutableStateOf("")
    }

    OutlinedTextField(
        value = value,
        shape = CircleShape,

        placeholder = { Text(text = "Search Note...") },
        trailingIcon = {
           IconButton(onClick = {
               value = ""
           }) {
               Icon(
                   imageVector = Icons.Default.Close,
                   contentDescription = "",
                   tint = MaterialTheme.colors.onSecondary,
               )
           }
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            backgroundColor = MaterialTheme.colors.secondary,
            cursorColor = MaterialTheme.colors.onSecondary,
        ),
        onValueChange = {
            value = it
            onValueChanged(it)


        }
    )
}