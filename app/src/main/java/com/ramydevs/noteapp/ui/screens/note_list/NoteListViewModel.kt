package com.ramydevs.noteapp.ui.screens.note_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramydevs.noteapp.data.source.note.Note
import com.ramydevs.noteapp.data.source.note.use_cases.NoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


data class NoteListUiState(
    val items: List<Note> = emptyList(),
    val isLoading: Boolean = false
)

@HiltViewModel
class NoteListViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases
) : ViewModel() {

    private val _uiState = MutableStateFlow(NoteListUiState())
    val uiState get() = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true
                )
            }
            delay(800)
            noteUseCases.getNotes().collectLatest { noteList ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        items = noteList,
                    )
                }
            }
        }
    }

}