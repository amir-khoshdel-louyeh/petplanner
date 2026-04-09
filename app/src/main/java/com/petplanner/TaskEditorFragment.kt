package com.petplanner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment

class TaskEditorFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_task_editor, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val titleInput = view.findViewById<EditText>(R.id.taskTitleInput)
        val notesInput = view.findViewById<EditText>(R.id.taskNotesInput)
        val saveButton = view.findViewById<Button>(R.id.saveTaskButton)

        saveButton.setOnClickListener {
            val title = titleInput.text.toString().trim()
            if (title.isEmpty()) {
                Toast.makeText(requireContext(), "Please enter a task title.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val task = Task(
                id = "task-${System.currentTimeMillis()}",
                title = title,
                completed = false
            )
            LocalDataRepository.addTask(task)
            Toast.makeText(requireContext(), "Task saved: $title", Toast.LENGTH_SHORT).show()
            parentFragmentManager.popBackStack()
        }
    }
}
