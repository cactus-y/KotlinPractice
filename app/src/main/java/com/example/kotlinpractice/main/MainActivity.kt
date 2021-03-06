package com.example.kotlinpractice.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinpractice.R
import com.example.kotlinpractice.add_edit.AddEditTodoActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var adapter : MainTodoAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = MainTodoAdapter(this)
        widget_rcv_item.adapter = adapter
        widget_rcv_item.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        main_fab_add.setOnClickListener{
            /*val newItem = TodoItem(0, System.currentTimeMillis().toString(), "asd", "fgh")
            adapter?.addItem(newItem)*/
            val addIntent = Intent(this, AddEditTodoActivity::class.java)
            addIntent.putExtra("mode", AddEditTodoActivity.MODE_ADD)
            startActivity(addIntent)
        }
    }

    override fun onRestart() {
        super.onRestart()
        adapter?.refresh()
    }

    override fun onResume() {
        super.onResume()
        adapter?.refresh()
    }
}
