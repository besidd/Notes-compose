package com.example.note.ui.theme.feature_note.presentation.notes

import com.example.note.ui.theme.feature_note.domain.model.Note
import com.example.note.ui.theme.feature_note.presentation.uti.NoteOrder
import com.example.note.ui.theme.feature_note.presentation.uti.OrderType

data class NotesState(
    val notes: List<Note> = emptyList(),
    val noteOrder: NoteOrder = NoteOrder.Date(OrderType.Ascending),
    val isOrderSectionVisible: Boolean = false
)
