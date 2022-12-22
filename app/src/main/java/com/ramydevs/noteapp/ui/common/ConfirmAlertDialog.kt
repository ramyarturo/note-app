package com.ramydevs.noteapp.ui.common

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.ramydevs.noteapp.R
import com.ramydevs.noteapp.ui.theme.Green
import com.ramydevs.noteapp.ui.theme.Red


@Composable
fun ConfirmAlertDialog(
    show: Boolean = false,
    onConfirm: () -> Unit = {},
    onDismiss: () -> Unit = {},
    message: String = "",
    confirmText: String
) {

    if (show) {
        Dialog(
            onDismissRequest = onDismiss,
            properties = DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = false
            ),
        ) {
            DialogContent(
                message = message,
                onConfirm = onConfirm,
                onDismiss = onDismiss,
                confirmText = confirmText
            )
        }
    }
}

@Composable
private fun DialogContent(
    message: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    confirmText: String
) {

    Card(
        modifier = Modifier.padding(10.dp),
        shape = MaterialTheme.shapes.large,
        backgroundColor = MaterialTheme.colors.primary,
        elevation = 10.dp,
    ) {
        Column(

            modifier = Modifier.padding(
                horizontal = 10.dp,
                vertical = 20.dp
            ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            Icon(
                modifier = Modifier.size(40.dp),
                painter = painterResource(R.drawable.ic_outline_info_24),
                contentDescription = "",
                tint = MaterialTheme.colors.onSecondary,
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = message,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.body1.copy(
                    color = MaterialTheme.colors.onSecondary
                )
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {


                OutlinedButton(
                    modifier = Modifier.width(100.dp),
                    onClick = onDismiss,
                    colors = ButtonDefaults.outlinedButtonColors(
                        backgroundColor = Red
                    )
                ) { Text(text = "Discard") }

                OutlinedButton(
                    modifier = Modifier.width(100.dp),
                    onClick = onConfirm,
                    colors = ButtonDefaults.outlinedButtonColors(
                        backgroundColor = Green
                    )
                ) { Text(text = confirmText) }


            }
        }
    }
}