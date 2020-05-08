package com.sdsu.noteapp.data.repository

import androidx.lifecycle.LiveData
import com.sdsu.noteapp.data.db.NotesDao
import com.sdsu.noteapp.data.model.AllNotesModel

class NotesRepository(private val notesDao: NotesDao) {

    fun getAllNotes(noteType: String): LiveData<List<AllNotesModel>>{
        return notesDao.getAllNotes(noteType.toString())
    }

    fun addNoteRepo(notesModel: AllNotesModel) {
        notesDao.addNotes(notesModel)
    }

    fun getAllTypeNotes(): LiveData<List<AllNotesModel>> {
        return notesDao.getAllTypeNotes()
    }

    fun updateNoteRepo(notesModel: AllNotesModel) {
        notesDao.updateNotes(notesModel)
    }

    fun deleteNoteRepo(notesModel: AllNotesModel) {
        notesDao.deleteNotes(notesModel)
    }
}