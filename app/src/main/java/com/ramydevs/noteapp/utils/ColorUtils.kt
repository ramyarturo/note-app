package com.ramydevs.noteapp.utils

import androidx.compose.ui.graphics.Color
import kotlin.random.Random


fun generateRandomColor(): Color {
    val randomNumber = { Random.nextInt(256) }

    return Color(
        randomNumber(),
        randomNumber(),
        randomNumber(),
        randomNumber()
    );

}