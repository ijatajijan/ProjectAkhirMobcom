package com.example.ilearn

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ilearn.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var db: TodosDatabaseHelper
    private lateinit var TodosAdapter: TodosAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        db = TodosDatabaseHelper(this)
        TodosAdapter= TodosAdapter(db.getAllTodos(), this)

        binding.itemList.layoutManager = LinearLayoutManager(this)
        binding.itemList.adapter= TodosAdapter

        binding.addButton.setOnClickListener{
            val intent = Intent(this, AddTodoActivity::class.java)
            startActivity(intent)
        }




    }

    override fun onResume(){
        super.onResume()
        TodosAdapter.refreshTodos(db.getAllTodos())

    }
}