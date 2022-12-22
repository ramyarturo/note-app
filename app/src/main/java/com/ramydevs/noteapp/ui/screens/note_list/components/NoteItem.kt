package com.ramydevs.noteapp.ui.screens.note_list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ramydevs.noteapp.data.source.note.Note


@Composable
fun NoteItem(
    modifier: Modifier = Modifier,
    note: Note,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = modifier
            .padding(10.dp)
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.large)
            .clickable {
                onClick()
            },
        shape = MaterialTheme.shapes.large,
        elevation = 10.dp,
    ) {
        Box(
            modifier = Modifier.background(
                Color(note.color)
            ),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                modifier = Modifier.padding(30.dp),
                text = note.title,
                style = MaterialTheme.typography.h4.copy(
                    color = Color.Black,
                )
            )
        }
    }
}