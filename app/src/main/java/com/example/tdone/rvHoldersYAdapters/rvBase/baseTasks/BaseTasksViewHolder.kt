package com.example.tdone.rvHoldersYAdapters.rvBase.baseTasks

import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.tdone.databinding.ItemTaskBinding
import com.example.tdone.dataclasses.TaskDataClass
import com.example.tdone.rvHoldersYAdapters.rvTags.TagsAdapter
class BaseTasksViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemTaskBinding.bind(view)

    fun bind(task: TaskDataClass, nav: (TaskDataClass) -> Unit) {
        binding.tvTaskTittle.text =task.name
        if (task.group != null) {
            binding.tvTaskGroup.text = task.group!!.name
        }
        binding.tvTaskEndDate.text = "12/12/2023"

        when (task.tag_DataClasses.size) {
            0 -> {
                binding.tag1.root.visibility = View.GONE
                binding.tag2.root.visibility = View.GONE
                binding.tag3.root.visibility = View.GONE
            }

            1 -> {
                binding.tag1.cvTag.setCardBackgroundColor(
                    ContextCompat.getColor(
                        binding.tag1.root.context,
                        task.tag_DataClasses[0].color
                    )
                )
                binding.tag2.root.visibility = View.GONE
                binding.tag3.root.visibility = View.GONE
            }

            2 -> {
                binding.tag1.cvTag.setCardBackgroundColor(
                    ContextCompat.getColor(
                        binding.tag1.root.context,
                        task.tag_DataClasses[0].color
                    )
                )
                binding.tag2.cvTag.setCardBackgroundColor(
                    ContextCompat.getColor(
                        binding.tag2.root.context,
                        task.tag_DataClasses[1].color
                    )
                )
                binding.tag3.root.visibility = View.GONE
            }

            else -> {
                binding.tag1.cvTag.setCardBackgroundColor(
                    ContextCompat.getColor(
                        binding.tag1.root.context,
                        task.tag_DataClasses[0].color
                    )
                )
                binding.tag2.cvTag.setCardBackgroundColor(
                    ContextCompat.getColor(
                        binding.tag2.root.context,
                        task.tag_DataClasses[1].color
                    )
                )
                binding.tag3.cvTag.setCardBackgroundColor(
                    ContextCompat.getColor(
                        binding.tag3.root.context,
                        task.tag_DataClasses[2].color
                    )
                )
            }
        }

        binding.cbTask.isChecked = task.checked
        binding.root.setOnClickListener {
            nav(task)
        }
    }
}