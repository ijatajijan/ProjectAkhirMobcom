package com.example.ilearn

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class TodosAdapter(private var Todos: List<Todo>, context: Context) : RecyclerView.Adapter<TodosAdapter.TodoViewHolder>() {

    private val db: TodosDatabaseHelper = TodosDatabaseHelper(context)

    class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.todoTitle)
        val descriptionTextView: TextView = itemView.findViewById(R.id.todoDescription)
        val updateButton: ImageView = itemView.findViewById(R.id.editButton)
        val deleteButton: ImageView = itemView.findViewById(R.id.deleteButton)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.todo_item, parent, false)
        return TodoViewHolder(view)
    }

    override fun getItemCount(): Int = Todos.size

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val todo = Todos[position]
        holder.titleTextView.text = todo.title
        holder.descriptionTextView.text = todo.description

        holder.updateButton.setOnClickListener {
            val intent = Intent(holder.itemView.context, UpdateTodoActivity::class.java).apply {
                putExtra("todo_id", todo.id)
            }
            holder.itemView.context.startActivity(intent)
        }

        holder.deleteButton.setOnClickListener {
            db.deleteTodoById(todo.id)
            refreshTodos(db.getAllTodos())
            Toast.makeText(holder.itemView.context, "Todo deleted", Toast.LENGTH_SHORT).show()
        }
    }

    fun refreshTodos(newTodos: List<Todo>) {
        Todos = newTodos
        notifyDataSetChanged()
    }

}