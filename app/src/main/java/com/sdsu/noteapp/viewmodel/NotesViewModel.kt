package com.sdsu.noteapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.sdsu.noteapp.data.db.NotesAppDatabase
import com.sdsu.noteapp.data.model.AllNotesModel
import com.sdsu.noteapp.data.repository.NotesRepository

class NotesViewModel (application: Application): AndroidViewModel(application) {
    private lateinit var notesRepository: NotesRepository

    init {
        val notesDao = NotesAppDatabase.getDatabase(application, viewModelScope).notesDao()
        notesRepository = NotesRepository(notesDao)
    }

    fun getAllTypeNotes() : LiveData<List<AllNotesModel>> {
        return notesRepository.getAllTypeNotes()
    }

    fun getAllNotes(noteType: String): LiveData<List<AllNotesModel>> {
        return notesRepository.getAllNotes(noteType);
    }

    fun addNoteVm(notesModel: AllNotesModel) {
        notesRepository.addNoteRepo(notesModel)
    }

    fun deleteNote(notesModel: AllNotesModel) {
        notesRepository.deleteNoteRepo(notesModel)
    }

    fun updateNoteVm(notesModel: AllNotesModel) {
        notesRepository.updateNoteRepo(notesModel)
    }
}