package com.example.note.ui.theme.feature_note.presentation.add_edit_notes.viewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.note.ui.theme.feature_note.domain.model.InvalidNoteException
import com.example.note.ui.theme.feature_note.domain.model.Note
import com.example.note.ui.theme.feature_note.domain.use_case.NoteUseCases
import com.example.note.ui.theme.feature_note.presentation.add_edit_notes.AddEditNoteEvents
import com.example.note.ui.theme.feature_note.presentation.add_edit_notes.NoteTextFieldState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var _noteTitle = mutableStateOf(
        NoteTextFieldState(
            hintText = "Title..."
        )
    )
    val noteTitle: State<NoteTextFieldState> = _noteTitle

    private var _noteContent = mutableStateOf(
        NoteTextFieldState(
            hintText = "Content..."
        )
    )
    val noteContent: State<NoteTextFieldState> = _noteContent

    private var _noteColor = mutableIntStateOf(Note.notColors.random().toArgb())
    val noteColor: State<Int> = _noteColor

    private var _eventFlow = MutableSharedFlow<UiState>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentNoteId: Int? = null

    init {
        savedStateHandle.get<Int>("noteId")?.let { noteId ->
            if (noteId != -1) {
                viewModelScope.launch {
                    noteUseCases.getNote(noteId)?.also { note ->
                        currentNoteId = note.noteId
                        _noteTitle.value = noteTitle.value.copy(
                            text = note.title,
                            isHintVisible = false,
                        )
                        _noteContent.value = noteContent.value.copy(
                            text = note.content,
                            isHintVisible = false,
                        )
                        _noteColor.intValue = note.noteColor
                    }
                }
            }
        }
    }

    fun onEvent(event: AddEditNoteEvents) {
        when (event) {
            is AddEditNoteEvents.ChangeColor -> {
                _noteColor.intValue = event.color
            }

            is AddEditNoteEvents.ChangeFocusContent -> {
                _noteContent.value = noteContent.value.copy(
                    isHintVisible = !event.focusState.hasFocus &&
                            noteContent.value.text.isBlank()
                )
            }

            is AddEditNoteEvents.ChangeFocusTitle -> {
                _noteTitle.value = noteTitle.value.copy(
                    isHintVisible = !event.focusState.hasFocus &&
                            noteTitle.value.text.isBlank()
                )
            }

            is AddEditNoteEvents.EnteredContent -> {
                _noteContent.value = noteContent.value.copy(
                    text = event.value
                )
            }

            is AddEditNoteEvents.EnteredTitle -> {
                _noteTitle.value = noteTitle.value.copy(
                    text = event.value
                )
            }

            AddEditNoteEvents.SaveNote -> {
                viewModelScope.launch {
                    try {
                        noteUseCases.addNote(
                            Note(
                                title = noteTitle.value.text,
                                content = noteContent.value.text,
                                timeStamp = System.currentTimeMillis(),
                                noteColor = noteColor.value,
                                noteId = currentNoteId
                            )
                        )
                        _eventFlow.emit(UiState.SaveNote)
                    } catch (e: InvalidNoteException) {
                        _eventFlow.emit(
                            UiState.ShowSnackBar(message = e.message ?: "Unknown Exception")
                        )
                    }
                }
            }
        }
    }
}

sealed class UiState {
    data class ShowSnackBar(val message: String) : UiState()
    object SaveNote : UiState()
}
