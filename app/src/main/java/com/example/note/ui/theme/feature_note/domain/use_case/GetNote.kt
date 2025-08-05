package com.example.note.ui.theme.feature_note.domain.use_case

import com.example.note.ui.theme.feature_note.domain.model.Note
import com.example.note.ui.theme.feature_note.domain.repository.NoteRepository

class GetNote(
    val repository: NoteRepository
) {
    suspend operator fun invoke(id: Int): Note? {
        return repository.getNoteById(id)
    }
}