package com.example.todov5

import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Build
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todov5.databinding.ItemTodoBinding

// Adapter for displaying a list of ToDoItem in a RecyclerView
class ToDoAdapter(
    private var items: List<ToDoItem>,
    private val onCheckedChange: (ToDoItem, Boolean) -> Unit
) : RecyclerView.Adapter<ToDoAdapter.ToDoViewHolder>() {

    // ViewHolder holds the binding for each item view
    class ToDoViewHolder(val binding: ItemTodoBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoViewHolder {
        val binding = ItemTodoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ToDoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ToDoViewHolder, position: Int) {
        val item = items[position]
        holder.binding.textTitle.text = item.title
        holder.binding.textDescription.text = item.description ?: ""
        holder.binding.checkboxDone.isChecked = item.isDone
        // Priority label and background
        when (item.priority) {
            Priority.HIGH -> {
                holder.binding.textPriority.text = "High Priority"
                holder.binding.textPriority.setTextColor(Color.parseColor("#B71C1C")) // Deep red
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    holder.binding.priorityBg.background.colorFilter = BlendModeColorFilter(Color.parseColor("#80FF4444"), BlendMode.SRC_IN)
                } else {
                    @Suppress("DEPRECATION")
                    holder.binding.priorityBg.background.setColorFilter(Color.parseColor("#80FF4444"), PorterDuff.Mode.SRC_IN)
                }
            }
            Priority.MEDIUM -> {
                holder.binding.textPriority.text = "Medium Priority"
                holder.binding.textPriority.setTextColor(Color.parseColor("#FF8800")) // Orange
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    holder.binding.priorityBg.background.colorFilter = BlendModeColorFilter(Color.parseColor("#80FFBB33"), BlendMode.SRC_IN)
                } else {
                    @Suppress("DEPRECATION")
                    holder.binding.priorityBg.background.setColorFilter(Color.parseColor("#80FFBB33"), PorterDuff.Mode.SRC_IN)
                }
            }
            Priority.LOW -> {
                holder.binding.textPriority.text = "Low Priority"
                holder.binding.textPriority.setTextColor(Color.parseColor("#388E3C")) // Green
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    holder.binding.priorityBg.background.colorFilter = BlendModeColorFilter(Color.parseColor("#8099CC00"), BlendMode.SRC_IN)
                } else {
                    @Suppress("DEPRECATION")
                    holder.binding.priorityBg.background.setColorFilter(Color.parseColor("#8099CC00"), PorterDuff.Mode.SRC_IN)
                }
            }
        }
        // Checkbox logic
        holder.binding.checkboxDone.setOnCheckedChangeListener(null)
        holder.binding.checkboxDone.isChecked = item.isDone
        holder.binding.checkboxDone.setOnCheckedChangeListener { _, isChecked ->
            if (item.isDone != isChecked) {
                onCheckedChange(item, isChecked)
            }
        }
    }

    override fun getItemCount(): Int = items.size

    // Update the list of items and refresh the RecyclerView
    fun submitList(newItems: List<ToDoItem>) {
        items = newItems
        notifyDataSetChanged()
    }
}
