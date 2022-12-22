package com.ramydevs.noteapp.data.source.note

import kotlinx.coroutines.flow.Flow

class DefaultNoteRepositoryImpl(
    private val noteDatabase: NoteDatabase
) : NoteRepository {
    private val noteDao get() = noteDatabase.noteDao()
    override fun getNotes(): Flow<List<Note>> {
        return noteDao.getNotes()
    }

    override suspend fun getNote(id: Int): Note? {
        return noteDao.getNote(id)
    }

    override suspend fun insertNote(note: Note) {
        return noteDao.insertNote(note)
    }

    override suspend fun deleteNote(note: Note) {
        return noteDao.deleteNote(note)
    }
}