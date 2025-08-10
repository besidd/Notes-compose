package com.example.note.ui.theme.feature_note.domain.use_case.featureNote.domain.use_case

import com.example.note.ui.theme.feature_note.domain.model.Note
import com.example.note.ui.theme.feature_note.domain.use_case.GetNotes
import com.example.note.ui.theme.feature_note.domain.use_case.featureNote.data.repository.FakeNoteRepository
import com.example.note.ui.theme.feature_note.presentation.uti.NoteOrder
import com.example.note.ui.theme.feature_note.presentation.uti.OrderType
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetNotesTest {

    private lateinit var getNotes: GetNotes
    private lateinit var fakeNoteRepository: FakeNoteRepository

    @Before
    fun setUp() {
        fakeNoteRepository = FakeNoteRepository()
        getNotes = GetNotes(fakeNoteRepository)

        val notesToInsert = mutableListOf<Note>()
        ('a'..'z').forEachIndexed { index, char ->
            notesToInsert.add(
                Note(
                    title = char.toString(),
                    content = char.toString(),
                    timeStamp = index.toLong(),
                    noteColor = index,
                    noteId = index,
                )
            )
        }
        notesToInsert.shuffle()
        notesToInsert.forEach {
            runBlocking {
                fakeNoteRepository.insertNote(
                    note = it
                )
            }
        }
    }

    @Test
    fun `Order notes by title ascending, correct order`() = runBlocking {
        val notes = getNotes(NoteOrder.Title(OrderType.Ascending)).first()
        for (i in 0..notes.size-2) {
            assertThat(notes[i].title).isLessThan(notes[i+1].title)
        }
    }

    @Test
    fun `Order notes by title descending, correct order`() = runBlocking {
        val notes = getNotes(NoteOrder.Title(OrderType.Descending)).first()
        for (i in 0..notes.size-2) {
            assertThat(notes[i].title).isGreaterThan(notes[i+1].title)
        }
    }

    @Test
    fun `Order notes by date ascending, correct order`() = runBlocking {
        val notes = getNotes(NoteOrder.Date(OrderType.Ascending)).first()
        for (i in 0..notes.size-2) {
            assertThat(notes[i].timeStamp).isLessThan(notes[i+1].timeStamp)
        }
    }

    @Test
    fun `Order notes by date descending, correct order`() = runBlocking {
        val notes = getNotes(NoteOrder.Date(OrderType.Descending)).first()
        for (i in 0..notes.size-2) {
            assertThat(notes[i].timeStamp).isGreaterThan(notes[i+1].timeStamp)
        }
    }

    @Test
    fun `Order notes by color ascending, correct order`() = runBlocking {
        val notes = getNotes(NoteOrder.Color(OrderType.Ascending)).first()
        for (i in 0..notes.size-2) {
            assertThat(notes[i].noteColor).isLessThan(notes[i+1].noteColor)
        }
    }

    @Test
    fun `Order notes by color descending, correct order`() = runBlocking {
        val notes = getNotes(NoteOrder.Color(OrderType.Descending)).first()
        for (i in 0..notes.size-2) {
            assertThat(notes[i].noteColor).isGreaterThan(notes[i+1].noteColor)
        }
    }
}