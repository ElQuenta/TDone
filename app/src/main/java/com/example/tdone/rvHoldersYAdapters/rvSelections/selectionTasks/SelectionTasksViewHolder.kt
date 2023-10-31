package com.example.tdone.rvHoldersYAdapters.rvSelections.selectionTasks

import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.tdone.R
import com.example.tdone.databinding.ItemTaskBinding
import com.example.tdone.dataclasses.TaskDataClass

class SelectionTasksViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemTaskBinding.bind(view)

    fun bind(task: TaskDataClass) {
        binding.root.setCardBackgroundColor(
            if (task.hasVinculation) {
                ContextCompat.getColor(binding.root.context, R.color.white)
            } else {
                ContextCompat.getColor(binding.root.context, R.color.colorGris)
            }
        )
        binding.tvTaskTittle.text = task.taskName
        binding.tvTaskGroup.text = task.taskGroup?.groupName ?: ""
        binding.tvTaskEndDate.text = "12/12/2023"
        when (task.taskTags.size) {
            0 -> {
                binding.tag1.root.visibility = View.GONE
                binding.tag2.root.visibility = View.GONE
                binding.tag3.root.visibility = View.GONE
            }

            1 -> {
                binding.tag1.cvTag.setCardBackgroundColor(
                    ContextCompat.getColor(
                        binding.tag1.root.context,
                        task.taskTags[0].tagColor
                    )
                )
                binding.tag2.root.visibility = View.GONE
                binding.tag3.root.visibility = View.GONE
            }

            2 -> {
                binding.tag1.cvTag.setCardBackgroundColor(
                    ContextCompat.getColor(
                        binding.tag1.root.context,
                        task.taskTags[0].tagColor
                    )
                )
                binding.tag2.cvTag.setCardBackgroundColor(
                    ContextCompat.getColor(
                        binding.tag2.root.context,
                        task.taskTags[1].tagColor
                    )
                )
                binding.tag3.root.visibility = View.GONE
            }

            else -> {
                binding.tag1.cvTag.setCardBackgroundColor(
                    ContextCompat.getColor(
                        binding.tag1.root.context,
                        task.taskTags[0].tagColor
                    )
                )
                binding.tag2.cvTag.setCardBackgroundColor(
                    ContextCompat.getColor(
                        binding.tag2.root.context,
                        task.taskTags[1].tagColor
                    )
                )
                binding.tag3.cvTag.setCardBackgroundColor(
                    ContextCompat.getColor(
                        binding.tag3.root.context,
                        task.taskTags[2].tagColor
                    )
                )
            }
        }
        binding.cbTask.visibility = View.INVISIBLE
        binding.cbTask.layoutParams.width = 0
        binding.cbTask.isClickable = false
    }
}