package com.ramydevs.noteapp.ui.screens.add_note

import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramydevs.noteapp.core.Resource
import com.ramydevs.noteapp.data.source.note.Note
import com.ramydevs.noteapp.data.source.note.use_cases.NoteUseCases
import com.ramydevs.noteapp.utils.NoteDestinationArgs
import com.ramydevs.noteapp.utils.generateRandomColor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class UiState(
    val isLoading: Boolean = false,
    val message: String? = null,
    val note: Note = Note(
        title = "",
        content = "",
    )
)

@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _noteId = savedStateHandle.get<Int>(NoteDestinationArgs.NOTE_ID_ARG)
    val isEditing get() = _noteId != -1

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()
    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private var _initialNote: Note = _uiState.value.note
    val hasUserEditedNote get() = _initialNote != _uiState.value.note

    init {
        if (isEditing) {
            viewModelScope.launch {
                _uiState.update {
                    it.copy(
                        isLoading = true
                    )
                }
                val note = noteUseCases.getNote(_noteId!!) ?: return@launch
                _initialNote = note

                _uiState.update {
                    it.copy(
                        note = note,
                        isLoading = false
                    )
                }
            }
        }
    }

    fun onTitleChanged(title: String) = _uiState.update {
        it.copy(
            note = it.note.copy(
                title = title
            )
        )
    }

    fun onContentChanged(content: String) = _uiState.update {
        it.copy(
            note = it.note.copy(
                content = content
            )
        )
    }

    fun onDeleteNote() {
        val note = _uiState.value.note.takeIf { it.id != -1 } ?: return
        viewModelScope.launch {
            noteUseCases.deleteNote(note)
            _uiEvent.send(UiEvent.DeletedNote)
        }
    }

    fun onSaveNote() {
        val note = _uiState.value.note
        viewModelScope.launch {
            val result = noteUseCases.insertNote(
                note.copy(
                    title = note.title.trim(),
                    content = note.content.trim(),
                    color = if (isEditing) note.color else generateRandomColor().toArgb(),
                    createdAt = if (isEditing) note.createdAt else System.currentTimeMillis(),
                    updatedAt = if (isEditing) System.currentTimeMillis() else null
                )
            )
            when (result) {
                is Resource.Success -> {
                    _uiEvent.send(UiEvent.SavedNote)
                }
                is Resource.Failure -> {
                    _uiEvent.send(
                        UiEvent.ShowSnackbar(result.message ?: "Error saving note")
                    )
                }
            }
        }

    }
}

sealed interface UiEvent {
    data class ShowSnackbar(val message: String) : UiEvent
    object SavedNote : UiEvent
    object DeletedNote : UiEvent
}