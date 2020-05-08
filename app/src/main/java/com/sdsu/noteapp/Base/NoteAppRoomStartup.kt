package com.sdsu.noteapp.Base

import android.app.Application
import androidx.room.Room
import com.sdsu.noteapp.data.db.NotesAppDatabase

class NoteAppRoomStartup : Application() {

    companion object {
        var database: NotesAppDatabase? = null
    }
    override fun onCreate() {
        super.onCreate()
        database =
            Room.databaseBuilder(applicationContext, NotesAppDatabase::class.java, "notes_db").fallbackToDestructiveMigration().build()
    }
}