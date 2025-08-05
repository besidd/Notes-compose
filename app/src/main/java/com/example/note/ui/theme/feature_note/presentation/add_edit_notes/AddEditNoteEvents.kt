package com.example.note.ui.theme.feature_note.presentation.add_edit_notes

import androidx.compose.ui.focus.FocusState

sealed class AddEditNoteEvents {
    data class EnteredTitle(val value: String): AddEditNoteEvents()
    data class ChangeFocusTitle(val focusState: FocusState): AddEditNoteEvents()

    data class EnteredContent(val value: String): AddEditNoteEvents()
    data class ChangeFocusContent(val focusState: FocusState): AddEditNoteEvents()

    data class ChangeColor(val color: Int): AddEditNoteEvents()
    object SaveNote: AddEditNoteEvents()
}
