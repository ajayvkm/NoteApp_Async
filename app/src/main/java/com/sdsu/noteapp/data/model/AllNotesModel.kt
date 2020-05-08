package com.sdsu.noteapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "AllNotes")
data class AllNotesModel(

    @PrimaryKey(autoGenerate = true)
    var noteId: Int,
    var noteTitle: String,
    var noteDescription: String,
    var noteType: String,
    var createdOn: String


)