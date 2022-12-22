package com.ramydevs.noteapp.ui.common

import androidx.compose.runtime.Composable


@Composable
fun LoadingContent(
    isLoading: Boolean = false,
    loadingContent: @Composable () -> Unit = {},
    emptyContent : @Composable () -> Unit = {},
    isEmpty: Boolean = false,
    content: @Composable () -> Unit
) {

    when {
        isLoading -> {
            loadingContent()
        }
        isEmpty -> {
            emptyContent()
        }
        else -> {
            content()
        }
    }
}