package com.example.todo.presentation.main_activity

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.R
import com.example.todo.databinding.ActivityMainBinding
import com.example.todo.presentation.adapter.ToDoAdapter
import com.example.todo.presentation.add_item_package.AddToDoActivity
import com.example.todo.presentation.add_item_package.AddToDoItemFragment

class MainActivity : AppCompatActivity(), AddToDoItemFragment.OnEditingFinishedListener {

    private lateinit var viewModel: MainViewModel
    private lateinit var toDoListAdapter: ToDoAdapter
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupRV()

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.itemList.observe(this) {
            toDoListAdapter.submitList(it)
        }

        binding.floatingActionButton.setOnClickListener{
            if(isOnePaneMode()) {
                val intent = AddToDoActivity.newIntentAddItem(this)
                startActivity(intent)
            } else {
                launchFragment(AddToDoItemFragment.newInstanceAddItem())
            }
        }
    }

    private fun isOnePaneMode(): Boolean {
        return binding.fragmentContainer == null
    }

    private fun launchFragment(fragment: Fragment) {
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun setupRV(){
        val recyclerView = binding.rv
        if (binding.rv == null) {
            Log.d("MATag", "rv is null")
        } else {
            with(recyclerView!!) {
                toDoListAdapter = ToDoAdapter()
                adapter = toDoListAdapter
                recycledViewPool.setMaxRecycledViews(
                    ToDoAdapter.VIEW_TYPE_ENABLED,
                    ToDoAdapter.MAX_POOl_SIZE
                )
                recycledViewPool.setMaxRecycledViews(
                    ToDoAdapter.VIEW_TYPE_DISABLED,
                    ToDoAdapter.MAX_POOl_SIZE
                )
            }
            setupClickListener()
            setupLongClickListener()
            setupSwipeListener(binding.rv!!)
        }
    }
    private fun setupSwipeListener(rv: RecyclerView){
        val callback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = toDoListAdapter.currentList[viewHolder.adapterPosition]
                viewModel.deleteToDoItem(item.id)
            }
        }
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(rv)
    }
    private fun setupClickListener(){
        toDoListAdapter.onItemClickListener = {
            if(isOnePaneMode()) {
                val intent = AddToDoActivity.newIntentEditItem(this, it.id)
                startActivity(intent)
            } else {
                launchFragment(AddToDoItemFragment.newInstanceEditItem(it.id))
            }
        }
    }
    private fun setupLongClickListener(){
        toDoListAdapter.onItemLongClickListener = {
            viewModel.changeEnableState(it)
        }
    }

    override fun onEditingFinished() {
        Toast.makeText(this@MainActivity, "Success", Toast.LENGTH_LONG).show()
        supportFragmentManager.popBackStack()
    }

}