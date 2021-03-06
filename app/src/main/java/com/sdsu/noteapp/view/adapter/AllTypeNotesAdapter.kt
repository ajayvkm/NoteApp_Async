package com.sdsu.noteapp.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.sdsu.noteapp.R
import com.sdsu.noteapp.data.model.AllNotesModel

class AllTypeNotesAdapter(var allNotes: List<AllNotesModel>) :
    RecyclerView.Adapter<AllTypeNotesAdapter.AllTypesNotesHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllTypesNotesHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.adapter_all_type_notes, parent, false)
        return AllTypesNotesHolder(view)
    }

    override fun getItemCount(): Int {
        Log.e("ADAPTER", allNotes.size.toString())
        return allNotes.size
    }

    override fun onBindViewHolder(holder: AllTypesNotesHolder, position: Int) {
        val note = allNotes[position]
        Log.e("ADAPTER", "Adapter called")
        holder.bind(note)
    }

    inner class AllTypesNotesHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private lateinit var allTypeNotes: AllNotesModel
        private val noteTitle: TextView = itemView.findViewById<TextView>(R.id.rvNoteTitle)
        private val noteDescription: TextView = itemView.findViewById<TextView>(R.id.rvNoteDescription)
        private val createdOn: TextView = itemView.findViewById<TextView>(R.id.rvCreatedOn)

        init {
            itemView.setOnClickListener {
                Toast.makeText(itemView.context, "All Type Single clicked!", Toast.LENGTH_SHORT)
                    .show()
            }

            itemView.setOnLongClickListener {
                Toast.makeText(itemView?.context, "All Type Long Press clicked!", Toast.LENGTH_SHORT)
                    .show()
                true
            }

            /*itemView.setOnDragListener {
                Toast.makeText(itemView?.context, "${allTypeNotes.noteTitle} Dragged !", Toast.LENGTH_SHORT)
                    .show()
                true
            }*/
        }
        fun bind(allTypeNotesModel: AllNotesModel) {
            this.allTypeNotes = allTypeNotesModel
            this.noteTitle.text = this.allTypeNotes.noteTitle
            this.noteDescription.text = this.allTypeNotes.noteDescription
            this.createdOn.text = this.allTypeNotes.createdOn
        }
    }
}




