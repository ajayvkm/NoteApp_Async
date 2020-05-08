package com.sdsu.noteapp.view.fragment

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sdsu.noteapp.R
import com.sdsu.noteapp.Util.NoteUtil
import com.sdsu.noteapp.data.model.AllNotesModel
import com.sdsu.noteapp.view.adapter.AllNotesAdapter
import com.sdsu.noteapp.viewmodel.NotesViewModel

class AllNotesFragment : Fragment() {
    private var allNotes = mutableListOf<AllNotesModel>()
    private lateinit var colorDrawableBackground: ColorDrawable
    private lateinit var deleteIcon: Drawable

    companion object {
        fun newInstance() =
            AllNotesFragment()
    }

    private lateinit var viewModel: NotesViewModel
    private lateinit var allNotesRecyclerView: RecyclerView
    private var adapter: AllNotesAdapter? = null
    private var notesList = mutableListOf<AllNotesModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.all_notes_fragment, container, false)
        allNotesRecyclerView =
            view.findViewById(R.id.noteAllRecycler) as RecyclerView
        allNotesRecyclerView.layoutManager =
            LinearLayoutManager(this.context)
        allNotesRecyclerView.adapter = adapter

        colorDrawableBackground = ColorDrawable(Color.parseColor("#ccb900"))
        deleteIcon = ContextCompat.getDrawable(this.context!!, R.drawable.ic_list_red_24dp)!!

        // CallBack for Swipe
        val itemTouchHelperCallback = object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                TODO("Not yet implemented")
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDirection: Int) {
                (adapter as AllNotesAdapter).removeItem(
                    viewHolder.adapterPosition,
                    viewHolder,
                    viewModel
                )
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                val itemView = viewHolder.itemView
                val iconMarginVertical =
                    (viewHolder.itemView.height - deleteIcon.intrinsicHeight) / 2

                if (dX > 0) {
                    colorDrawableBackground.setBounds(
                        itemView.left,
                        itemView.top,
                        dX.toInt(),
                        itemView.bottom
                    )
                    deleteIcon.setBounds(
                        itemView.left + iconMarginVertical,
                        itemView.top + iconMarginVertical,
                        itemView.left + iconMarginVertical + deleteIcon.intrinsicWidth,
                        itemView.bottom - iconMarginVertical
                    )
                } else {
                    colorDrawableBackground.setBounds(
                        itemView.right + dX.toInt(),
                        itemView.top,
                        itemView.right,
                        itemView.bottom
                    )
                    deleteIcon.setBounds(
                        itemView.right - iconMarginVertical - deleteIcon.intrinsicWidth,
                        itemView.top + iconMarginVertical,
                        itemView.right - iconMarginVertical,
                        itemView.bottom - iconMarginVertical
                    )
                    deleteIcon.level = 0
                }
                colorDrawableBackground.draw(c)
                c.save()

                if (dX > 0)
                    c.clipRect(itemView.left, itemView.top, dX.toInt(), itemView.bottom)
                else
                    c.clipRect(
                        itemView.right + dX.toInt(),
                        itemView.top,
                        itemView.right,
                        itemView.bottom
                    )

                deleteIcon.draw(c)
                c.restore()
                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
            }

        }

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(allNotesRecyclerView)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(NotesViewModel::class.java)
    }


    override fun onStart() {
        super.onStart()

        viewModel.getAllNotes(NoteUtil.NOTE).observe(
            viewLifecycleOwner,
            Observer { listNotes ->
                listNotes?.let {
                    Log.i("Notes", "Got crimeLiveData ${listNotes.size}")
                    updateUI(listNotes)
                }
            }
        )
        adapter = AllNotesAdapter(allNotes)
        allNotesRecyclerView.adapter = adapter
    }


    private fun updateUI(allNotesRe: List<AllNotesModel>) {
        adapter?.let {
            it.allNotes = allNotesRe
        } ?: run {
            adapter = AllNotesAdapter(allNotes)
        }
        allNotesRecyclerView.adapter = adapter
    }

    /*private class GetDataFromDb(var context: AllNotesFragment, var viewModel: AllNotesViewModel) : AsyncTask<Void, Void, List<AllNotesModel>>() {
        override fun doInBackground(vararg params: Void?): List<AllNotesModel> {
            return viewModel.getAllNotes(NoteUtil.NOTE)
        }
        override fun onPostExecute(allNotes: List<AllNotesModel>?) {
            if (allNotes!!.size > 0) {
                for (i in 0..allNotes.size - 1) {
                    //context.tvDatafromDb.append(chapterList[i].chapterName)
                }
            }
        }
    }*/
}
