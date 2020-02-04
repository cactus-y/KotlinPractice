package com.example.kotlinpractice.main

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinpractice.room.entity.TodoItem
import kotlinx.android.synthetic.main.item_todo.view.*

class MainTodoViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView){
    fun onBind(item : TodoItem){
        itemView.todo_cb.isChecked = item.checked
        itemView.todo_tv_name.text = item.name
    }
}