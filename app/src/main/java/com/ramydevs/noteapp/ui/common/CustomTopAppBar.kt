package com.ramydevs.noteapp.ui.common

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ramydevs.noteapp.R

@Composable
fun CustomTopAppBar(
    modifier: Modifier = Modifier,
    title: String = "",
    onNavIconClick: () -> Unit = {},
    showNavButton: Boolean = false,
    actions: @Composable RowScope.() -> Unit = {}
) {
    TopAppBar(
        modifier = modifier,
        title = { Text(text = title, style = MaterialTheme.typography.h5) },
        navigationIcon = showNavButton.takeIf { it }?.let {
            {
                CustomRoundedIcon(
                    painter = painterResource(R.drawable.ic_outline_arrow_back_24),
                    onClick = onNavIconClick
                )
            }
        },
        actions = actions,
        elevation = 0.dp
    )
}