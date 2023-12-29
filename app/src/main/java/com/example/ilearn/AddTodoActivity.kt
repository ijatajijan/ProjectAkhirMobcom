package com.example.ilearn

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import com.example.ilearn.databinding.ActivityAddTodoBinding

class AddTodoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddTodoBinding
    private lateinit var db: TodosDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTodoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = TodosDatabaseHelper(this)

        binding.saveButton.setOnClickListener {
            val title = binding.todoTitle.text.toString()
            val description = binding.todoDescription.text.toString()
            val todo = Todo(
                id = 0,
                title = title,
                description = description
            )
            db.addTodo(todo)
            finish()
            Toast.makeText(this, "Todo saved", Toast.LENGTH_SHORT).show()
        }

    }
}