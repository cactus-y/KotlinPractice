package com.example.kotlinpractice.add_edit

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.kotlinpractice.R
import com.example.kotlinpractice.room.database.MyDatabase
import com.example.kotlinpractice.room.entity.TodoItem
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_add_edit_todo.*
import java.util.*

class AddEditTodoActivity : AppCompatActivity() {
    companion object{
        val MODE_ADD = 0
        val MODE_EDIT = 1
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_save, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_todo)
        val mode = intent.getIntExtra("mode", -1)
        actionBar?.title = if(mode == MODE_EDIT) "EditTodo" else "AddTodo"
        actionBar?.setDisplayHomeAsUpEnabled(true)
        if(mode == MODE_EDIT){
            val id = intent.getIntExtra("item_id", -1).also {
                if(it == -1) {
                    Log.d("item_id", "item id wrong")
                Toast.makeText(this, "item id wrong", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
            val myDatabase = MyDatabase.getInstance(this)
            myDatabase?.todoDao()?.getTodo(id)?.also {
                add_edit_til_todo.editText?.setText(it.name)
                add_edit_til_start_date.editText?.setText(it.sDate)
                add_edit_til_due_date.editText?.setText(it.dDate)
                add_edit_til_memo.editText?.setText(it.memo)
            }

        }
        add_edit_til_start_date.editText?.setOnClickListener {
            showCalendar(add_edit_til_start_date)
        }
        add_edit_til_due_date.editText?.setOnClickListener {
            showCalendar(add_edit_til_due_date)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.menu_save -> {
                val todoName = add_edit_til_todo.editText?.text.toString()
                val todoStart = add_edit_til_start_date.editText?.text.toString()
                val todoDue = add_edit_til_due_date.editText?.text.toString()
                val todoMemo = add_edit_til_memo.editText?.text.toString()
                if(!checkIsEmpty(add_edit_til_todo) && !checkIsEmpty(add_edit_til_start_date) && !checkIsEmpty(add_edit_til_due_date)){
                    if(todoStart > todoDue){
                        add_edit_til_start_date.error = "시작 날짜가 더 느려요!"
                        add_edit_til_due_date.error = "끝나는 날짜가 더 빨라요!"
                    } else {
                        val newTodo = TodoItem(0, todoName, todoStart, todoDue, todoMemo)
                        val database = MyDatabase.getInstance(this)
                        database?.todoDao()?.insertTodo(newTodo)
                        finish()
                    }
                }
            }
            android.R.id.home -> {
                finish()
            }
            else -> {}
        }
        return super.onOptionsItemSelected(item)
    }
    private fun checkIsEmpty(til : TextInputLayout) : Boolean{
        return if(til.editText?.text.toString().trim() == ""){
            til.isErrorEnabled = true
            til.error = "Write Anything"
            true
        } else {
            til.error = null
            false
        }
    }
    private fun showCalendar(til : TextInputLayout){
        val cal = Calendar.getInstance()
        val mYear = cal.get(Calendar.YEAR)
        val mMonth = cal.get(Calendar.MONTH)
        val mDay = cal.get(Calendar.DAY_OF_MONTH)
        DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            val sMonth : String = if (month + 1 < 10) {
                "0$month"
            } else {
                month.toString()
            }
            val sDate : String = if(dayOfMonth < 10){
                "0$dayOfMonth"
            } else {
                dayOfMonth.toString()
            }
            val date : String = "${year}/${sMonth}/${sDate}"
            til.editText?.setText(date)
        }, mYear, mMonth, mDay).show()
    }
}
