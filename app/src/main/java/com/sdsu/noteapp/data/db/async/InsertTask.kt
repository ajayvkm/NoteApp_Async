package com.sdsu.noteapp.data.db.async

import android.content.Context
import android.os.AsyncTask
import android.widget.Toast
import com.sdsu.noteapp.data.model.AllNotesModel
import com.sdsu.noteapp.viewmodel.NotesViewModel

class InsertTask(var context: Context?, var viewModel: NotesViewModel, var allNotesModel: AllNotesModel) : AsyncTask<Void, Void, Boolean>() {
    override fun doInBackground(vararg params: Void?): Boolean {
        viewModel.addNoteVm(allNotesModel)
        return true
    }
    override fun onPostExecute(bool: Boolean?) {
        if (bool!!) {
            Toast.makeText(context, "Added to Database", Toast.LENGTH_LONG).show()
        }
    }
}