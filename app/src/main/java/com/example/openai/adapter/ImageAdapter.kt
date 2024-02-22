package com.example.openai.adapter

import android.content.Context
import android.content.res.Resources
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.openai.R
import com.example.openai.activities.ImageActivity
import com.example.openai.model.ImageModel
import com.example.openai.model.MessageModel


open class ImageAdapter(private val context: Context, private val list:ArrayList<ImageModel>):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(viewType==0){
            return MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_message,parent,false))
        }
        else{
            return MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_image,parent,false))
        }
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model=list[position]
        if(holder is MyViewHolder){
            if (model.isUser) {
                holder.itemView.findViewById<TextView>(R.id.tv_user).text = model.ImageName
                holder.itemView.findViewById<ImageButton>(R.id.ib_send).setImageResource(R.drawable.user)
                holder.itemView.findViewById<TextView>(R.id.tv_gen).visibility=View.VISIBLE
                Handler(Looper.getMainLooper()).postDelayed({
                    holder.itemView.findViewById<TextView>(R.id.tv_gen).visibility=View.GONE
                }, 14000)
            } else {
                Glide
                    .with(context)
                    .load(model.ImageName)
                    .into(holder.itemView.findViewById(R.id.image))

//                if(context is ImageActivity){
//                    context.hideProgressBar()
//                }
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
    class MyViewHolder(view: View): RecyclerView.ViewHolder(view)
}