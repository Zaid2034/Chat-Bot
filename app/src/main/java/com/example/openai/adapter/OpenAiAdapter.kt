package com.example.openai.adapter

import android.content.Context
import android.content.res.Resources
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.openai.R
import com.example.openai.activities.MainActivity
import com.example.openai.model.MessageModel

open class OpenAiAdapter(private val context: Context, private val list:ArrayList<MessageModel>):RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(viewType==0){
            return MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_message,parent,false))
        }
        else{
            return MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_bot,parent,false))
        }
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model=list[position]
        if(holder is MyViewHolder){
            if (model.isUser) {
                holder.itemView.findViewById<TextView>(R.id.tv_user).text = model.message
                holder.itemView.findViewById<ImageButton>(R.id.ib_send).setImageResource(R.drawable.user)
            } else {
                holder.itemView.findViewById<TextView>(R.id.tv_bot).text = model.message
                holder.itemView.findViewById<ImageButton>(R.id.ib_send2).setImageResource(R.drawable.bot)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        val message=list[position]
        if(message.isUser){
            return 0
        }
        else{
            return 1
        }
    }
    override fun getItemCount(): Int {
        return list.size
    }
    private fun Int.toDP():Int=(this/ Resources.getSystem().displayMetrics.density).toInt()
    private fun Int.toPX():Int=(this* Resources.getSystem().displayMetrics.density).toInt()
    class MyViewHolder(view:View):RecyclerView.ViewHolder(view)
}