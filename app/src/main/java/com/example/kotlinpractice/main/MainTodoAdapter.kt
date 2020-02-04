package com.example.kotlinpractice.main

import android.app.AlertDialog
import android.content.Context
import android.text.Layout
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinpractice.R
import com.example.kotlinpractice.room.database.MyDatabase
import com.example.kotlinpractice.room.entity.TodoItem

class MainTodoAdapter (context : Context) : RecyclerView.Adapter<MainTodoViewHolder>(){
    private var items : MutableList<TodoItem> = mutableListOf()
    private val myDatabase = MyDatabase.getInstance(context)
    init {
        val itemList = myDatabase?.todoDao()?.getAllTodo()?.also {
            items.addAll(it)
        }
        notifyDataSetChanged()
    }

    fun addItem(item : TodoItem){
        items.add(item)
        myDatabase?.todoDao()?.insertTodo(item)
        notifyDataSetChanged()
    }

    fun deleteItem(item : TodoItem){
        items.remove(item)
        myDatabase?.todoDao()?.deleteTodo(item)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainTodoViewHolder {
        val viewHolder : MainTodoViewHolder = MainTodoViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_todo, parent, false))
        viewHolder.itemView.setOnClickListener{
            items[viewHolder.adapterPosition].checked = !items[viewHolder.adapterPosition].checked
            myDatabase?.todoDao()?.updateTodo(items[viewHolder.adapterPosition])
            notifyDataSetChanged()
        }
        viewHolder.itemView.setOnLongClickListener{
            val builder : AlertDialog.Builder = AlertDialog.Builder(parent.context)
            val menu : Array<String> = arrayOf("삭제", "수정", "취소")
            builder.setTitle(items[viewHolder.adapterPosition].name)
            builder.setItems(menu) { dialog, which ->
                when(menu[which]) {
                    "삭제" -> deleteItem(items[viewHolder.adapterPosition])
                    "수정" -> {} // TODO
                    "취소" -> { }
                    else -> {
                        Log.d("SetOnLongClickListener", "Item position error")
                    }
                }
            }
            builder.show()
            false
        }
        return viewHolder
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MainTodoViewHolder, position: Int) {
        holder.onBind(items[position])
    }

}