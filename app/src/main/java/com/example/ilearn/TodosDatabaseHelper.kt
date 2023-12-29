package com.example.ilearn

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class TodosDatabaseHelper  (context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "Todos_db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "Todos"
        private const val COLUMN_ID = "id"
        private const val COLUMN_TITLE = "title"
        private const val COLUMN_DESCRIPTION = "description"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableSQL = "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COLUMN_TITLE TEXT, $COLUMN_DESCRIPTION TEXT)"
        db?.execSQL(createTableSQL)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTableSQL = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTableSQL)
        onCreate(db)
    }

    fun addTodo (todo: Todo) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITLE, todo.title)
            put(COLUMN_DESCRIPTION, todo.description)
        }
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun getAllTodos(): List<Todo> {
        val db = readableDatabase
        val TodosList = mutableListOf<Todo>()
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)

        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
            val description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION))
            val Todo = Todo(
                id = id,
                title = title,
                description = description
            )
            TodosList.add(Todo)
        }
        cursor.close()
        db.close()
        return TodosList
    }

    fun updateTodo (todo: Todo) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITLE, todo.title)
            put(COLUMN_DESCRIPTION, todo.description)
        }
        db.update(TABLE_NAME, values, "$COLUMN_ID = ?", arrayOf(todo.id.toString()))
        db.close()
    }

    fun getTodoById (id: Int): Todo {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = ?", arrayOf(id.toString()))
        cursor.moveToFirst()

        val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
        val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
        val description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION))
        val todo = Todo(
            id,
            title,
            description
        )
        cursor.close()
        db.close()
        return todo
    }

    fun deleteTodoById (id: Int) {
        val db = writableDatabase
        db.delete(TABLE_NAME, "$COLUMN_ID = ?", arrayOf(id.toString()))
        db.close()
    }
}