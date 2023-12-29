package com.example.ilearn

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.ilearn.databinding.ActivityUpdateTodoBinding

class UpdateTodoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateTodoBinding
    private lateinit var todosDatabaseHelper: TodosDatabaseHelper
    private var todoId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateTodoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        todosDatabaseHelper = TodosDatabaseHelper(this)

        todoId = intent.getIntExtra("todo_id", -1)
        if (todoId == -1) {
            finish()
            return
        }

        val todo = todosDatabaseHelper.getTodoById(todoId)
        binding.todoTitle.setText(todo.title)
        binding.todoDescription.setText(todo.description)

        binding.updateSaveButton.setOnClickListener {
            val title = binding.todoTitle.text.toString()
            val description = binding.todoDescription.text.toString()
            val todo = Todo(
                id = todoId,
                title = title,
                description = description
            )
            todosDatabaseHelper.updateTodo(todo)
            finish()
            Toast.makeText(this, "Todo updated", Toast.LENGTH_SHORT).show()
        }
    }
}
