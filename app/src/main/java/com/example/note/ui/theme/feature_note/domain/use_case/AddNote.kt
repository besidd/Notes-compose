package com.example.note.ui.theme.feature_note.domain.use_case

import com.example.note.ui.theme.feature_note.domain.model.InvalidNoteException
import com.example.note.ui.theme.feature_note.domain.model.Note
import com.example.note.ui.theme.feature_note.domain.repository.NoteRepository

class AddNote(
    private val repository: NoteRepository
) {
    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note: Note) {
        if (note.title.isBlank()) {
            throw InvalidNoteException("Missing note title")
        }
        if (note.content.isBlank()) {
            throw InvalidNoteException("Missing note content")
        }
        repository.insertNote(note)
    }
}