package com.ramydevs.noteapp.data.source.note.use_cases

import com.ramydevs.noteapp.core.Resource
import com.ramydevs.noteapp.data.source.note.Note
import com.ramydevs.noteapp.data.source.note.NoteRepository
import kotlinx.coroutines.flow.Flow

data class NoteUseCases(
    val getNotes: GetNotes,
    val getNote: GetNote,
    val insertNote: InsertNote,
    val deleteNote: DeleteNote,
)

class GetNotes(private val noteRepository: NoteRepository) {
    operator fun invoke(): Flow<List<Note>> {
        return noteRepository.getNotes()
    }
}

class GetNote(private val noteRepository: NoteRepository) {
    suspend operator fun invoke(id: Int): Note? {
        return noteRepository.getNote(id)
    }
}

class InsertNote(private val noteRepository: NoteRepository) {
    suspend operator fun invoke(note: Note): Resource<Nothing> {

        if (note.title.isBlank()) {
            return Resource.Failure("Title can't be empty.")
        }
        if (note.content.isBlank()) {
            return Resource.Failure("Content can't be empty.")
        }
        noteRepository.insertNote(note)
        return Resource.Success()
    }
}

class DeleteNote(private val noteRepository: NoteRepository) {
    suspend operator fun invoke(note: Note) {
        return noteRepository.deleteNote(note)
    }
}