package com.example.note.ui.theme.feature_note.presentation.notes

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.note.ui.theme.feature_note.domain.model.Note
import com.example.note.ui.theme.feature_note.domain.use_case.NoteUseCases
import com.example.note.ui.theme.feature_note.presentation.uti.NoteOrder
import com.example.note.ui.theme.feature_note.presentation.uti.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases
) : ViewModel() {

    private val _state = mutableStateOf(NotesState())
    val state = _state

    private var recentlyDeletedNote: Note? = null
    private var notesJob: Job? = null

    init {
        getNotes(NoteOrder.Date(OrderType.Descending))
    }

    fun onEvent(event: NotesEvents) {
        when (event) {
            is NotesEvents.DeleteNote -> {
                viewModelScope.launch {
                    noteUseCases.deleteNotes(event.note)
                    recentlyDeletedNote = event.note
                }
            }

            is NotesEvents.Order -> {
                if (_state.value.noteOrder::class == event.noteOrder::class &&
                    _state.value.noteOrder == event.noteOrder
                ) {
                    return
                }
                getNotes(event.noteOrder)

            }

            NotesEvents.RestoreNote -> {
                viewModelScope.launch {
                    noteUseCases.addNote(recentlyDeletedNote ?: return@launch)
                    recentlyDeletedNote = null
                }
            }

            NotesEvents.ToggleOrderSection -> {
                _state.value = _state.value.copy(
                    isOrderSectionVisible = !_state.value.isOrderSectionVisible
                )
            }
        }
    }

    private fun getNotes(order: NoteOrder) {
        notesJob?.cancel()
        notesJob = noteUseCases.getNotes(noteOrder = order).onEach { notes ->
            _state.value = state.value.copy(
                notes = notes,
                noteOrder = order
            )
        }
            .launchIn(viewModelScope)
    }
}