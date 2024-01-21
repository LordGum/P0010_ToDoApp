package com.example.todo.presentation.add_item_package

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.todo.R
import com.example.todo.databinding.FragmentAddToDoItemBinding
import com.example.todo.domain.entities.ToDoItem
import com.example.todo.presentation.ToDoApplication
import com.example.todo.presentation.ViewModelFactory
import javax.inject.Inject

class AddToDoItemFragment : Fragment() {

    private lateinit var viewModel: AddItemViewModel
    private lateinit var onEditingFinishedListener: OnEditingFinishedListener

    private var _binding: FragmentAddToDoItemBinding? = null
    private val binding: FragmentAddToDoItemBinding
        get() = _binding ?: throw RuntimeException("FragmentAddToDoItemBinding is null")

    private var screenMode: String = MODE_UNKNOWN
    private var itemId: Int = ToDoItem.UNDEFINED_ID

    @Inject
    lateinit var  viewModelFactory: ViewModelFactory

    private val component by lazy {
        (requireActivity().application as ToDoApplication).component
    }

    override fun onAttach(activity: Activity) {
        component.inject(this)
        super.onAttach(activity)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseParams()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddToDoItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory)[AddItemViewModel::class.java]
        launchRightMode()
        observeViewModel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun launchRightMode() {
        when(screenMode) {
            MODE_ADD -> launchAddMode()
            MODE_EDIT -> launchEditMode()
            else -> throw RuntimeException("Unknown mode: $screenMode")
        }
    }

    private fun launchEditMode() {
        viewModel.getToDoItem(itemId)
        viewModel.item.observe(viewLifecycleOwner) {
            binding.etTask.setText(it.name)
            binding.etDeadline.setText(it.dataCreation)
            binding.spinnerPriority.setSelection(it.priority)
            binding.etDescription.setText(it.description)
            binding.saveButton.setText(R.string.save_task)
        }
        binding.saveButton.setOnClickListener {
            viewModel.editToDoItem(
                binding.etTask.text?.toString(),
                binding.etDescription.text?.toString(),
                binding.etDeadline.text?.toString(),
                binding.spinnerPriority.selectedItemPosition
            )
        }
    }
    private fun launchAddMode() {
        binding.saveButton.setOnClickListener {
            viewModel.addToDoItem(
                binding.etTask.text?.toString(),
                binding.etDescription.text?.toString(),
                binding.etDeadline.text?.toString(),
                binding.spinnerPriority.selectedItemPosition
            )
            Log.d("MATag", "add priority = ${binding.spinnerPriority.selectedItemPosition}")
        }
    }

    private fun parseParams() {
        val args = requireArguments()
        if(!args.containsKey(SCREEN_MODE)) throw RuntimeException("Param screen mode is absent")
        val mode = args.getString(SCREEN_MODE)
        if (mode!= MODE_EDIT && mode!= MODE_ADD) throw RuntimeException("Unknown screen mode: $mode")
        screenMode = mode
        if (screenMode == MODE_EDIT) {
            if(!args.containsKey(ITEM_ID)) throw RuntimeException("Param item id is absent")
            itemId = args.getInt(ITEM_ID, ToDoItem.UNDEFINED_ID)
        }
    }

    interface OnEditingFinishedListener {
        fun onEditingFinished()
    }

    private fun observeViewModel() {
        viewModel.shouldCloseScreen.observe(viewLifecycleOwner) {
            onEditingFinishedListener.onEditingFinished()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnEditingFinishedListener) {
            onEditingFinishedListener = context
        } else {
            throw RuntimeException("Activity must implement OnEditingFinishedListener")
        }
    }

    companion object{
        private const val SCREEN_MODE = "extra_mode"
        private const val ITEM_ID = "extra_item_id"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"
        private const val MODE_UNKNOWN = "mode_unknown"

        fun newInstanceAddItem(): AddToDoItemFragment {
            return AddToDoItemFragment().apply {
                arguments = Bundle().apply {
                    putString(SCREEN_MODE, MODE_ADD)
                }
            }
        }

        fun newInstanceEditItem(itemId: Int): AddToDoItemFragment {
            return AddToDoItemFragment().apply {
                arguments = Bundle().apply {
                    putString(SCREEN_MODE, MODE_EDIT)
                    putInt(ITEM_ID, itemId)
                }
            }
        }
    }
}