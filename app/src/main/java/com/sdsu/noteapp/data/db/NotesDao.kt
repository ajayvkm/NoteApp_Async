package com.sdsu.noteapp.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.sdsu.noteapp.data.model.AllNotesModel

@Dao
interface NotesDao {

    @Query("SELECT * FROM AllNotes where noteType=:noteType")
    fun getAllNotes(noteType: String): LiveData<List<AllNotesModel>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addNotes(notesModel: AllNotesModel)

    @Query("SELECT * FROM AllNotes")
    fun getAllTypeNotes(): LiveData<List<AllNotesModel>>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateNotes(notesModel: AllNotesModel)

    @Delete
    fun deleteNotes(notesModel: AllNotesModel)

}
