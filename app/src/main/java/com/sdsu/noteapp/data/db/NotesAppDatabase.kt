package com.sdsu.noteapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sdsu.noteapp.data.model.AllNotesModel
import kotlinx.coroutines.CoroutineScope

@Database(entities = [(AllNotesModel::class)], version = 1, exportSchema = false)
abstract class NotesAppDatabase : RoomDatabase() {

    abstract fun notesDao(): NotesDao

    companion object {
        @Volatile
        private var INSTANCE: NotesAppDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): NotesAppDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NotesAppDatabase::class.java,
                    "notes_db"
                )
                    // Wipes and rebuilds instead of migrating if no Migration object.
                    // Migration is not part of this codelab.
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}