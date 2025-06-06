package com.example.todov5

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.todov5.databinding.FragmentAddTodoBinding

class AddToDoFragment : Fragment() {
    private var _binding: FragmentAddTodoBinding? = null
    private val binding get() = _binding!!

    // Use activityViewModels to share ViewModel between fragments
    private val viewModel: ToDoViewModel by lazy {
        ViewModelProvider(requireActivity(), ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application))[ToDoViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddTodoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set up Priority spinner with capitalized labels (High, Medium, Low)
        val priorities = Priority.values().map { it.name.lowercase().replaceFirstChar { c -> c.uppercase() } }
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, priorities)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerPriority.adapter = adapter

        // Set Save button to darker purple and only enable if title is not empty
        binding.buttonSaveTask.setBackgroundColor(0xFF7E57C2.toInt())
        binding.editTaskTitle.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                binding.buttonSaveTask.isEnabled = !s.isNullOrBlank()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        // Save button click
        binding.buttonSaveTask.setOnClickListener {
            val title = binding.editTaskTitle.text.toString()
            val description = binding.editTaskDescription.text.toString().takeIf { it.isNotBlank() }
            val priority = Priority.values()[binding.spinnerPriority.selectedItemPosition]
            val newTask = ToDoItem(
                id = (viewModel.tasks.value?.maxOfOrNull { it.id } ?: 0) + 1,
                title = title,
                description = description,
                priority = priority,
                isDone = false
            )
            viewModel.addTask(newTask)
            findNavController().navigate(R.id.action_AddToDoFragment_to_ToDoListFragment)
        }

        // Remove buttonBackToList logic since the FAB/app bar handles navigation
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
