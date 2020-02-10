package com.example.kotlinpractice.main

import android.graphics.Paint
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinpractice.room.entity.TodoItem
import kotlinx.android.synthetic.main.item_todo.view.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class MainTodoViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView){
    fun onBind(item : TodoItem){
        val cal = Calendar.getInstance()
        cal.time = Date()
        val df:DateFormat = SimpleDateFormat("yyyy/MM/dd")
        val curDate = df.format(cal.time)
        val dateDiff = dateDif(curDate,item.dDate)

        itemView.todo_cb.isChecked = item.checked
        if(itemView.todo_cb.isChecked){
            itemView.todo_tv_name.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
        } else {
            itemView.todo_tv_name.paintFlags = 0
        }
        itemView.todo_tv_name.text = item.name
        if(itemView.todo_cb.isChecked){
            itemView.todo_tv_dday.text = "Done!"
        } else {
            if(dateDiff == 0){
                itemView.todo_tv_dday.text = "D-Day"
            } else if(dateDiff > 0){
                itemView.todo_tv_dday.text = "D-$dateDiff"
            } else{
                itemView.todo_tv_dday.text = "D+${-dateDiff}"
            }
        }
    }

    private fun dateDif(startDate:String, endDate:String):Int {
        val formatter = SimpleDateFormat("yyyy/MM/dd")
        val begin:Date = formatter.parse(startDate)
        val end:Date = formatter.parse(endDate)
        val diff = end.time - begin.time
        return (diff / (24*60*60*1000)).toInt()
    }

}