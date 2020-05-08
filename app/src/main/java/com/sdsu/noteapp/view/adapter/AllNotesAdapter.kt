package com.sdsu.noteapp.view.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.sdsu.noteapp.R
import com.sdsu.noteapp.data.model.AllNotesModel
import com.google.android.material.snackbar.Snackbar
import com.sdsu.noteapp.data.db.async.DeleteTask
import com.sdsu.noteapp.data.db.async.InsertTask
import com.sdsu.noteapp.viewmodel.NotesViewModel

class AllNotesAdapter(var allNotes: List<AllNotesModel>) :
    RecyclerView.Adapter<AllNotesAdapter.AllNotesHolder>() {

    private var removedPosition: Int = 0
    private lateinit var removedNote: AllNotesModel
    private lateinit var context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllNotesHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.adapter_all_type_notes, parent, false)
        context = parent.context
        return AllNotesHolder(view)
    }

    override fun getItemCount(): Int {
        Log.e("ADAPTER", allNotes.size.toString())
        return allNotes.size
    }

    override fun onBindViewHolder(holder: AllNotesHolder, position: Int) {
        val note = allNotes[position]
        Log.e("ADAPTER", "Adapter called")
        holder.bind(note)
    }

    fun removeItem(
        position: Int,
        viewHolder: RecyclerView.ViewHolder,
        viewModel: NotesViewModel
    ) {
        removedNote = allNotes[position]
        removedPosition = position
        DeleteTask(context, viewModel, removedNote).execute()
        notifyItemRemoved(position)

        Snackbar.make(viewHolder.itemView, "$removedNote removed", Snackbar.LENGTH_LONG).setAction("UNDO") {
            //allNotes.toMutableList().add(removedPosition, removedNote)
            //viewModel.addNoteVm(removedNote)
            InsertTask(context, viewModel, removedNote).execute()
            notifyItemInserted(removedPosition)
        }.show()
    }

    inner class AllNotesHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private lateinit var allTypeNotes: AllNotesModel
        private val noteTitle: TextView = itemView.findViewById<TextView>(R.id.rvNoteTitle)
        private val noteDescription: TextView =
            itemView.findViewById<TextView>(R.id.rvNoteDescription)
        private val createdOn: TextView = itemView.findViewById<TextView>(R.id.rvCreatedOn)

        init {
            itemView.setOnClickListener {
                Toast.makeText(itemView.context, "Note Single clicked!", Toast.LENGTH_SHORT)
                    .show()
            }

            itemView.setOnLongClickListener {
                Toast.makeText(itemView?.context, "Note Long Press clicked!", Toast.LENGTH_SHORT)
                    .show()
                true
            }
        }

        fun bind(notesModel: AllNotesModel) {
            this.allTypeNotes = notesModel
            this.noteTitle.text = this.allTypeNotes.noteTitle
            this.noteDescription.text = this.allTypeNotes.noteDescription
            this.createdOn.text = this.allTypeNotes.createdOn
        }
    }
}