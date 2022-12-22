package com.ramydevs.noteapp.core.di.modules

import android.app.Application
import androidx.room.Room
import com.ramydevs.noteapp.data.source.note.DefaultNoteRepositoryImpl
import com.ramydevs.noteapp.data.source.note.NoteDatabase
import com.ramydevs.noteapp.data.source.note.NoteRepository
import com.ramydevs.noteapp.data.source.note.use_cases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): NoteDatabase {
        return Room.databaseBuilder(
            app,
            NoteDatabase::class.java,
            "note_db"
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(noteDatabase: NoteDatabase): NoteRepository {
        return DefaultNoteRepositoryImpl(noteDatabase)
    }

    @Provides
    @Singleton
    fun provideNoteUseCases(noteRepository: NoteRepository): NoteUseCases {
        return NoteUseCases(
            getNotes = GetNotes(noteRepository),
            getNote = GetNote(noteRepository),
            insertNote = InsertNote(noteRepository),
            deleteNote = DeleteNote(noteRepository)
        )
    }
}