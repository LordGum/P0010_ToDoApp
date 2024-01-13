package com.example.todo.presentation.add_item_package

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.todo.R
import com.example.todo.domain.entities.ToDoItem

class AddToDoActivity : AppCompatActivity(), AddToDoItemFragment.OnEditingFinishedListener {
    private var screedMode = MODE_UNKNOWN
    private var itemId = ToDoItem.UNDEFINED_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_to_do)

        parseIntent()
        if (savedInstanceState == null) {
            launchRightMode()
        }
    }

    private fun launchRightMode() {
        val fragment = when(screedMode) {
            MODE_EDIT -> AddToDoItemFragment.newInstanceEditItem(itemId)
            MODE_ADD -> AddToDoItemFragment.newInstanceAddItem()
            else -> throw RuntimeException("Unknown screen mode: $screedMode")
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    private fun parseIntent() {
        if(!intent.hasExtra(EXTRA_SCREEN_MODE)) {
            throw RuntimeException("Param screen mode is absent")
        }
        val mode = intent.getStringExtra(EXTRA_SCREEN_MODE)
        if (mode!= MODE_ADD && mode!= MODE_EDIT) {
            throw RuntimeException("unknown screen mode: $mode")
        }
        screedMode = mode
        if (screedMode == MODE_EDIT) {
            if (!intent.hasExtra(EXTRA_ITEM_ID)) throw RuntimeException("Param item id is absent")
            itemId = intent.getIntExtra(EXTRA_ITEM_ID, ToDoItem.UNDEFINED_ID)
        }
    }

    override fun onEditingFinished() {
        finish()
    }

    companion object{
        private const val EXTRA_SCREEN_MODE = "extra_mode"
        private const val EXTRA_ITEM_ID = "extra_item_id"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"
        private const val MODE_UNKNOWN = "mode_unknown"

        fun newIntentAddItem(context: Context): Intent {
            val intent = Intent(context, AddToDoActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_ADD)
            return intent
        }

        fun newIntentEditItem(context: Context, itemId: Int): Intent {
            val intent = Intent(context, AddToDoActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_EDIT)
            intent.putExtra(EXTRA_ITEM_ID, itemId)
            return intent
        }
    }
}