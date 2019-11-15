package com.example.spendingtracker

import android.content.Context
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.card_layout.view.*

class CardContainerRecyclerAdapter(val context: Context?, val titles: Array<String>) : RecyclerView.Adapter<CardContainerRecyclerAdapter.ViewHolder>()
{
    override fun getItemCount(): Int {
        return titles.size
    }
     override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.card_layout, parent, false))
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {

        p0.itemView.card_text.text = titles.get(p1)
        p0.itemView.setOnClickListener {
            Toast.makeText(context , titles.get(p1) ,Toast.LENGTH_SHORT).show()
        }

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
//        init {
//            itemView.setOnClickListener {
//                Toast.makeText(itemView.context, , Toast.LENGTH_LONG).show()
//            }
//        }
    }
}
