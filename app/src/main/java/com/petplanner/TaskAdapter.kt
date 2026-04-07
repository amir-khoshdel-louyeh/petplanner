package com.petplanner

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView

class TaskAdapter(
    tasks: List<Task>,
    private val onTaskToggle: (Task) -> Unit
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    private val taskList = tasks.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(taskList[position])
    }

    override fun getItemCount(): Int = taskList.size

    fun addTask(task: Task) {
        taskList.add(task)
        notifyItemInserted(taskList.size - 1)
    }

    fun toggleTaskCompleted(position: Int) {
        if (position < 0 || position >= taskList.size) return
        val task = taskList[position]
        val updated = task.copy(completed = !task.completed)
        taskList[position] = updated
        notifyItemChanged(position)
        onTaskToggle(updated)
    }

    fun updateTask(updated: Task) {
        val index = taskList.indexOfFirst { it.id == updated.id }
        if (index == -1) return
        taskList[index] = updated
        notifyItemChanged(index)
    }

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val taskCheckBox: CheckBox = itemView.findViewById(R.id.taskCheckBox)

        fun bind(task: Task) {
            taskCheckBox.setOnCheckedChangeListener(null)
            taskCheckBox.text = task.title
            taskCheckBox.isChecked = task.completed

            taskCheckBox.setOnCheckedChangeListener { _, isChecked ->
                val position = bindingAdapterPosition
                if (position == RecyclerView.NO_POSITION) return@setOnCheckedChangeListener
                val updatedTask = taskList[position].copy(completed = isChecked)
                taskList[position] = updatedTask
                notifyItemChanged(position)
                onTaskToggle(updatedTask)
            }

            itemView.setOnClickListener {
                val position = bindingAdapterPosition
                if (position == RecyclerView.NO_POSITION) return@setOnClickListener
                val updatedTask = taskList[position].copy(completed = !taskList[position].completed)
                taskList[position] = updatedTask
                notifyItemChanged(position)
                onTaskToggle(updatedTask)
            }
        }
    }
}
