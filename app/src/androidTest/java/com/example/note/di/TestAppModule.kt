package com.example.note.di

import android.app.Application
import androidx.room.Room
import com.example.note.ui.theme.feature_note.data.data_source.NoteDatabase
import com.example.note.ui.theme.feature_note.data.repository.NoteRepositoryImpl
import com.example.note.ui.theme.feature_note.domain.repository.NoteRepository
import com.example.note.ui.theme.feature_note.domain.use_case.AddNote
import com.example.note.ui.theme.feature_note.domain.use_case.DeleteNotes
import com.example.note.ui.theme.feature_note.domain.use_case.GetNote
import com.example.note.ui.theme.feature_note.domain.use_case.GetNotes
import com.example.note.ui.theme.feature_note.domain.use_case.NoteUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {

    @Provides
    @Singleton
    fun providesNoteDatabase(app: Application): NoteDatabase {
        return Room.inMemoryDatabaseBuilder(
            app,
            NoteDatabase::class.java,
        ).build()
    }

    @Provides
    @Singleton
    fun providesNoteRepository(db: NoteDatabase): NoteRepository {
        return NoteRepositoryImpl(db.noteDao)
    }

    @Provides
    @Singleton
    fun providesNoteUseCases(repository: NoteRepository): NoteUseCases {
        return NoteUseCases(
            getNotes = GetNotes(repository),
            deleteNotes = DeleteNotes(repository),
            addNote = AddNote(repository),
            getNote = GetNote(repository)
        )
    }
}