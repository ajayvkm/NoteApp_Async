package com.sdsu.noteapp.view.fragment

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.sdsu.noteapp.R
import com.sdsu.noteapp.Util.NoteUtil
import com.sdsu.noteapp.data.db.async.InsertTask
import com.sdsu.noteapp.data.model.AllNotesModel
import com.sdsu.noteapp.viewmodel.NotesViewModel
import kotlinx.android.synthetic.main.add_note_fragment.*
import java.text.SimpleDateFormat
import java.util.*


class AddNoteFragment : Fragment() {

    companion object {
        fun newInstance() =
            AddNoteFragment()
    }

    var cal = Calendar.getInstance()
    private lateinit var viewModel: NotesViewModel
    lateinit var dateTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_note_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView(view)
        initViewModel()
        initListner()
        observeAddNoteViewModel()

    }

    private fun setUpView(view: View) {
        dateTextView = view.findViewById(R.id.datePicker)
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(NotesViewModel::class.java)
    }

    private fun observeAddNoteViewModel() {

    }

    private fun initListner() {
        datePicker.setOnClickListener { v ->
            when (v?.id) {
                R.id.datePicker -> openDatePicker(v)
            }
        }

        addNote.setOnClickListener {
            saveNote()
            closeCurrentFragment()
        }
    }

    private fun closeCurrentFragment() {
        fragmentManager?.beginTransaction()?.remove(this)?.commit()
    }

    private fun saveNote() {
        var notesModel =
            AllNotesModel(
                0,
                noteTitle.text.toString(),
                noteDescription.text.toString(),
                NoteUtil.NOTE,
                dateTextView.text.toString()
            )
        //viewModel.addNoteVm(notesModel)
        InsertTask(this.context, viewModel, notesModel).execute()
    }

    private fun openDatePicker(v: View?) {

        val dateSetListener =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView()
            }
        DatePickerDialog(
            v?.context!!,
            dateSetListener,
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun updateDateInView() {
        val myFormat = "MM/dd/yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        val format = sdf.format(cal.time)
        dateTextView.text = format
    }

}





