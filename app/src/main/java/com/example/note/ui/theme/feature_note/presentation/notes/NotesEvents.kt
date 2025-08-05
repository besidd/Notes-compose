package com.example.note.ui.theme.feature_note.presentation.notes

import com.example.note.ui.theme.feature_note.domain.model.Note
import com.example.note.ui.theme.feature_note.presentation.uti.NoteOrder

sealed class NotesEvents {
    data class Order(val noteOrder: NoteOrder) : NotesEvents()
    data class DeleteNote(val note: Note) : NotesEvents()
    object RestoreNote : NotesEvents()
    object ToggleOrderSection : NotesEvents()
}