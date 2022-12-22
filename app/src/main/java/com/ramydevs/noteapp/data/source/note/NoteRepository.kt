package com.ramydevs.noteapp.data.source.note

import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    fun getNotes(): Flow<List<Note>>
    suspend fun getNote(id: Int) : Note?
    suspend fun insertNote(note: Note)
    suspend fun deleteNote(note: Note)
}