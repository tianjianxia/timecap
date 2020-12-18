package com.example.login

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.time.LocalDate
import kotlin.collections.ArrayList

class mailAdapter(mail : ArrayList<Mail>) : RecyclerView.Adapter<mailAdapter.ViewHolder>() {
    var listener : AdapterListener? = null
    var data : ArrayList<Mail>? = null

    init {
        data = mail

    }

    inner class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        val from = view.findViewById<TextView>(R.id.from)
        val to = view.findViewById<TextView>(R.id.to)
        val senddate = view.findViewById<TextView>(R.id.senddate)
        val opendate = view.findViewById<TextView>(R.id.opendate)

        init{
            view.setOnClickListener {
                if(listener != null){
                    if(adapterPosition != androidx.recyclerview.widget.RecyclerView.NO_POSITION){
                        listener!!.itemOnClick(data!![adapterPosition].id)
                    }
                }
            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.from.text = "From: " + data!![position].from
        holder.to.text = "To: " + data!![position].to
        holder.senddate.text = "Send Date: " + data!![position].senddate.toString()
        holder.opendate.text = "Scheduled Date: " + data!![position].opendate.toString()
        println(data)
    }

    override fun getItemCount() = data!!.size

    fun setAdapterListener(listener : AdapterListener){
        this.listener = listener
    }



    interface AdapterListener {
        fun itemOnClick(id : String)
    }
}

data class Mail(var id : String, var from: String, var to : String, var senddate : LocalDate, var opendate : LocalDate);