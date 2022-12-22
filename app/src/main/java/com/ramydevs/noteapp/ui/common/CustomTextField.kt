package com.ramydevs.noteapp.ui.common

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardCapitalization


@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    textStyle: TextStyle = TextStyle.Default,
    hint: String = "",
    value: String = "",
    onValueChanged: (String) -> Unit = {},
    singleLine: Boolean = false
) {
    val focusRequester = remember { FocusRequester() }
    var isFocused by remember {
        mutableStateOf(false)
    }
    Box(
        modifier = modifier.pointerInput(true) {
            detectTapGestures(
                onTap = {
                    isFocused = true
                    focusRequester.requestFocus()
                }
            )
        }
    ) {
        TextField(
            modifier = Modifier
                .focusRequester(focusRequester)
                .fillMaxWidth()
                .onFocusChanged {
                    isFocused = it.isFocused
                },
            colors = TextFieldDefaults.textFieldColors(
                cursorColor = Color.White,
                backgroundColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            value = value,
            onValueChange = onValueChanged,
            singleLine = singleLine,
            textStyle = textStyle,

            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,

                )
        )

        if (!isFocused && value.isBlank()) {
            Text(
                text = hint,
                style = textStyle.copy(
                    color = Color(0xff9A9A9A),
                ),
            )
        }
    }
}